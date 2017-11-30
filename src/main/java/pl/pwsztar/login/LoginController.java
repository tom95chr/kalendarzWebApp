package pl.pwsztar.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.ClientService;
import pl.pwsztar.client.confirmation.ConfirmationCodeValidator;
import pl.pwsztar.login.forgotPassword.ForgotPasswordDTO;
import pl.pwsztar.login.forgotPassword.ForgotPasswordValidator;
import pl.pwsztar.login.forgotPassword.RemindByEmailDTO;
import pl.pwsztar.login.forgotPassword.RemindByEmailValidator;
import pl.pwsztar.recaptcha.RecaptchaFormValidator;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Lapek on 11.06.2017.
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    RemindByEmailValidator remindByEmailValidator;

    @Autowired
    RecaptchaFormValidator recaptchaFormValidator;

    @Autowired
    ForgotPasswordValidator forgotPasswordValidator;

    @Autowired
    public LoginController(LoginService loginService, RemindByEmailValidator remindByEmailValidator,
                            ForgotPasswordValidator forgotPasswordValidator, RecaptchaFormValidator recaptchaFormValidator) {
        this.loginService = loginService;
        this.remindByEmailValidator = remindByEmailValidator;
        this.forgotPasswordValidator = forgotPasswordValidator;
        this.recaptchaFormValidator = recaptchaFormValidator;
    }

    @ModelAttribute("recaptchaSiteKey")
    public String getRecaptchaSiteKey(@Value("${recaptcha.site-key}") String recaptchaSiteKey) {
        return recaptchaSiteKey;
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", loginService.getPrincipal());
        return "login/accessDenied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login/login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/remind-password", method = RequestMethod.GET)
    public ModelAndView remindPasswordGet(){
        return loginService.remindByEmailGet();
    }

    @RequestMapping(value = "/remind-password", method = RequestMethod.POST)
    public ModelAndView remindPasswordPost(@ModelAttribute("remindByEmailDTO")RemindByEmailDTO remindByEmailDTO,
                                           BindingResult bindingResult) {
        return loginService.remindByEmailPost(remindByEmailDTO, bindingResult);
    }

    @RequestMapping(value = "/reset-{token}-{emailToken}", method = RequestMethod.GET)
    public ModelAndView forgotPasswordGet(@PathVariable("token") String token, @PathVariable("emailToken")
            String emailToken){
        return loginService.forgotPasswordGet();
    }

    @RequestMapping(value = "/reset-{token}-{emailToken}", method = RequestMethod.POST)
    public ModelAndView forgotPasswordPost(@ModelAttribute("forgotPasswordDTO") ForgotPasswordDTO forgotPasswordDTO,
                                           BindingResult bindingResult, @PathVariable("token") String token,
                                           @PathVariable("emailToken") String emailToken) {
        return loginService.forgotPasswordPost(forgotPasswordDTO, bindingResult, token, emailToken);
    }
}
