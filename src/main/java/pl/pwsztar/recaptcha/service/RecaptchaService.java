package pl.pwsztar.recaptcha.service;

public interface RecaptchaService {

    boolean isResponseValid(String remoteIp, String response);

}
