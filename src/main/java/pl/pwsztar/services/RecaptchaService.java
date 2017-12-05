package pl.pwsztar.services;

public interface RecaptchaService {

    boolean isResponseValid(String remoteIp, String response);

}
