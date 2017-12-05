package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.daos.LoginDetailsDAO;
import pl.pwsztar.entities.LoginDetails;
import pl.pwsztar.forms.ForgotPasswordDTO;
import pl.pwsztar.validators.ForgotPasswordValidator;
import pl.pwsztar.forms.RemindByEmailDTO;
import pl.pwsztar.validators.RemindByEmailValidator;
import pl.pwsztar.validators.RecaptchaFormValidator;

@Service
@Transactional
public class LoginService {

    @Autowired
    ForgotPasswordValidator forgotPasswordValidator;

    @Autowired
    RemindByEmailValidator remindByEmailValidator;

    @Autowired
    RecaptchaFormValidator recaptchaFormValidator;

    @Autowired
    LoginDetailsDAO loginDetailsDAO;

    @Autowired
    KeyGeneratorService keyGeneratorService;

    @Autowired
    EmailService emailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    public ModelAndView remindByEmailGet(){
        ModelAndView modelAndView = new ModelAndView("/login/remindByEmail");
        modelAndView.addObject(new RemindByEmailDTO());
        return modelAndView;
    }

    public ModelAndView remindByEmailPost(RemindByEmailDTO remindByEmailDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("/login/remindByEmail");
        remindByEmailValidator.validate(remindByEmailDTO, bindingResult);

        if (bindingResult.hasErrors()){
            modelAndView.addObject(new RemindByEmailDTO());
            modelAndView.addObject("notFoundError","Nie istnieje konto z podanym adresem email.");
            return modelAndView;
        }
        LoginDetails loginDetails = loginDetailsDAO.findByTherapist_Email(remindByEmailDTO.getEmail());
        //token = md5 hashed user password
        String token = keyGeneratorService.generate(loginDetails.getPassword());
        //send email with reset link
        String emailToken = keyGeneratorService.generate(loginDetails.getTherapist().getEmail());
        emailService.resetPasswordEmail(loginDetails.getTherapist().getEmail(),"Reset hasła",token, emailToken);
        modelAndView.addObject("emailSent",Boolean.TRUE);
        return modelAndView;
    }

    public ModelAndView forgotPasswordGet(){
        ModelAndView modelAndView = new ModelAndView("/login/forgotPassword");
        modelAndView.addObject(new ForgotPasswordDTO());
        return modelAndView;
    }

    public ModelAndView forgotPasswordPost(ForgotPasswordDTO forgotPasswordDTO, BindingResult bindingResult,
                                           String token, String emailToken){
        ModelAndView modelAndView = new ModelAndView("/result");

        forgotPasswordValidator.validate(forgotPasswordDTO, bindingResult);

        if (bindingResult.hasErrors()){
            ModelAndView model = new ModelAndView("/login/forgotPassword");
            return model;
        }

        Iterable<LoginDetails> details = loginDetailsDAO.findAll();
        for (LoginDetails loginDetails: details
                ) {
            if (token.equals(keyGeneratorService.generate(loginDetails.getPassword()))) {
                if (emailToken.equals(keyGeneratorService.generate(loginDetails.getTherapist().getEmail()))) {
                    String newPassword = bCryptPasswordEncoder.encode(forgotPasswordDTO.getNewPassword());
                    loginDetails.setPassword(newPassword);
                    loginDetailsDAO.save(loginDetails);
                    modelAndView.addObject("information", "Twoje hasło zostało zmienione.");
                    return modelAndView;
                }
            }
        }
        modelAndView.addObject("information","Podany token jest nieaktywny. Spróbuj ponownie.");
        return modelAndView;
    }
}
