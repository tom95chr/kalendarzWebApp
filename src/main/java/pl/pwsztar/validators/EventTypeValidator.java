package pl.pwsztar.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.daos.EventTypeDAO;
import pl.pwsztar.entities.EventType;

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