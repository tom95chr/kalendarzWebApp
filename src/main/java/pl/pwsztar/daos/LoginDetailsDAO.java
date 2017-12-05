package pl.pwsztar.daos;

import org.springframework.data.repository.CrudRepository;
import pl.pwsztar.entities.LoginDetails;

/**
 * Created by Lapek on 01.07.2017.
 */
public interface LoginDetailsDAO extends CrudRepository<LoginDetails, String> {
    LoginDetails findByTherapist_TherapistId(String therapistId);
    LoginDetails findByTherapist_Email(String email);
}
