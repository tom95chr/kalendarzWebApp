package pl.pwsztar.therapists.colour;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.therapists.Therapist;

import java.util.List;

public interface TherapistColourDAO extends CrudRepository<TherapistColour, String> {
    List<TherapistColour> findAll();
    List<TherapistColour> findAllByTaken(Boolean taken);
    TherapistColour findByColourCode(String colourCode);
}
