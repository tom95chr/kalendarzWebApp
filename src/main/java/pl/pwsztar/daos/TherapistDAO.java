package pl.pwsztar.daos;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.entities.Therapist;

import java.util.List;

/**
 * Created by Lapek on 23.05.2017.
 */
public interface TherapistDAO extends CrudRepository<Therapist, String> {
    Therapist findByTherapistId(String therapistId);
    List<Therapist> findAll();
    Therapist findByEmail(String email);
    List<Therapist> findAllByLoginDetails_UserRole(String userRole);
}