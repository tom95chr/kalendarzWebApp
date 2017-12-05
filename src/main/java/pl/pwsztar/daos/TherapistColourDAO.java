package pl.pwsztar.daos;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.entities.TherapistColour;

import java.util.List;

public interface TherapistColourDAO extends CrudRepository<TherapistColour, String> {
    List<TherapistColour> findAll();
    List<TherapistColour> findAllByTaken(Boolean taken);
    TherapistColour findByColourCode(String colourCode);
}
