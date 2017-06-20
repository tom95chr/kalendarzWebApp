package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;

import java.util.*;

/**
 * Created by Agnieszka on 2017-06-20.
 */

@Service
public class ClientService {
    @Autowired
    EventDAO eventDAO;


    public List<Event> getSortDates(List<Event> clients) {
        if (clients.size() > 0) {
            Map<Date, String> dateSort = new TreeMap<Date, String>();
            for (int i = 0; i < clients.size(); i++) {
                dateSort.put(clients.get(i).getStartDateTime(), clients.get(i).getEventId());
            }

            System.out.print(dateSort);
            List<Event> events = new ArrayList<Event>();
            int i = 0;
            for (Map.Entry<Date, String> entry : dateSort.entrySet()) {
                System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
                events.add(i, eventDAO.findByEventId(entry.getValue()));
                i++;
            }
            return  events;
        }

        return null;
    }
}
