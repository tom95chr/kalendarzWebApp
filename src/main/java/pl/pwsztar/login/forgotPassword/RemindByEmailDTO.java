package pl.pwsztar.login.forgotPassword;

import pl.pwsztar.recaptcha.RecaptchaForm;

public class RemindByEmailDTO extends RecaptchaForm {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
