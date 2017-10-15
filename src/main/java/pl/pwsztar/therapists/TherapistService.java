package pl.pwsztar.therapists;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.ClientService;
import pl.pwsztar.client.reservation.Reservation;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.EventDTO;
import pl.pwsztar.event.EventValidator;
import pl.pwsztar.event.eventType.EventType;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.event.eventType.EventTypeValidator;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.login.LoginService;
import pl.pwsztar.mainServices.EmailService;
import pl.pwsztar.mainServices.googleCalendar.GoogleCalendar;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service
public class TherapistService {

    @Autowired
    LoginService loginService;

    @Autowired
    TherapistDAO therapistDAO;

    @Autowired
    GoogleCalendar googleCalendar;

    @Autowired
    LoginDetailsDAO loginDetailsDAO;

    @Autowired
    EventDAO eventDAO;

    @Autowired
    EventTypeDAO eventTypeDAO;

    @Autowired
    TherapistService therapistService;

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    ClientService clientService;

    @Autowired
    EventValidator eventValidator;

    @Autowired
    EmailService emailService;

    public ModelAndView therapistEventsGet() {

        ModelAndView model = new ModelAndView("therapist/therapistEvents");
        model.addObject("user", loginService.getPrincipal());
        List<Event> events = eventDAO.findByTherapist_EmailOrderByStartDateTime(
                loginService.getPrincipal());
        model.addObject("events", events);
        model.addObject("therapists", therapistDAO.findAll());

        //number of participants
        List<Integer> participants = new ArrayList<Integer>();
        for (Event e : events
                ) {
            participants.add(new Integer(clientService.nrOfParticipants(e)));
        }
        model.addObject("participants", participants);
        return model;
    }

    public ModelAndView createEventGet() {
        ModelAndView modelAndView = new ModelAndView("therapist/createEvent");
        modelAndView.addObject("eventTypes", eventTypeDAO.findAll());
        modelAndView.addObject("therapists", therapistDAO.findAll());
        modelAndView.addObject("eventDTO", new EventDTO());
        return modelAndView;
    }

    public ModelAndView createEventPost(EventDTO eventDTO, BindingResult bindingResult) {

        //saving for event creation form
        EventDTO oldDto = eventDTO;

        ModelAndView model = new ModelAndView("therapist/createEvent");
        eventValidator.validate(eventDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addObject("eventTypes", eventTypeDAO.findAll());
            model.addObject("therapists", therapistDAO.findAll());
            return model;
        }


        //date format changed to googleDateFormat
        Format myGoogleFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        //cyclic events creation
        Integer nr = eventDTO.getNumberOfRepetitions();
        if (nr == null) nr = 0;

        for (int i = 0; i <= nr; i++) {
            String startDate = myGoogleFormat.format(eventDTO.getStartDateTime());
            String endDate = myGoogleFormat.format(eventDTO.getEndDateTime());
            Event collidedEvent = therapistService.detectCollisionsByTherapist(eventDTO);

            if (collidedEvent != null) {
                model.addObject("collidedEvent", collidedEvent);
                model.addObject("eventTypes", eventTypeDAO.findAll());
                model.addObject("therapists", therapistDAO.findAll());
                return model;
            } else {
                try {
                    String eventId = googleCalendar.createEvent(therapistDAO.findByEmail(loginService.getPrincipal()).getGoogleCalendarId(),
                            "free", "free", startDate + ":59.000+02:00",
                            endDate + ":59.000+02:00");

                    Event event = new Event();
                    event.setEventId(eventId);
                    event.setName("free");
                    event.setStartDateTime(eventDTO.getStartDateTime());
                    event.setEndDateTime(eventDTO.getEndDateTime());
                    event.setTherapist(therapistDAO.findByEmail(loginService.getPrincipal()));
                    event.setRoom(eventDTO.getRoom());
                    event.setEventType(eventTypeDAO.findByEventTypeId(eventDTO.getEventType()));
                    event.setFree(Boolean.TRUE);
                    eventDAO.save(event);
                    eventDTO = therapistService.addOneWeek(eventDTO);
                    //just for form view
                    model.addObject("info", "New event/events created successfully !");
                } catch (IOException e) {
                    model.addObject("info", "Event creation failed. Unexpected error");
                    e.printStackTrace();
                } catch (Exception e) {
                    model.addObject("info", "Event creation failed. Unexpected error");
                    e.printStackTrace();
                }
            }
        }

        model.addObject("eventTypes", eventTypeDAO.findAll());
        model.addObject("therapists", therapistDAO.findAll());
        return model;
    }

    public ModelAndView dropEvent(String eventId) {

        ModelAndView model = new ModelAndView("redirect:/therapist-events");
        List<Reservation> listOfParticipants = reservationDAO.findAllByEvent(eventDAO.findByEventId(eventId));
        for (Reservation reservation : listOfParticipants
                ) {
            String email = reservation.getClient().getEmail();
            emailService.sendEmail(email, "Event cancellation", "Hello.\n\nWe would like to inform, that" +
                    "event which you were signed has been cancelled.\n\nEvent details: " +
                    "\nTherapist: " + reservation.getEvent().getTherapist().getFirstName() + " " +
                    reservation.getEvent().getTherapist().getLastName() +
                    "\nStart date/time: " + reservation.getEvent().getStartDateTime() +
                    "\nroom nr:" + reservation.getEvent().getRoom() + "\n\nKind regards\n " + reservation.getEvent().getTherapist().getFirstName() + " " +
                    reservation.getEvent().getTherapist().getLastName());
        }
        try {
            reservationDAO.deleteReservationsByEvent_EventId(eventId);
            googleCalendar.deleteEvent(eventDAO.findByEventId(eventId).getTherapist().getGoogleCalendarId(), eventId);
            eventDAO.deleteByEventId(eventId);

        } catch (IOException e) {
            e.printStackTrace();
            model.addObject("info", "unexpected error during event dropping");
        }
        return model;
    }

    public ModelAndView eventParticipants(String eventId) {
        ModelAndView modelAndView = new ModelAndView("therapist/participants");
        modelAndView.addObject(reservationDAO.findAllByEvent(eventDAO.findByEventId(eventId)));
        return modelAndView;
    }

    //returns true if collisions found
    public Event detectCollisionsByTherapist(EventDTO eventDTO) {

        List<Event> events = eventDAO.findAll();

        for (Event event : events) {

            if (event.getRoom().equals(eventDTO.getRoom())
                    && (
                    (event.getStartDateTime().compareTo(eventDTO.getStartDateTime()) == 0)
                            || (event.getEndDateTime().compareTo(eventDTO.getStartDateTime()) == 0)
                            || ((eventDTO.getStartDateTime().after(event.getStartDateTime()))
                            && (eventDTO.getStartDateTime().before(event.getEndDateTime()))))
                    ) {

                return event;
            }
        }
        return null;
    }

    public EventDTO addOneWeek(EventDTO eventDTO) {

        Long start = (eventDTO.getStartDateTime().getTime() + (7 * 24 * 3600 * 1000));
        Long end = (eventDTO.getEndDateTime().getTime() + (7 * 24 * 3600 * 1000));
        eventDTO.setStartDateTime(new Date(start));
        eventDTO.setEndDateTime(new Date(end));

        return eventDTO;
    }

    public ModelAndView editEventGet(String eventId) {
        ModelAndView model = new ModelAndView("therapist/editEvent");
        model.addObject("event", eventDAO.findByEventId(eventId));
        model.addObject("eventDTO", new EventDTO());

        List<EventType> eventTypes = eventTypeDAO.findAll();
        List<String> types = new ArrayList<String>();
        for (EventType eventType : eventTypes) {
            types.add(eventType.getEventTypeId());
        }
        int index = types.indexOf(eventDAO.findByEventId(eventId).getEventType().getEventTypeId());
        Collections.swap(types, 0, index);
        model.addObject("types", types);
        return model;
    }

    public ModelAndView editEventPost(String eventId, EventDTO eventDTO, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView("redirect:/therapist-events");
        Event e = eventDAO.findByEventId(eventId);

        //System.out.println(eventDTO.getStartDateTime() + "\n" + eventDTO.getEndDateTime() + "\n" + eventDTO.getRoom() + "\n" + eventDTO.getEventType());

        /*//Format myGoogleFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        //String startDate = myGoogleFormat.format(eventDTO.getStartDateTime());
        String endDate = myGoogleFormat.format(eventDTO.getEndDateTime());*/

        if (!(eventDTO.getEventType().equals(e.getEventType().getEventTypeId()))){
            if (clientService.nrOfParticipants(e) > eventTypeDAO.findByEventTypeId(eventDTO.getEventType()).getSeats()) {
                model.addObject("editError", "Cannot change event type, because number of " +
                        "participants is greater than number of free seats");
                return model;
            }
            else {
                if (eventTypeDAO.findByEventTypeId(eventDTO.getEventType()).getSeats() > clientService.nrOfParticipants(e)) {
                    e.setFree(Boolean.TRUE);
                }
                else{
                    e.setFree(Boolean.FALSE);
                }
                e.setEventType(eventTypeDAO.findByEventTypeId(eventDTO.getEventType()));
            }
            if (eventDTO.getStartDateTime() != null) {
                e.setStartDateTime(eventDTO.getStartDateTime());
            }
            if (eventDTO.getEndDateTime() != null)
                e.setEndDateTime(eventDTO.getEndDateTime());
            if (!eventDTO.getRoom().equals(""))
                e.setRoom(eventDTO.getRoom());
            eventDAO.save(e);

            return model;
        }
        return model;
    }
}