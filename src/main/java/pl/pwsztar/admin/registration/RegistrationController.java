package pl.pwsztar.admin.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.therapists.Therapist;


/**
 * Created by Lapek on 11.07.2017.
 */

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/admin/registration/login-details", method = RequestMethod.GET)
    public ModelAndView registrationGet(){
        return registrationService.registrationGet();
    }
    @RequestMapping(value = "/admin/registration/login-details", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("userForm") LoginDetails userForm, BindingResult bindingResult,
                                     String email) {
        return registrationService.registrationPost(userForm,bindingResult,email);
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.GET)
    public ModelAndView personalDataGet() {
        return registrationService.personalDataGet();
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public ModelAndView personalData(@ModelAttribute("therapist")Therapist therapist, BindingResult bindingResult) {
        return registrationService.personalDataPost(therapist,bindingResult);
    }

    @RequestMapping(value = "/admin/registration/success", method = RequestMethod.GET)
    public ModelAndView registrationSuccess() {
        return new ModelAndView("admin/registration/success");
    }

    @RequestMapping(value = "/admin/registration/fail", method = RequestMethod.GET)
    public ModelAndView registrationFail() {
        return new ModelAndView("admin/registration/fail");
    }

    @RequestMapping("admin/therapist-{therapistId}/drop")
    public ModelAndView dropTherapist(@PathVariable("therapistId") String therapistId){
        return registrationService.dropTherapistById(therapistId);
    }
}