package pl.pwsztar.event.eventType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;

@Component
public class EventTypeValidator implements Validator {

    @Autowired
    EventTypeDAO eventTypeDAO;

    public boolean supports(Class<?> aClass) {
        return EventType.class.equals(aClass);
    }


    public void validate(Object o, Errors errors) {
        EventType eventType = (EventType) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventTypeId", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "seats", "NotEmpty");
        if (eventTypeDAO.findByEventTypeId(eventType.getEventTypeId()) != null) {
            errors.rejectValue("eventTypeId", "Duplicate.eventType.Id");
            System.out.println(eventType.getSeats());
        }
    }
}