package pl.pwsztar.daos;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.entities.EventHistory;

import java.util.List;

public interface EventHistoryDAO extends CrudRepository<EventHistory, String> {

    List<EventHistory> findAll();
    <S extends EventHistory> S save(S s);
}
