package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agnieszka on 2017-06-20.
 */

@Service
public class EventService {
    @Autowired
    EventDAO eventDAO;


    public Event checkDates(EventDTO eventDTO) {

        List<Event> eventList = eventDAO.findByRoom(eventDTO.getRoom());

        for (Event eve : eventList) {

            if (!((eve.getStartDateTime().before(eventDTO.getStartDateTime()) && (eve.getEndDateTime().before(eventDTO.getStartDateTime()) || eve.getEndDateTime().compareTo(eventDTO.getStartDateTime()) == 0)) || ((eve.getStartDateTime().after(eventDTO.getEndDateTime()) || eve.getStartDateTime().compareTo(eventDTO.getEndDateTime()) == 0) && eve.getEndDateTime().after(eventDTO.getEndDateTime())))) {
                return eve;
            }

        }
        return null;
    }
}
