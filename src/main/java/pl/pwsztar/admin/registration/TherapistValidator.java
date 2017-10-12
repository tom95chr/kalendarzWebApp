package pl.pwsztar.admin.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;

@Component
public class TherapistValidator implements Validator {

    @Autowired
    TherapistDAO therapistDAO;

    public boolean supports(Class<?> aClass) {
        return Therapist.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        Therapist t = (Therapist) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialization", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (t.getEmail().length() < 6 || t.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.therapist.email");
        }
        if (therapistDAO.findByEmail(t.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.therapist.email");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "NotEmpty");
        if (t.getTelephone().length() < 9) {
            errors.rejectValue("telephone", "Size.therapist.telephone");
        }
    }
}