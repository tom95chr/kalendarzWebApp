package pl.pwsztar.therapists;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lapek on 23.05.2017.
 */
public interface TherapistDAO extends CrudRepository<Therapist, String> {
    Therapist findByTherapistId(String therapistId);
    List<Therapist> findAll();
    Therapist findByEmail(String email);
}