package pl.pwsztar.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.client.reservation.ReservationDTO;


@Component
public class ClientValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return ReservationDTO.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        ReservationDTO c = (ReservationDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

        if (!c.getEmailConfirm().equals(c.getEmail())) {
            errors.rejectValue("emailConfirm", "Diff.client.emailConfirm");
        }
    }
}