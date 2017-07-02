package pl.pwsztar.login;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Lapek on 01.07.2017.
 */
public interface LoginDetailsDAO extends CrudRepository<LoginDetails, String> {
}
