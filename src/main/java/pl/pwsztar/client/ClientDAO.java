package pl.pwsztar.client;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.event.Event;

import javax.transaction.Transactional;
import java.util.List;

public interface ClientDAO extends CrudRepository<Client, String> {

    List<Client> findAll();
    Client findByEmail(String email);
}
