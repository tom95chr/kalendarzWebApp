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

    @Autowired
    ClientDAO clientDAO;

    public List<Event> getSortDates(List<Event> clients) {
        if (clients.size() > 0) {
            Map<Date, String> dateSort = new TreeMap<Date, String>();
            for (int i = 0; i < clients.size(); i++) {
                if(dateSort.get(clients.get(i).getStartDateTime()) == null){
                    dateSort.put(clients.get(i).getStartDateTime(), clients.get(i).getEventId());
                }
                else{

                    Date dat = clients.get(i).getStartDateTime();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    cal.add(Calendar.SECOND, 10);
                    System.out.println(dateSort.get(dat));
                    while(dateSort.get(cal.getTime()) != null) {
                      //  cal.setTime(dat);
                        System.out.println(dateSort.get(dat));
                        cal.add(Calendar.SECOND, 10);
                    }
                    dateSort.put(cal.getTime(), clients.get(i).getEventId());
                }
            }

            System.out.print(dateSort);
            List<Event> events = new ArrayList<Event>();
            int i = 0;
            for (Map.Entry<Date, String> entry : dateSort.entrySet()) {
                events.add(i, eventDAO.findByEventId(entry.getValue()));
                i++;
            }
            return events;
        }

        return null;
    }

    public String addClient(ClientDTO clientDTO, String eventId) {
        Event event = eventDAO.findByEventId(eventId);

        // true  wolne miejsca są
        for(Client cli : clientDAO.findAllByEvent(eventDAO.findByEventId(eventId))){

            if(cli.getEmail().equals(clientDTO.getEmail())){
                return "Jesteś już zapisany na zajęcia pod tym emailem";
            }
        }

        if (event.getConfirmed()) {
            if (event.getType_Event().getMaxSeats() - 1 == clientDAO.findAllByEvent(eventDAO.findByEventId(eventId)).size()) {
                event.setConfirmed(false);
                eventDAO.save(event); //ustawienie false jak się ktoś zapisze
            }

            Client client = new Client();
            client.setEmail(clientDTO.getEmail());
            client.setTelephone(clientDTO.getTelephone());
            client.setEvent(eventDAO.findByEventId(eventId));
            clientDAO.save(client);
        }
        return "zapisany";
    }
}
