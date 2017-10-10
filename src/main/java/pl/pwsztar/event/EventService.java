package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;

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
    EventTypeDAO eventTypeDAO;
    @Autowired
    TherapistDAO therapistDAO;


    //returns true if colissions found
    public Event detectColisionsByTherapist(EventDTO eventDTO) {
        List<Event> events = eventDAO.findAll();
        //System.out.println("therapistId : "+therapistEmail);

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