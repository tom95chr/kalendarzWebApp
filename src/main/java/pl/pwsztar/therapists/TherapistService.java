package pl.pwsztar.therapists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.ClientService;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.EventDTO;
import pl.pwsztar.event.EventParticipant;
import pl.pwsztar.event.eventType.EventType;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.event.eventType.EventTypeValidator;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.login.LoginService;
import pl.pwsztar.mainServices.googleCalendar.GoogleCalendar;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    EventTypeValidator eventTypeValidator;

    @Autowired
    TherapistService therapistService;

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    ClientService clientService;

    public ModelAndView therapistEventsGet(){

        ModelAndView model = new ModelAndView("therapist/therapistEvents");
        model.addObject("user", loginService.getPrincipal());
        model.addObject("events",eventDAO.findByTherapist_Email(loginService.getPrincipal()));
        model.addObject("therapists",therapistDAO.findAll());

        //number of participants
        List<Integer> participants = new ArrayList<Integer>();
        for (Event e:eventDAO.findByTherapist_Email(loginService.getPrincipal())
             ) {
            participants.add(new Integer(clientService.nrOfParticipants(e)));
            System.out.println(e.getEventId() + "  "+ clientService.nrOfParticipants(e));
        }
        model.addObject("participants",participants);
        return model;
    }

    public ModelAndView therapistSettingsGet(){

        ModelAndView model = new ModelAndView("therapist/settings");
        model.addObject("eventTypes",eventTypeDAO.findAll());
        model.addObject("eventType",new EventType());
        return model;
    }

    public ModelAndView therapistSettingsPost(EventType eventType, BindingResult bindingResult){

        ModelAndView model = new ModelAndView("redirect:/therapist-events/settings");
        model.addObject("eventTypes",eventTypeDAO.findAll());

        eventTypeValidator.validate(eventType, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("event/settings");
        }
        eventTypeDAO.save(eventType);
        return model;
    }

    public ModelAndView createEvent(HttpServletRequest request, EventDTO eventDTO, BindingResult result, String user){

        ModelAndView model = new ModelAndView("therapist/createEvent");
        model.addObject("eventTypes", eventTypeDAO.findAll());
        model.addObject("therapists",therapistDAO.findAll());

        //saving for event creation form
        EventDTO oldDto = eventDTO;

        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {
            //date format changed to googleDateFormat
            Format myGoogleFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            //cyclic events creation
            for (int i = 0; i <= eventDTO.getNumberOfRepetitions(); i++){
                String startDate = myGoogleFormat.format(eventDTO.getStartDateTime());
                String endDate   = myGoogleFormat.format(eventDTO.getEndDateTime());
                Event collidedEvent = therapistService.detectColisionsByTherapist(eventDTO);

                if (collidedEvent != null){
                    model.addObject("collidedEvent",collidedEvent);
                    return model;
                }
                else {
                    model.addObject("eventCreated","New event/events created successfully !");
                    try {
                        String eventId = googleCalendar.createEvent(therapistDAO.findByEmail(user).getGoogleCalendarId(),
                                "free", "free", startDate + ":59.000+02:00",
                                endDate + ":59.000+02:00");

                        Event event = new Event();
                        event.setEventId(eventId);
                        event.setName("free");
                        event.setStartDateTime(eventDTO.getStartDateTime());
                        event.setEndDateTime(eventDTO.getEndDateTime());
                        event.setTherapist(therapistDAO.findByEmail(user));
                        event.setRoom(eventDTO.getRoom());
                        event.setEventType(eventTypeDAO.findByEventTypeId(eventDTO.getEventType()));
                        event.setFree(Boolean.TRUE);
                        eventDAO.save(event);
                        eventDTO = therapistService.addOneWeek(eventDTO);
                    } catch (IOException e) {
                        model.addObject("error","Event creation failed");
                        e.printStackTrace();
                        return model;
                    }
                    catch (Exception e){
                        model.addObject("error","Event creation failed");
                        e.printStackTrace();
                        return model;
                    }
                }
            }
        }
        eventDTO = oldDto;
        return model;
    }

    public ModelAndView dropEvent(String eventId){

        ModelAndView model = new ModelAndView("redirect:/therapist-events");
        try {
            reservationDAO.deleteReservationsByEvent_EventId(eventId);
            googleCalendar.deleteEvent(eventDAO.findByEventId(eventId).getTherapist().getGoogleCalendarId(), eventId);
            eventDAO.deleteByEventId(eventId);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    public ModelAndView eventParticipants(String eventId){
        ModelAndView modelAndView = new ModelAndView("therapist/participants");
        modelAndView.addObject(reservationDAO.findAllByEvent(eventDAO.findByEventId(eventId)));
        return modelAndView;
    }

    //returns true if colissions found
    public Event detectColisionsByTherapist(EventDTO eventDTO) {

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

}