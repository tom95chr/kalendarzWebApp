package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;

@Component
public class EventValidator implements Validator {

    @Autowired
    EventDAO eventDAO;

    public boolean supports(Class<?> aClass) {
        return EventDTO.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        EventDTO e = (EventDTO) o;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDateTime", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDateTime", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "room", "NotEmpty");

        /*if (t.getEmail().length() < 6 || t.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.therapist.email");
        }
        if (therapistDAO.findByEmail(t.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.therapist.email");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "NotEmpty");
        if (t.getTelephone().length() < 9) {
            errors.rejectValue("telephone", "Size.therapist.telephone");
        }*/
    }
}