package pl.pwsztar.daos;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.entities.EventType;

import java.util.List;


public interface EventTypeDAO extends CrudRepository<EventType, String> {
    List<EventType> findAll();
    EventType findByEventTypeId(String eventTypeId);
}
