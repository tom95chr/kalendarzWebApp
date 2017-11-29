package pl.pwsztar.therapists.editProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.pwsztar.therapists.TherapistDAO;

@Component
public class EditProfileValidator implements Validator {

    @Autowired
    TherapistDAO therapistDAO;

    public boolean supports(Class<?> aClass) {
        return EditProfileDTO.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        EditProfileDTO t = (EditProfileDTO) o;

    }
}
