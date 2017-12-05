package pl.pwsztar.components;


import pl.pwsztar.forms.RecaptchaForm;

public class ConfirmationCode extends RecaptchaForm{

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
