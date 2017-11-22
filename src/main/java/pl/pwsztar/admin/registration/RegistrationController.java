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

    @RequestMapping(value = "/admin-registration", method = RequestMethod.GET)
    public ModelAndView personalDataGet() {
        return registrationService.registerGet();
    }

    @RequestMapping(value = "/admin-registration", method = RequestMethod.POST)
    public ModelAndView personalData(@ModelAttribute("registrationDTO")RegistrationDTO registrationDTO,
                                     BindingResult bindingResult) {
        return registrationService.registerPost(registrationDTO,bindingResult);
    }

    @RequestMapping("admin/therapist-{therapistId}/drop")
    public ModelAndView dropTherapist(@PathVariable("therapistId") String therapistId){
        return registrationService.dropTherapistById(therapistId);
    }
}