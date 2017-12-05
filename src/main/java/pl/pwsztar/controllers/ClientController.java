package pl.pwsztar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.services.ClientService;
import pl.pwsztar.components.ConfirmationCode;
import pl.pwsztar.validators.ConfirmationCodeValidator;
import pl.pwsztar.forms.ReservationDTO;
import pl.pwsztar.validators.RecaptchaFormValidator;

import javax.servlet.http.HttpSession;

@Controller

public class ClientController {


    @Autowired
    RecaptchaFormValidator recaptchaFormValidator;

    @Autowired
    ClientService clientService;

    @Autowired
    ConfirmationCodeValidator confirmationCodeValidator;
    
    @Autowired
    public ClientController(ClientService clientService, ConfirmationCodeValidator confirmationCodeValidator,
                                RecaptchaFormValidator recaptchaFormValidator) {
        this.clientService = clientService;
        this.confirmationCodeValidator = confirmationCodeValidator;
        this.recaptchaFormValidator = recaptchaFormValidator;
    }

    @ModelAttribute("recaptchaSiteKey")
    public String getRecaptchaSiteKey(@Value("${recaptcha.site-key}") String recaptchaSiteKey) {
        return recaptchaSiteKey;
    }

    @RequestMapping("/")
    public ModelAndView therapistsList(HttpSession session) {
        return clientService.therapistsList(session);
    }

    @RequestMapping(value = "/therapist-{therapistId}",method = RequestMethod.GET)
    public ModelAndView therapistData(@PathVariable("therapistId") String therapistId) {
        return clientService.therapistData(therapistId);
    }

    @RequestMapping(value = "/therapist-event-{eventId}", method = RequestMethod.GET)
    public ModelAndView eventReservationGet(@PathVariable("eventId") String eventId){

        return clientService.eventReservationGet(/*therapistId,*/eventId);
    }

    @RequestMapping(value = "/therapist-event-{eventId}", method = RequestMethod.POST)
    public ModelAndView eventReservationPost(@ModelAttribute("reservationDto")ReservationDTO reservationDTO, BindingResult bindingResult,
                                             @PathVariable("eventId") String eventId) {

        return clientService.eventReservationPost(reservationDTO,bindingResult,eventId/*,therapistId*/);
    }
    @RequestMapping(value = "/confirm-reservation", method = RequestMethod.GET)
    public ModelAndView confirmationGet(){
        return clientService.confirmationGet();
    }

    @RequestMapping(value = "/confirm-reservation", method = RequestMethod.POST)
    public ModelAndView confirmationPost(@ModelAttribute("confirmationCode")ConfirmationCode confirmationCode,
                                   BindingResult bindingResult){

        return clientService.confirmationPost(confirmationCode,bindingResult);
    }
    @RequestMapping(value = "/my-reservation", method = RequestMethod.GET)
    public ModelAndView myReservationGet(){
        return clientService.myReservationGet();
    }

    @RequestMapping(value = "/my-reservation", method = RequestMethod.POST)
    public ModelAndView myReservationPost(@ModelAttribute("confirmationCode")ConfirmationCode confirmationCode,
                                         BindingResult bindingResult){

        return clientService.myReservationPost(confirmationCode,bindingResult);
    }

    @RequestMapping(value = "/my-reservation-{confirmationCode}/cancel",method = RequestMethod.GET)
    public ModelAndView cancelReservation(@PathVariable("confirmationCode") String confirmationCode){
        return clientService.cancelReservation(confirmationCode);
    }
}
