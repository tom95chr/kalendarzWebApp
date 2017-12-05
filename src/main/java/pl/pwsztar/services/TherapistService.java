package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.daos.*;
import pl.pwsztar.entities.Therapist;
import pl.pwsztar.forms.EventDTO;
import pl.pwsztar.entities.Event;
import pl.pwsztar.entities.Reservation;
import pl.pwsztar.entities.EventType;
import pl.pwsztar.forms.EditProfileDTO;
import pl.pwsztar.validators.EditProfileValidator;
import pl.pwsztar.validators.EventValidator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
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

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    EditProfileValidator editProfileValidator;

    @Autowired
    EventHistoryDAO eventHistoryDAO;

    @Autowired
    EventService eventService;

    public ModelAndView therapistEventsGet() {

        ModelAndView model = new ModelAndView("therapist/therapistEvents");
        model.addObject("user", loginService.getPrincipal());
        List<Event> events = eventDAO.findByTherapist_EmailOrderByStartDateTime(
                loginService.getPrincipal());
        model.addObject("events", events);
        model.addObject("therapists", therapistDAO.findAll());

        LocalDateTime now = LocalDateTime.now();
        //move to history and create participant list
        List<Integer> participants = new ArrayList<Integer>();
        for (Event e : events
                ) {
            participants.add(new Integer(e.nrOfParticipants()));
            if (e.getStartDateTime().isBefore(now)){
                eventService.moveToHistory(e);
            }
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

        ModelAndView model = new ModelAndView("therapist/createEvent");
        eventValidator.validate(eventDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addObject("eventTypes", eventTypeDAO.findAll());
            model.addObject("therapists", therapistDAO.findAll());
            return model;
        }
        //endDateTime = startdate + startTime + duration
        eventDTO.setStartDateTime(LocalDateTime.of(eventDTO.getStartDate(), eventDTO.getStartTime()));
        LocalDateTime startPlusMins = eventDTO.getStartDateTime().plusMinutes(eventDTO.getDuration());
        eventDTO.setEndDateTime(startPlusMins);

        //date format changed for google
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        //cyclic events creation
        Integer nr = eventDTO.getNumberOfRepetitions();
        if (nr == null) nr = 0;

        for (int i = 0; i <= nr; i++) {

            String startDate = eventDTO.getStartDateTime().format(formatter);
            String endDate = eventDTO.getEndDateTime().format(formatter);
            System.out.println(startDate);
            Event collidedEvent = therapistService.detectCollisionsByTherapist(eventDTO, "");

            if (collidedEvent != null) {
                model.addObject("collidedEvent", collidedEvent);
                model.addObject("eventTypes", eventTypeDAO.findAll());
                model.addObject("therapists", therapistDAO.findAll());
                return model;
            } else {
                try {
                    String eventId = googleCalendar.createEvent(therapistDAO.findByEmail(loginService.getPrincipal()).getGoogleCalendarId(),
                            "wolny", "free", startDate + ":00.000+01:00",
                            endDate + ":00.000+01:00");

                    Event event = new Event();
                    event.setEventId(eventId);
                    event.setName("wolny");
                    event.setStartDateTime(eventDTO.getStartDateTime());
                    event.setEndDateTime(eventDTO.getEndDateTime());
                    event.setTherapist(therapistDAO.findByEmail(loginService.getPrincipal()));
                    event.setRoom(eventDTO.getRoom());
                    event.setEventType(eventTypeDAO.findByEventTypeId(eventDTO.getEventType()));
                    event.setFree(Boolean.TRUE);
                    eventDAO.save(event);
                    eventDTO = therapistService.addOneWeek(eventDTO);
                    //just for form view
                    model.addObject("successInfo", "Nowe spotkanie / spotkania zostały utworzone pomyślnie");
                } catch (IOException e) {
                    model.addObject("info", "NIEPRZEWIDZIANY BŁĄD NIE UTWORZONO SPOTAKANIA");
                    e.printStackTrace();
                } catch (Exception e) {
                    model.addObject("info", "NIEPRZEWIDZIANY BŁĄD NIE UTWORZONO SPOTAKANIA");
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
        //c = cancellation
        emailService.sendMultiple(listOfParticipants, 'c');
        try {
            reservationDAO.deleteReservationsByEvent_EventId(eventId);
            googleCalendar.deleteEvent(eventDAO.findByEventId(eventId).getTherapist().getGoogleCalendarId(), eventId);
            eventDAO.deleteByEventId(eventId);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    public ModelAndView eventParticipants(String eventId) {
        ModelAndView modelAndView = new ModelAndView("therapist/participants");
        modelAndView.addObject(reservationDAO.findAllByEvent(eventDAO.findByEventId(eventId)));
        return modelAndView;
    }

    //returns true if collisions found
    public Event detectCollisionsByTherapist(EventDTO eventDTO, String eventId) {

        List<Event> events = eventDAO.findAll();

        for (Event event : events) {

            if (!event.getEventId().equals(eventId)) {
                if (event.getRoom().equals(eventDTO.getRoom())
                        && (
                        //All collision(1-7) types are showed on kolizje.png
                        (eventDTO.getStartDateTime().isBefore(event.getStartDateTime()) && eventDTO.getEndDateTime().isAfter(event.getStartDateTime())) ||
                                (eventDTO.getStartDateTime().isBefore(event.getStartDateTime()) && eventDTO.getEndDateTime().isAfter(event.getEndDateTime())) ||
                                (eventDTO.getStartDateTime().isBefore(event.getEndDateTime()) && eventDTO.getEndDateTime().isAfter(event.getEndDateTime())) ||
                                (eventDTO.getStartDateTime().compareTo(event.getStartDateTime()) == 0 && eventDTO.getEndDateTime().compareTo(event.getEndDateTime())== 0) ||
                                (eventDTO.getStartDateTime().isAfter(event.getStartDateTime()) && eventDTO.getEndDateTime().isBefore(event.getEndDateTime())) ||
                                (eventDTO.getStartDateTime().compareTo(event.getStartDateTime()) == 0 && eventDTO.getEndDateTime().isBefore(event.getEndDateTime())) ||
                                (eventDTO.getStartDateTime().isAfter(event.getStartDateTime()) && eventDTO.getEndDateTime().compareTo(event.getEndDateTime()) == 0)

                        ) ){
                    return event;
                }
            }
        }
        return null;
    }

    public EventDTO addOneWeek(EventDTO eventDTO) {

        LocalDateTime start = eventDTO.getStartDateTime().plusWeeks(1);
        LocalDateTime end = eventDTO.getEndDateTime().plusWeeks(1);
        eventDTO.setStartDateTime(start);
        eventDTO.setEndDateTime(end);

        return eventDTO;
    }

    public ModelAndView editEventGet(String eventId) {
        ModelAndView model = new ModelAndView("therapist/editEvent");
        model.addObject("event", eventDAO.findByEventId(eventId));
        model.addObject("eventDTO", new EventDTO());
        model.addObject("types", getEventTypesId(eventTypeDAO.findAll(), eventId));
        model.addObject("therapists",therapistDAO.findAll());
        return model;
    }

    public ModelAndView editEventPost(String eventId, EventDTO eventDTO, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView("redirect:/therapist-events");

        Event e = eventDAO.findByEventId(eventId);

        //to avoid problems with nullException
        if (eventDTO.getStartDate()==null){
            eventDTO.setStartDate(e.getStartDateTime().toLocalDate());
        }
        if (eventDTO.getStartTime()==null){
            eventDTO.setStartTime(e.getStartDateTime().toLocalTime());
        }
        if (eventDTO.getDuration()==null){
            eventDTO.setDuration(e.calculateDuration());
        }
        if (eventDTO.getRoom().equals("")) {
            eventDTO.setRoom(e.getRoom());
        }


        //endDateTime = startdate + startTime + duration
        eventDTO.setStartDateTime(LocalDateTime.of(eventDTO.getStartDate(), eventDTO.getStartTime()));
        LocalDateTime startPlusMins = eventDTO.getStartDateTime().plusMinutes(eventDTO.getDuration());
        eventDTO.setEndDateTime(startPlusMins);

        //if notching changed
        if (eventDTO.getStartDateTime().isEqual(e.getStartDateTime()) &&
                eventDTO.getEndDateTime().isEqual(e.getEndDateTime()) &&
                eventDTO.getRoom().equals(e.getRoom()) &&
                eventDTO.getEventType().equals(e.getEventType().getEventTypeId())){
            ModelAndView m2 = new ModelAndView("therapist/editEvent");
            m2.addObject("event", eventDAO.findByEventId(eventId));
            m2.addObject("eventDTO", new EventDTO());
            m2.addObject("types", getEventTypesId(eventTypeDAO.findAll(), eventId));
            model.addObject("therapists",therapistDAO.findAll());
            m2.addObject("editError", "Nic nie zmieniono");
            return m2;
        }

        //event tye old = new
        if (!(eventDTO.getEventType().equals(e.getEventType().getEventTypeId()))) {
            //participants>free seats
            if (e.nrOfParticipants() > eventTypeDAO.findByEventTypeId(eventDTO.getEventType()).getSeats()) {
                ModelAndView m2 = new ModelAndView("therapist/editEvent");
                m2.addObject("event", eventDAO.findByEventId(eventId));
                m2.addObject("eventDTO", new EventDTO());
                m2.addObject("types", getEventTypesId(eventTypeDAO.findAll(), eventId));
                model.addObject("therapists",therapistDAO.findAll());
                m2.addObject("editError", "Nie można zmienić typu spotkania, ponieważ " +
                        "ilość zapisanych osób jest większa niż ilość wolnych miejsc.");
                return m2;
            } else {
                if (eventTypeDAO.findByEventTypeId(eventDTO.getEventType()).getSeats() > e.nrOfParticipants()) {
                    e.setFree(Boolean.TRUE);
                } else {
                    e.setFree(Boolean.FALSE);
                }
                e.setEventType(eventTypeDAO.findByEventTypeId(eventDTO.getEventType()));
            }
        }

        //availability for googleCal
        String availability;
        if (e.getFree()) {
            availability = "free";
        } else {
            availability = "busy";
        }
        //if date/time changed
        if (!(eventDTO.getStartDateTime().isEqual(e.getStartDateTime())) || !(eventDTO.getEndDateTime().isEqual(e.getEndDateTime()))) {

            Event collidedEvent = therapistService.detectCollisionsByTherapist(eventDTO, eventId);
            if (collidedEvent != null) {
                ModelAndView m2 = new ModelAndView("therapist/editEvent");
                m2.addObject("event", eventDAO.findByEventId(eventId));
                m2.addObject("eventDTO", new EventDTO());
                m2.addObject("types", getEventTypesId(eventTypeDAO.findAll(), eventId));
                model.addObject("therapists",therapistDAO.findAll());
                m2.addObject("collidedEvent", collidedEvent);
                return m2;
            }
            e.setStartDateTime(eventDTO.getStartDateTime());
            e.setEndDateTime(eventDTO.getEndDateTime());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String startDate = eventDTO.getStartDateTime().format(formatter);
            String endDate = eventDTO.getEndDateTime().format(formatter);

            String therapistEmail = loginService.getPrincipal();
            try {
                Boolean isUpdated = googleCalendar.editEvent(therapistEmail, eventId, startDate +
                        ":59.000+01:00", endDate + ":59.000+01:00", availability);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        //when time/date not changed
        else {
            try {
                String therapistEmail = loginService.getPrincipal();
                Boolean isUpdated = googleCalendar.editEvent(therapistEmail, eventId, availability);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (!e.getRoom().equals(eventDTO.getRoom()))
            e.setRoom(eventDTO.getRoom());
        //save
        eventDAO.save(e);
        //inform participants
        List<Reservation> r = reservationDAO.findAllByEvent(e);
        if (r.size() > 0) {
            //e = edited
            emailService.sendMultiple(r, 'e');
        }

        return model;
    }

    public ModelAndView dropParticipant(String confirmationCode){
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode);
        String eventId = reservationDAO.findByConfirmationCode(confirmationCode).getEvent().getEventId();
        ModelAndView modelAndView = new ModelAndView("redirect:/therapist-events-event-"+eventId+"-participants");
        Event event = eventDAO.findByEventId(eventId);
        if (event.nrOfParticipants() - 1 < event.getEventType().getSeats()){
            event.setFree(Boolean.TRUE);
        }
        reservationDAO.delete(reservation);
        return modelAndView;
    }

    public ModelAndView editProfileGet(){
        ModelAndView modelAndView = new ModelAndView("/therapist/editProfile");
        modelAndView.addObject("therapist", therapistDAO.findByEmail(loginService.getPrincipal()));
        modelAndView.addObject("editProfileDTO",new EditProfileDTO());
        return modelAndView;
    }

    public ModelAndView editProfilePost(EditProfileDTO editProfileDTO, BindingResult bindingResult){
        ModelAndView model = new ModelAndView("/therapist/editProfile");
        Therapist therapist = therapistDAO.findByEmail(loginService.getPrincipal());
        model.addObject("therapist", therapist);
        model.addObject("editProfileDTO",new EditProfileDTO());

        editProfileValidator.validate(editProfileDTO,bindingResult);

        if (bindingResult.hasErrors()) {
            return  model;
        }

        //if editData equals therapistData
        if (!editProfileDTO.getFirstName().equals("")){
            therapist.setFirstName(editProfileDTO.getFirstName());
        }
        if (!editProfileDTO.getLastName().equals("")){
            therapist.setLastName(editProfileDTO.getLastName());
        }
        if (!editProfileDTO.getSpecialization().equals("")){
            therapist.setSpecialization(editProfileDTO.getSpecialization());
        }
        if (!editProfileDTO.getTelephone().equals("")){
            therapist.setTelephone(editProfileDTO.getTelephone());
        }
        if (!editProfileDTO.getDescription().equals("")){
            therapist.setDescription(editProfileDTO.getDescription());
        }
        if (!editProfileDTO.getEmail().equals("")){
            therapist.setEmail(editProfileDTO.getEmail());
            model.addObject("emailChanged", Boolean.TRUE);
        }

        therapistDAO.save(therapist);

        return model;
    }

    public List<String> getEventTypesId(List<EventType> eventTypes, String eventId) {
        List<String> types = new ArrayList<String>();
        for (EventType eventType : eventTypes) {
            types.add(eventType.getEventTypeId());
        }
        int index = types.indexOf(eventDAO.findByEventId(eventId).getEventType().getEventTypeId());
        Collections.swap(types, 0, index);
        return types;
    }
}