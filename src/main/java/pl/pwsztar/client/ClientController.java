package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.confirmation.ConfirmationCode;

@Controller

public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping("/")
    public ModelAndView therapistsList() {
        return clientService.therapistsList();
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
    public ModelAndView eventReservationPost(@ModelAttribute("client")Client client, BindingResult bindingResult,
                                   @PathVariable("eventId") String eventId) {

        return clientService.eventReservationPost(client,bindingResult,eventId/*,therapistId*/);
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
