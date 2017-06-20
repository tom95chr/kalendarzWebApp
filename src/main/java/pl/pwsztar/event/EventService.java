package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.type_event.Type_EventDAO;

import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Agnieszka on 2017-06-20.
 */

@Service
public class EventService {
    @Autowired
    EventDAO eventDAO;
    @Autowired
    GoogleCalendar googleCalendar;
    @Autowired
    Type_EventDAO type_eventDAO;
    @Autowired
    TherapistDAO therapistDAO;

    public Event checkDates(EventDTO eventDTO) {

        List<Event> eventList = eventDAO.findByRoom(eventDTO.getRoom());

        for (Event eve : eventList) {

            if (!((eve.getStartDateTime().before(eventDTO.getStartDateTime()) && (eve.getEndDateTime().before(eventDTO.getStartDateTime()) || eve.getEndDateTime().compareTo(eventDTO.getStartDateTime()) == 0)) || ((eve.getStartDateTime().after(eventDTO.getEndDateTime()) || eve.getStartDateTime().compareTo(eventDTO.getEndDateTime()) == 0) && eve.getEndDateTime().after(eventDTO.getEndDateTime())))) {
                return eve;
            }

        }
        return null;
    }

    public void addNewEvent(EventDTO eventDTO, String user) throws IOException, ParseException {
        Date date = eventDTO.getStartDateTime();
        Date date2 = eventDTO.getEndDateTime();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
        String dat = formatter.format(date);
        String dat2 = formatter.format(date2);
        //qwe terapeute zmienić jak będzie logowanie zrobione
        String idEvent = googleCalendar.createEvent(googleCalendar.getGoogleCalendarId(user), eventDTO.getName(), "busy", dat + ":59.000+02:00", dat2 + ":59.000+02:00");


        Event event = new Event();
        event.setEventId( idEvent);


        event.setName(eventDTO.getName());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setEndDateTime(eventDTO.getEndDateTime());
        event.setRoom(eventDTO.getRoom());

        event.setType_Event(type_eventDAO.findByTypeEventId(eventDTO.getTyp()));

        event.setTherapist(therapistDAO.findByTherapistId(user));


        event.setConfirmed(true);


        eventDAO.save(event);
    }


    public void editEvent(EventDTO eventDTO, String eventId) throws IOException {
        System.out.println("user");



        Event event = eventDAO.findByEventId(eventId);
        event.setName(eventDTO.getName());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setEndDateTime(eventDTO.getEndDateTime());
        event.setRoom(eventDTO.getRoom());
        event.setType_Event(type_eventDAO.findByTypeEventId(eventDTO.getTyp()));

        event.setConfirmed(true);//

        eventDAO.save(event);
        googleCalendar.editEventGoogle(event);


    }
}
