package pl.pwsztar.admin.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.login.LoginDetailsDAO;
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
@Transactional
public class RegistrationService {

    @Autowired
    GoogleCalendar googleCalendar;

    @Autowired
    private LoginDetailsDAO loginDetailsDAO;

    @Autowired
    private TherapistColourDAO therapistColourDAO;

    @Autowired
    private TherapistDAO therapistDAO;

    @Autowired
    private ReservationValidator reservationValidator;

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

    public ModelAndView registerGet(){
        ModelAndView model = new ModelAndView("admin/registration/userForm");
        model.addObject("registrationDTO",new RegistrationDTO());
        List<TherapistColour> colours = therapistColourDAO.findAllByTaken(false);
        model.addObject("colours",colours);
        return model;
    }

    public ModelAndView registerPost(RegistrationDTO registrationDTO, BindingResult bindingResult){

        Therapist therapist = new Therapist();
        LoginDetails loginDetails = new LoginDetails();

        //login
        String id = registrationDTO.getFirstName()+registrationDTO.getLastName();
        therapist.setTherapistId(id.replaceAll("\\s",""));

        //if therapist with the same name and surname existed
        if(therapistDAO.findByTherapistId(therapist.getTherapistId())!=null){
            therapist.setTherapistId(registrationDTO.getFirstName()+registrationDTO.getLastName()+"1");
        }
        reservationValidator.validate(registrationDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView m = new ModelAndView("admin/registration/userForm");
            m.addObject("colours", therapistColourDAO.findAllByTaken(false));
            return m;
        }
        //google
        if (checkAvailability(therapist.getEmail())) {
            try{
                therapist.setGoogleCalendarId(googleCalendar.createCalendar(registrationDTO.getEmail()));
            }catch(Exception e){
                ModelAndView modelAndView = new ModelAndView("admin/registration/result");
                modelAndView.addObject("information","Wystapił błąd. Nie utworzono użytkownika.");
                return modelAndView;
            }
        }
        else{
            ModelAndView modelAndView = new ModelAndView("admin/registration/result");
            modelAndView.addObject("information","Wystapił błąd. Nie utworzono użytkownika.");
            return modelAndView;
        }

        //therapist
        therapist.setFirstName(registrationDTO.getFirstName());
        therapist.setLastName(registrationDTO.getLastName());
        therapist.setSpecialization(registrationDTO.getSpecialization());
        therapist.setColour(registrationDTO.getColour());
        therapist.setDescription(registrationDTO.getDescription());
        therapist.setEmail(registrationDTO.getEmail());
        therapist.setTelephone(registrationDTO.getTelephone());
        therapist.setLoginDetails(loginDetails);
        /*//therapistDAO.save(therapist);*/

        //login details
        /*loginDetails.setEmail(registrationDTO.getEmail());*/
        loginDetails.setEnabled(Boolean.TRUE);
        String password = passwordEncoder.encode(registrationDTO.getPassword());
        loginDetails.setPassword(password);
        loginDetails.setUserRole(registrationDTO.getUserRole());
        loginDetails.setTherapist(therapist);
        loginDetailsDAO.save(loginDetails);

        //therapist colour
        TherapistColour therapistColour = therapistColourDAO.findByColourCode(registrationDTO.getColour());
        therapistColour.setTaken(true);
        therapistColourDAO.save(therapistColour);

        ModelAndView modelAndView = new ModelAndView("admin/registration/result");
        modelAndView.addObject("information","Użytkownik został utworzony pomyślnie !");
        return modelAndView;
    }

    public ModelAndView dropTherapistById(String therapistId){
        ModelAndView model = new ModelAndView("redirect:/admin");

        Therapist t = therapistDAO.findByTherapistId(therapistId);

        try {
            googleCalendar.deleteCalendar(t.getGoogleCalendarId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        reservationDAO.deleteReservationsByEvent_Therapist_TherapistId(therapistId);
        Therapist therapist = therapistDAO.findByTherapistId(therapistId);
        therapist.setLoginDetails(loginDetailsDAO.findByEmail(therapistId));
        therapistDAO.delete(therapist);


        TherapistColour therapistColour = therapistColourDAO.findByColourCode(t.getColour());
        therapistColour.setTaken(false);
        therapistColourDAO.save(therapistColour);
        return model;
    }
}