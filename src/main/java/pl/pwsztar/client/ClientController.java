package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.reservation.Reservation;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.therapists.colour.TherapistColour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    TherapistDAO therapistDAO;

    @Autowired
    EventDAO eventDAO;

    @Autowired
    ClientValidator clientValidator;

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ReservationDAO reservationDAO;

    @RequestMapping("/")
    public String therapistsList(Model model) {
        model.addAttribute("therapists", therapistDAO.findAll());
        return "home";
    }

    @RequestMapping(value = { "/therapist-{therapistId}/", "/admin/therapist-{therapistId}/"}, method = RequestMethod.GET)
    public String therapistData(@PathVariable("therapistId") String therapistId, Model model) {
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));
        model.addAttribute("events",eventDAO.findByTherapist_TherapistId(therapistId));
        return "client/therapist";
    }

    @RequestMapping(value = "/therapist-{therapistId}/event-{eventId}/", method = RequestMethod.GET)
    public String eventReservation(@PathVariable("eventId") String eventId,
                                   @PathVariable("therapistId") String therapistId,
                                   Model model){
        model.addAttribute("client", new Client());
        model.addAttribute("event", eventDAO.findByEventId(eventId));
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));
        return "client/reservation";
    }
    @RequestMapping(value = "/therapist-{therapistId}/event-{eventId}/", method = RequestMethod.POST)
    public String personalData(@ModelAttribute("client")Client client, BindingResult bindingResult,
                               @PathVariable("eventId") String eventId,
                               @PathVariable("therapistId") String therapistId,
                               Model model) throws IOException {

        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            return ("client/reservation");
        }
        Event event = eventDAO.findByEventId(eventId);
        clientDAO.save(client);
        Reservation rr = new Reservation();
        rr.setClient(client);
        rr.setEvent(event);
        rr.setConfirmed(false);
        reservationDAO.save(rr);
//        Reservation res = reservationDAO.findByClientAndEvent(client,event);
//        res.setConfirmed(true);
//        reservationDAO.save(res);
        return ("redirect:/confirm-reservation");
    }
    @RequestMapping(value = "/confirm-reservation")
    public String confirmation(){
        return "client/confirmation";
    }

}
