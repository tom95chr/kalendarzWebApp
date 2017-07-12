package pl.pwsztar.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;


/**
 * Created by Lapek on 11.07.2017.
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private LoginDetailsDAO loginDetailsDAO;

    @Autowired
    private TherapistDAO therapistDAO;

    @Autowired
    private TherapistValidator therapistValidator;

    @Autowired
    private GoogleCalendar googleCalendar;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/admin/registration/login-details", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new LoginDetails());

        return "registration/loginDetails";
    }
    @RequestMapping(value = "/admin/registration/login-details", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") LoginDetails userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration/loginDetails";
        }

        userForm.setEnabled(Boolean.TRUE);
        loginDetailsDAO.save(userForm);

        return "redirect:/admin/registration/success";
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.GET)
    public String personalData(Model model) {
        model.addAttribute("therapist", new Therapist());

        return "registration/therapistData";
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public String personalData(@ModelAttribute("therapist")Therapist therapist, BindingResult bindingResult, Model model) {
        therapistValidator.validate(therapist, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration/therapistData";
        }
        if (registrationService.checkGoogleCalendarNameAvailability(therapist.getEmail())) {
            therapist.setGoogleCalendarId(therapist.getEmail());
        }
        else{
            return "redirect:/admin/registration/fail";
        }

        therapist.setTherapistId(therapist.getEmail());
        therapistDAO.save(therapist);

        return "redirect:/admin/registration/login-details";
    }

    @RequestMapping(value = "/admin/registration/success", method = RequestMethod.GET)
    public String registrationSuccess(Model model) {

        return "registration/success";
    }
}
