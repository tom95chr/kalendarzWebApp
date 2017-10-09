package pl.pwsztar.client.reservation;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.client.Client;
import pl.pwsztar.event.Event;

public interface ReservationDAO extends CrudRepository<Reservation, String> {
    Reservation findByClientAndEvent(Client client, Event event);
    //Client findByEvent_EventId(String event_id);
    //List<Client> findAllByEvent(Event event);
    //@Transactional
    //void deleteAllByEvent(Event event);
}