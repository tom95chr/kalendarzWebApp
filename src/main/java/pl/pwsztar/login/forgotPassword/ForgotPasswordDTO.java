package pl.pwsztar.login.forgotPassword;

import pl.pwsztar.recaptcha.RecaptchaForm;

import javax.persistence.Transient;

public class ForgotPasswordDTO extends RecaptchaForm {

    private String newPassword;
    private String confirmPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
