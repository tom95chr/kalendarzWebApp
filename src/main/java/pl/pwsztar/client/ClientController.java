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

    @Autowired
    KeyGenerator keyGenerator;

    @Autowired
    EmailService emailService;

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
                                   Model model) throws IOException {

        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            return ("client/reservation");
        }
        clientDAO.save(client);
        Event event = eventDAO.findByEventId(eventId);
        Reservation reservation = reservationDAO.findByClientAndEvent(client,event);

        //if event already reserved by this client
        if(reservation != null){

            if (reservation.isConfirmed()){
                model.addAttribute("information",new String("Your reservation has been already confirmed"));
                model.addAttribute("therapist",therapistDAO.findByTherapistId(therapistId));
                model.addAttribute("event",eventDAO.findByEventId(eventId));
                return ("client/details");
            }
            else{
                String s = reservationDAO.findByClientAndEvent(client,event).getConfirmationCode();
                System.out.println("conf-code: "+s);
                return ("redirect:/confirm-reservation");
            }
        }
        else{
            //generate confirmationCode
            String key = keyGenerator.generate(eventId+(client.getEmail()));
            //send key to client's email
            emailService.sendEmail(client.getEmail(),"Kod potwierdzenia","Twoj kod to: "+key);
            Reservation rr = new Reservation();
            rr.setClient(client);
            rr.setEvent(event);
            rr.setConfirmed(false);
            rr.setConfirmationCode(key);
            reservationDAO.save(rr);
        }

        //if number of participants is greater than seats then set event type to busy(false)
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
        model.addAttribute("confirmationCode",new ConfirmationCode());
        return "client/confirmation";
    }
    @RequestMapping(value = "/confirm-reservation", method = RequestMethod.POST)
    public String confirmation(@ModelAttribute("confirmationCode")ConfirmationCode confirmationCode, BindingResult bindingResult,
                               Model model){
        confirmationCodeValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
            return ("client/confirmation");
        }
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode.getCode());


        if(reservation!=null && reservation.isConfirmed()){
            model.addAttribute("confirmationFailed", new String("This reservation has been already confirmed"));
        }
        else {
            if (reservation != null) {
                reservation.setConfirmed(true);
                reservationDAO.save(reservation);
                model.addAttribute("information", new String("Reservation confirmed successfully"));
                model.addAttribute("therapist", therapistDAO.findByTherapistId(reservation.getEvent().getTherapist().getTherapistId()));
                model.addAttribute("event", eventDAO.findByEventId(reservation.getEvent().getEventId()));
                return "client/details";
            }
            else{
                model.addAttribute("confirmationFailed","Reservation not found, check your reservation code and try again");
            }
        }

        return ("client/confirmation");
    }

}
