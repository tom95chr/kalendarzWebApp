package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;

import java.time.LocalDateTime;

@Component
public class EventValidator implements Validator {

    @Autowired
    EventDAO eventDAO;

    public boolean supports(Class<?> aClass) {
        return EventDTO.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        EventDTO e = (EventDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startTime", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "room", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "duration", "NotEmpty");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(e.getStartDate(), e.getStartTime());

        if (start.isBefore(now)){
            errors.rejectValue("startDate","event.StartDateTimeisBeforeNow");
        }
    }
}