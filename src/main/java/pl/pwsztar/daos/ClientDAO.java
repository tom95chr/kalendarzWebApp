package pl.pwsztar.daos;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.entities.Client;

import java.util.List;

public interface ClientDAO extends CrudRepository<Client, String> {

    List<Client> findAll();
    Client findByEmail(String email);
}
