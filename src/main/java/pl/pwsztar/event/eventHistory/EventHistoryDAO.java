package pl.pwsztar.event.eventHistory;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventHistoryDAO extends CrudRepository<EventHistory, String> {

    List<EventHistory> findAll();
    <S extends EventHistory> S save(S s);
}
