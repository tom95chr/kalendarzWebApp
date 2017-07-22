package pl.pwsztar.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.login.LoginDetailsDAO;

@Component
public class UserValidator implements Validator {

    @Autowired
    LoginDetailsDAO loginDetailsDAO;


    public boolean supports(Class<?> aClass) {
        return LoginDetails.class.equals(aClass);
    }


    public void validate(Object o, Errors errors) {
        LoginDetails user = (LoginDetails) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
