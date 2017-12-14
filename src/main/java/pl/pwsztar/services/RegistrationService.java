package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.validators.RegistrationValidator;
import pl.pwsztar.daos.ReservationDAO;
import pl.pwsztar.forms.RegistrationDTO;
import pl.pwsztar.entities.LoginDetails;
import pl.pwsztar.daos.LoginDetailsDAO;
import pl.pwsztar.entities.Therapist;
import pl.pwsztar.daos.TherapistDAO;

@Service
@Transactional
public class RegistrationService {

    @Autowired
    private LoginDetailsDAO loginDetailsDAO;

    @Autowired
    private TherapistDAO therapistDAO;

    @Autowired
    private RegistrationValidator registrationValidator;

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ModelAndView registerGet(){
        ModelAndView model = new ModelAndView("admin/registration/userForm");
        model.addObject("registrationDTO",new RegistrationDTO());
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
        registrationValidator.validate(registrationDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView m = new ModelAndView("admin/registration/userForm");
            return m;
        }

        //therapist
        therapist.setFirstName(registrationDTO.getFirstName());
        therapist.setLastName(registrationDTO.getLastName());
        therapist.setSpecialization(registrationDTO.getSpecialization());
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


        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("information","Użytkownik został utworzony pomyślnie !");
        return modelAndView;
    }

    public ModelAndView dropTherapistById(String therapistId){
        ModelAndView model = new ModelAndView("redirect:/admin");

        Therapist t = therapistDAO.findByTherapistId(therapistId);

        reservationDAO.deleteReservationsByEvent_Therapist_TherapistId(therapistId);
        Therapist therapist = therapistDAO.findByTherapistId(therapistId);
        loginDetailsDAO.delete(therapistId);
        therapistDAO.delete(therapist);

        return model;
    }
}