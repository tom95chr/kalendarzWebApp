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
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.therapists.colour.TherapistColour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Autowired
    EventTypeDAO eventTypeDAO;

    @Autowired
    ClientService clientService;

    @Autowired
    GoogleCalendar googleCalendar;

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
    public String personalData(@ModelAttribute("client")Client client, BindingResult bindingResult,
                               @PathVariable("eventId") String eventId,
                               @PathVariable("therapistId") String therapistId,
                               Model model) throws IOException {

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
                return ("client/confirmation");
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
