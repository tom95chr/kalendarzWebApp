package pl.pwsztar.admin.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.login.LoginValidator;
import pl.pwsztar.mainServices.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.therapists.colour.TherapistColour;
import pl.pwsztar.therapists.colour.TherapistColourDAO;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lapek on 12.07.2017.
 */
@Service
public class RegistrationService {

    @Autowired
    GoogleCalendar googleCalendar;

    @Autowired
    private LoginValidator loginValidator;

    @Autowired
    private LoginDetailsDAO loginDetailsDAO;

    @Autowired
    private TherapistColourDAO therapistColourDAO;

    @Autowired
    private TherapistDAO therapistDAO;

    @Autowired
    private TherapistValidator therapistValidator;

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean checkAvailability(String googleCalendarId) {

        try {
            if (googleCalendar.checkCalendarNameAvailability(googleCalendarId)) {
                return Boolean.TRUE;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    public ModelAndView registrationGet(){
        ModelAndView model = new ModelAndView("admin/registration/loginDetails");
        model.addObject("userForm", new LoginDetails());
        return model;
    }

    public ModelAndView registrationPost(LoginDetails userForm, BindingResult bindingResult, String email) {
        loginValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/registration/loginDetails");
        }
        userForm.setEmail(email);
        userForm.setEnabled(Boolean.TRUE);
        String password = passwordEncoder.encode(userForm.getPassword());
        userForm.setPassword(password);
        loginDetailsDAO.save(userForm);

        return new ModelAndView("redirect:/admin/registration/success");
    }

    public ModelAndView personalDataGet(){
        ModelAndView model = new ModelAndView("admin/registration/therapistData");
        model.addObject("therapist", new Therapist());
        List<TherapistColour> colours = therapistColourDAO.findAllByTaken(false);
        model.addObject("colours",colours);
        return model;
    }

    public ModelAndView personalDataPost(Therapist therapist, BindingResult bindingResult){

        //login
        therapist.setTherapistId(therapist.getFirstName()+therapist.getLastName());
        //if therapist with the same name and surname existed
        if(therapistDAO.findByTherapistId(therapist.getTherapistId())!=null){
            therapist.setTherapistId(therapist.getFirstName()+therapist.getLastName()+"1");
        }
        therapistValidator.validate(therapist, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView m = new ModelAndView("admin/registration/therapistData");
            m.addObject("colours", therapistColourDAO.findAllByTaken(false));
            return m;
        }
        if (checkAvailability(therapist.getEmail())) {
            try{
                therapist.setGoogleCalendarId(googleCalendar.createCalendar(therapist.getEmail()));
            }catch(Exception e){
                e.printStackTrace();
            }
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

    public ModelAndView dropTherapistById(String therapistId){
        ModelAndView model = new ModelAndView("redirect:/admin/therapists");

        Therapist t = therapistDAO.findByTherapistId(therapistId);

        try {
            googleCalendar.deleteCalendar(t.getGoogleCalendarId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(therapistId);
        reservationDAO.deleteReservationsByEvent_Therapist_TherapistId(therapistId);
        therapistDAO.delete(therapistId);
        loginDetailsDAO.delete(t.getEmail());

        TherapistColour therapistColour = therapistColourDAO.findByColourCode(t.getColour());
        therapistColour.setTaken(false);
        therapistColourDAO.save(therapistColour);
        return model;
    }
}