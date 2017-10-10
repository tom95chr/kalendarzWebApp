package pl.pwsztar.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.therapists.colour.TherapistColour;
import pl.pwsztar.therapists.colour.TherapistColourDAO;

import java.io.IOException;
import java.util.List;


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

    @Autowired
    private TherapistColourDAO therapistColourDAO;

    @RequestMapping(value = "/admin/registration/login-details", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("userForm", new LoginDetails());

        return "admin/registration/loginDetails";
    }
    @RequestMapping(value = "/admin/registration/login-details", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") LoginDetails userForm, BindingResult bindingResult,
                               Model model, String email) {

        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/registration/loginDetails";
        }
        userForm.setEmail(email);
        userForm.setEnabled(Boolean.TRUE);
        loginDetailsDAO.save(userForm);

        return "redirect:/admin/registration/success";
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.GET)
    public String personalData(Model model) {
        model.addAttribute("therapist", new Therapist());
        List<TherapistColour> colours = therapistColourDAO.findAllByTaken(false);
        model.addAttribute("colours",colours);
        return "admin/registration/therapistData";
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public ModelAndView personalData(@ModelAttribute("therapist")Therapist therapist, BindingResult bindingResult,
                                     Model model) throws IOException {
        List<TherapistColour> colours = therapistColourDAO.findAllByTaken(false);
        model.addAttribute("colours",colours);
        therapist.setTherapistId(therapist.getFirstName()+therapist.getLastName());
        if(therapistDAO.findByTherapistId(therapist.getTherapistId())!=null){
            therapist.setTherapistId(therapist.getFirstName()+therapist.getLastName()+"1");
        }
        therapistValidator.validate(therapist, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/registration/therapistData");
        }
        if (registrationService.checkAvailability(therapist.getEmail())) {
            therapist.setGoogleCalendarId(googleCalendar.createCalendar(therapist.getEmail()));
        }
        else{
            return new ModelAndView("redirect:/admin/registration/fail");
        }

        therapistDAO.save(therapist);

        //therapist colour
        TherapistColour therapistColour = therapistColourDAO.findByColourCode(therapist.getColour());
        therapistColour.setTaken(true);
        therapistColourDAO.save(therapistColour);

        String email = therapist.getEmail();
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/registration/login-details");
        modelAndView.addObject("email",email);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/registration/success", method = RequestMethod.GET)
    public String registrationSuccess(Model model) {

        return "admin/registration/success";
    }

    @RequestMapping(value = "/admin/registration/fail", method = RequestMethod.GET)
    public String registrationFail(Model model) {

        return "admin/registration/fail";
    }

    @RequestMapping("admin/therapist-{therapistId}/drop")
    public String dropTherapist(@PathVariable("therapistId") String therapistId){
        Therapist t = therapistDAO.findByTherapistId(therapistId);
        try {
            googleCalendar.deleteCalendar(t.getGoogleCalendarId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        therapistDAO.delete(therapistId);
        loginDetailsDAO.delete(t.getEmail());

        TherapistColour therapistColour = therapistColourDAO.findByColourCode(t.getColour());
        therapistColour.setTaken(false);
        therapistColourDAO.save(therapistColour);
        return "redirect:/admin/therapists";
    }
}