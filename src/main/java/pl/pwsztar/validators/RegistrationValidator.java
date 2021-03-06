package pl.pwsztar.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.forms.RegistrationDTO;
import pl.pwsztar.daos.TherapistDAO;

@Component
public class RegistrationValidator implements Validator {

    @Autowired
    TherapistDAO therapistDAO;

    public boolean supports(Class<?> aClass) {
        return RegistrationDTO.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        RegistrationDTO t = (RegistrationDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialization", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (t.getEmail().length() < 4 || t.getEmail().length() > 100) {
            errors.rejectValue("email", "Size.therapist.email");
        }
        if (therapistDAO.findByEmail(t.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.therapist.email");
        }

        if (t.getPassword().length() < 8 || t.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!t.getPasswordConfirm().equals(t.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}