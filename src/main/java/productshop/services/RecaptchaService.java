package productshop.services;

public interface RecaptchaService {

    String verifyRecaptcha(String ip, String recaptchaResponse);
}
