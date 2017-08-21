package pl.pwsztar.event.eventType;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EventTypeDAO extends CrudRepository<EventType, String> {
    List<EventType> findAll();
    EventType findByEventTypeId(String eventTypeId);
}
