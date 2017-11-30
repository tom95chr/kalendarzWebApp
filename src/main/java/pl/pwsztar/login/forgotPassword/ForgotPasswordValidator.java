package pl.pwsztar.login.forgotPassword;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ForgotPasswordValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return ForgotPasswordDTO.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        ForgotPasswordDTO c = (ForgotPasswordDTO) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty");

        if (!c.getNewPassword().equals(c.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userForm.passwordConfirm");
        }
    }
}