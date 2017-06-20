package pl.pwsztar.client;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.event.Event;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by Agnieszka on 2017-06-11.
 */
public interface ClientDAO extends CrudRepository< Client, String> {
    Client findByEvent_EventId(String  event_id);
    List<Client> findAll();
    List<Client> findAllByEvent(Event event);
}
