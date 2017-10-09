package pl.pwsztar.client;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.event.Event;

import javax.transaction.Transactional;
import java.util.List;

public interface ClientDAO extends CrudRepository<Client, String> {
    //Client findByEvent_EventId(String event_id);
    List<Client> findAll();
    List<Event> findAllByEmail(String email);
    //List<Client> findAllByEvent(Event event);
    //@Transactional
    //void deleteAllByEvent(Event event);
}
