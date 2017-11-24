package pl.pwsztar.client.reservation;

import pl.pwsztar.recaptcha.RecaptchaForm;

import javax.persistence.Transient;

public class ReservationDTO extends RecaptchaForm{

    private String email;
    private String emailConfirm;
    private String telephone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Transient
    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
