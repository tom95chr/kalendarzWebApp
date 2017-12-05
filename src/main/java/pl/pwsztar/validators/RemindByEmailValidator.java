package pl.pwsztar.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.forms.RemindByEmailDTO;
import pl.pwsztar.daos.TherapistDAO;

@Component
public class RemindByEmailValidator implements Validator {

    @Autowired
    TherapistDAO therapistDAO;

    public boolean supports(Class<?> aClass) {
        return RemindByEmailDTO.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        RemindByEmailDTO c = (RemindByEmailDTO) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

        if (therapistDAO.findByEmail(c.getEmail()) == null) {
            errors.rejectValue("email", "Email.NotFound");
        }
    }
}