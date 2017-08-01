package pl.pwsztar.type_event;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Agnieszka on 2017-06-11.
 */
public interface Type_EventDAO extends CrudRepository<Type_Event, String> {
    //Type_Event findByTypeEventId(String type_event_id);
    List<Type_Event> findAll();
    //List<Type_Event> findAllByTypeEventId(String type_event_id);
    Type_Event findByTypeEventId(String eventTypeId);
}
