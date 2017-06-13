package pl.pwsztar.event;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Agnieszka on 2017-06-11.
 */
public interface EventDAO extends CrudRepository< Event, String> {
    Event findByEventId(String eventId);
    List<Event> findAll();
    <S extends Event> S save(S s);
    List<Event> findByRoom(String room);
}
