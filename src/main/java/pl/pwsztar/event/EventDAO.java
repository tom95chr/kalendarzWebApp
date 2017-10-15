package pl.pwsztar.event;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface EventDAO extends CrudRepository<Event, String> {
    Event findByEventId(String eventId);
    List<Event> findAll();
    <S extends Event> S save(S s);
    List<Event> findByTherapist_TherapistIdOrderByStartDateTime(String therapistId);
    List<Event> findByTherapist_EmailOrderByStartDateTime(String therapistId);
    List<Event> findByTherapist_Email(String email);

    @Transactional
    void deleteByEventId(String eventId);

}
