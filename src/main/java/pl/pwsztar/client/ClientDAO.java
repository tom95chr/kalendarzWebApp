package pl.pwsztar.client;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Agnieszka on 2017-06-11.
 */
public interface ClientDAO extends CrudRepository< Client, String> {
    Client findByEmail(String email);
    List<Client> findAll();
}
