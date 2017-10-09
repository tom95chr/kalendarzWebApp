package pl.pwsztar.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ClientValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        Client c = (Client) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (c.getEmail().length() < 6 || c.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.therapist.email");
        }
    }
}