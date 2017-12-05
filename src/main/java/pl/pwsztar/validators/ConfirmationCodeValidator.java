package pl.pwsztar.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.components.ConfirmationCode;

@Component
public class ConfirmationCodeValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return ConfirmationCode.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        ConfirmationCode c = (ConfirmationCode) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty");
    }
}
