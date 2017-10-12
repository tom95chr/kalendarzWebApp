package pl.pwsztar.client;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.confirmation.ConfirmationCode;
import pl.pwsztar.client.confirmation.ConfirmationCodeValidator;
import pl.pwsztar.client.reservation.Reservation;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.services.EmailService;
import pl.pwsztar.services.KeyGenerator;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller

public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping("/")
    public ModelAndView therapistsList() {
        return clientService.therapistsList();
    }

    @RequestMapping(value = "/therapist-{therapistId}/",method = RequestMethod.GET)
    public ModelAndView therapistData(@PathVariable("therapistId") String therapistId) {
        return clientService.therapistData(therapistId);
    }

    @RequestMapping(value = "/therapist-{therapistId}/event-{eventId}/", method = RequestMethod.GET)
    public ModelAndView eventReservationGet(@PathVariable("eventId") String eventId,
                                         @PathVariable("therapistId") String therapistId){

        return clientService.eventReservationGet(therapistId,eventId);
    }

    @RequestMapping(value = "/therapist-{therapistId}/event-{eventId}/", method = RequestMethod.POST)
    public ModelAndView eventReservationPost(@ModelAttribute("client")Client client, BindingResult bindingResult,
                                   @PathVariable("eventId") String eventId,
                                   @PathVariable("therapistId") String therapistId)  {

        return clientService.eventReservationPost(client,bindingResult,eventId,therapistId);
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

}
