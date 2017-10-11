package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwsztar.client.confirmation.ConfirmationCode;
import pl.pwsztar.client.confirmation.ConfirmationCodeValidator;
import pl.pwsztar.client.reservation.Reservation;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller
@SessionAttributes("mycounter")
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

    @Autowired
    EventTypeDAO eventTypeDAO;

    @Autowired
    ClientService clientService;

    @Autowired
    GoogleCalendar googleCalendar;

    @Autowired
    ConfirmationCodeValidator confirmationCodeValidator;

    @RequestMapping("/")
    public String therapistsList(Model model) {
        model.addAttribute("therapists", therapistDAO.findAll());
        return "home";
    }

    @RequestMapping(value = { "/therapist-{therapistId}/", "/admin/therapist-{therapistId}/"}, method = RequestMethod.GET)
    public String therapistData(@PathVariable("therapistId") String therapistId, Model model) {
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));
        //getting events by therapist
        List<Event> events = eventDAO.findByTherapist_TherapistIdOrderByStartDateTime(therapistId);
        //removing events where nr. of participants >= free seats
        Iterator<Event> it = events.iterator();
        while (it.hasNext()) {
            Event e = it.next();
            if (e.getFree()!=Boolean.TRUE) {
                it.remove();
            }
        }
        model.addAttribute("events",events);
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
    public String eventReservation(@ModelAttribute("client")Client client, BindingResult bindingResult,
                               @PathVariable("eventId") String eventId,
                               @PathVariable("therapistId") String therapistId,
                               Model model, HttpSession session) throws IOException {

        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            return ("client/reservation");
        }
        Therapist therapist = therapistDAO.findByTherapistId(therapistId);
        Event event = eventDAO.findByEventId(eventId);
        Reservation reservation = reservationDAO.findByClientAndEvent(client,event);

        if(reservation != null){
            model.addAttribute("therapist",therapist);
            model.addAttribute("event",event);
            model.addAttribute("reservation",reservation);
            if (reservation.isConfirmed()){
                                model.addAttribute("information",new String("Your reservation is confirmed"));
                return ("client/details");
            }
            else{
                model.addAttribute("information",new String("You have already reserved this term."));
                session.setAttribute("sesionReservation", reservation);
                session.setAttribute("thrapist", therapist);
                session.setAttribute("event",event);
                return ("redirect:/confirm-reservation");
            }
        }

        clientDAO.save(client);
        Reservation rr = new Reservation();
        rr.setClient(client);
        rr.setEvent(event);
        rr.setConfirmed(false);
        reservationDAO.save(rr);

        //if number of participants is greater than seats then set event type to notFree
        if (clientService.nrOfPaticipants(event) >= eventTypeDAO.findByEventTypeId(
                event.getEventType().getEventTypeId()).getSeats()) {
            event.setFree(Boolean.FALSE);
            googleCalendar.changeEventAvailabilityAndName(therapistDAO.findByTherapistId(therapistId).getEmail(),
                    eventId,"busy","busy");
            eventDAO.save(event);
        }


        return ("redirect:/confirm-reservation");
    }
    @RequestMapping(value = "/confirm-reservation", method = RequestMethod.GET)
    public String confirmation(Model model,HttpSession session){
        model.addAttribute("therapist",(Therapist) session.getAttribute("therapist"));
        model.addAttribute("event",(Event) session.getAttribute("event"));
        model.addAttribute("confirmationCode",new ConfirmationCode());
        return "client/confirmation";
    }
    @RequestMapping(value = "/confirm-reservation", method = RequestMethod.POST)
    public String confirmation(@ModelAttribute("confirmationCode")ConfirmationCode confirmationCode, BindingResult bindingResult,
                               Model model, HttpSession session){
        Reservation r = (Reservation) session.getAttribute("sesionReservation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);
        //confirmation code from email....
        if (bindingResult.hasErrors()) {
            return ("client/confirmation");
        }

        r.setConfirmed(true);
        reservationDAO.save(r);
        return "client/details";
    }

}
