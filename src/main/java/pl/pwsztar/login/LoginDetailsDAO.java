package pl.pwsztar.login;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lapek on 01.07.2017.
 */
public interface LoginDetailsDAO extends CrudRepository<LoginDetails, String> {
    LoginDetails findByTherapist_TherapistId(String therapistId);
}
