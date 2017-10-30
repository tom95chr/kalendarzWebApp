package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.confirmation.ConfirmationCode;
import pl.pwsztar.client.confirmation.ConfirmationCodeValidator;
import pl.pwsztar.client.reservation.Reservation;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.login.LoginService;
import pl.pwsztar.mainServices.EmailService;
import pl.pwsztar.client.reservation.KeyGeneratorService;
import pl.pwsztar.mainServices.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ClientService {

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
    KeyGeneratorService keyGeneratorService;

    @Autowired
    EmailService emailService;

    @Autowired
    LoginService loginService;

    public int nrOfParticipants(Event e) {
        List<Reservation> listOfParticipants = reservationDAO.findAllByEvent(e);
        return listOfParticipants.size();
    }

    public ModelAndView therapistsList(HttpSession session) {
        ModelAndView model = new ModelAndView("home");
        model.addObject("therapists", therapistDAO.findAll());
        session.setAttribute("loggedUser",loginService.getPrincipal());
        System.out.println(session.getAttribute("loggedUser"));
        return model;
    }

    public ModelAndView therapistData(String therapistId) {
        ModelAndView model = new ModelAndView("client/therapist");

        model.addObject("therapist", therapistDAO.findByTherapistId(therapistId));
        //getting events by therapist
        List<Event> events = eventDAO.findByTherapist_TherapistIdOrderByStartDateTime(therapistId);
        //removing events where nr. of participants >= free seats
        Iterator<Event> it = events.iterator();
        LocalDateTime now = LocalDateTime.now();
        while (it.hasNext()) {
            Event e = it.next();
            if (e.getFree() != Boolean.TRUE || e.getStartDateTime().isBefore(now)) {
                it.remove();
            }
        }

        //calculating duration
        List<Long> duration = new ArrayList<>();
        for (Event e : events
                ) {
            duration.add(ChronoUnit.MINUTES.between(e.getStartDateTime(),e.getEndDateTime()));
        }
        model.addObject("duration",duration);
        model.addObject("events", events);

        return model;
    }

    public ModelAndView eventReservationGet(String eventId) {
        ModelAndView model = new ModelAndView("client/reservation");
        model.addObject("client", new Client());
        model.addObject("event", eventDAO.findByEventId(eventId));
        model.addObject("therapist", therapistDAO.findByTherapistId(eventDAO.findByEventId(eventId)
                .getTherapist().getTherapistId()));
        model.addObject("freeSlots", eventDAO.findByEventId(eventId).getEventType().getSeats()
                - nrOfParticipants(eventDAO.findByEventId(eventId)));
        return model;
    }

    public ModelAndView eventReservationPost(Client client, BindingResult bindingResult,
                                             String eventId) {
        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("client/reservation");
            model.addObject("therapist", therapistDAO.findByTherapistId(eventDAO.findByEventId(eventId)
                    .getTherapist().getTherapistId()));
            model.addObject("event", eventDAO.findByEventId(eventId));
            model.addObject("freeSlots", eventDAO.findByEventId(eventId).getEventType().
                    getSeats() - nrOfParticipants(eventDAO.findByEventId(eventId)));
            return model;
        }
        clientDAO.save(client);
        Event event = eventDAO.findByEventId(eventId);
        Reservation reservation = reservationDAO.findByClientAndEvent(client, event);

        //if event already reserved by this client
        if (reservation != null) {

            if (reservation.isConfirmed()) {
                ModelAndView model = new ModelAndView("client/details");
                model.addObject("information", new String("Twoja rezerwacja została już wcześniej" +
                        " potwierdzona."));
                model.addObject("therapist", therapistDAO.findByTherapistId(eventDAO.findByEventId(eventId)
                        .getTherapist().getTherapistId()));
                model.addObject("event", eventDAO.findByEventId(eventId));
                model.addObject("confirmationCode",eventDAO.findByEventId(eventId));
                return model;
            } else {
                return new ModelAndView("redirect:/confirm-reservation");
            }
        } else {
            //generate confirmationCode
            String key = keyGeneratorService.generate(eventId + (client.getEmail()));
            //send key to client's email
            String text1 = "Gratulacje. Udało Ci się zarezerwować termin spotkania.<br>"+event.getTherapist().getSpecialization()+
                    " "+event.getTherapist().getFirstName()+" "+event.getTherapist().getLastName()+". <br>Data: " +
                    event.getStartDateTime().toLocalDate()+" godz. " +event.getStartDateTime().toLocalTime()+"<br>Sala nr: "+event.getRoom()+
                    "<br><br>Teraz prosimy o potwierdzenie obecności. <br> ";
            String text2 = "Oto Twój kod potwierdzenia: ";
            String text3 = "Aby potwierdzić swoją obecność przejdź na ";
            String confirmPageUrl = "http://localhost:8080/confirm-reservation";
            String text4 = " i wprowadź swój kod rezerwacji. W przeciwnym razie Twoja rezerwacja zostanie automatycznie usunięta.";
            String confirmationLinkName = "stronę potwierdzenia";
            emailService.sendHtmlEmail(client.getEmail(), "Potwierdź swoją rezerwację",text1,text2,text3,
                    confirmPageUrl,text4,confirmationLinkName, key);
            Reservation rr = new Reservation();
            rr.setClient(client);
            rr.setEvent(event);
            rr.setConfirmed(false);
            rr.setConfirmationCode(key);
            reservationDAO.save(rr);

        }
        //if number of participants is greater than seats then set event free to busy(false)
        if (clientService.nrOfParticipants(event) >= eventTypeDAO.findByEventTypeId(
                event.getEventType().getEventTypeId()).getSeats()) {
            event.setFree(Boolean.FALSE);
            try {
                googleCalendar.changeEventAvailabilityAndName(therapistDAO.findByTherapistId(event.getTherapist().
                                getTherapistId()).getEmail(),
                        event.getEventId(), "busy", "zajety");
            } catch (Exception e) {
                e.printStackTrace();
            }
            eventDAO.save(event);
        }

        return new ModelAndView("redirect:/confirm-reservation");
    }

    public ModelAndView confirmationGet() {
        ModelAndView model = new ModelAndView("client/confirmation");
        model.addObject("confirmationCode", new ConfirmationCode());
        model.addObject("info1","Twój unikalny kod wysłaliśmy na podany przy rezerwacji email.");
        model.addObject("pageTypeInfo","potwierdzić");
        model.addObject("info2","Potwierdzenie");
        return model;
    }

    public ModelAndView confirmationPost(ConfirmationCode confirmationCode, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView("client/confirmation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addObject("info1","Twój unikalny kod wysłaliśmy na podany przy rezerwacji email.");
            model.addObject("pageTypeInfo","potwierdzić");
            model.addObject("info2","Potwierdzenie");
            return model;
        }
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode.getCode());


        if (reservation != null && reservation.isConfirmed()) {
            model.addObject("confirmationFailed", new String("Ta rezerwacja została już wcześniej " +
                    "potwierdzona"));
            model.addObject("info1","Twój unikalny kod wysłaliśmy na podany przy rezerwacji email.");
            model.addObject("pageTypeInfo","potwierdzić");
            model.addObject("info2","Potwierdzenie");;

        } else {
            if (reservation != null) {

                //confirmed successfully
                reservation.setConfirmed(true);
                //inform therapist
                emailService.sendEmail(reservation.getEvent().getTherapist().getEmail(),"Nowa rezerwacja",
                        "Witaj. \n Osoba o adresie email: "+reservation.getClient().getEmail()+
                                " Zarezerwowała i potwierdziła swoją obecność na spotkaniu dnia: "+reservation.getEvent().getStartDateTime().toLocalDate()
                                +" o godzinie "+reservation.getEvent().getStartDateTime().toLocalTime()+"\nTyp spotkania: "
                                +reservation.getEvent().getEventType().getEventTypeId());
                reservationDAO.save(reservation);
                ModelAndView model2 = new ModelAndView("client/details");
                model2.addObject("information", new String("Rezerwacja potwierdzona"));
                model2.addObject("therapist", therapistDAO.findByTherapistId(reservation.getEvent().
                        getTherapist().getTherapistId()));
                model2.addObject("event", eventDAO.findByEventId(reservation.getEvent().getEventId()));
                model2.addObject("confirmationCode",reservation.getConfirmationCode());
                return model2;
            } else {
                model.addObject("confirmationFailed", "Nie odnaleziono rezerwacji. Sprawdź" +
                        " swój kod i spróbuj ponownie później");
                model.addObject("info1","Twój unikalny kod wysłaliśmy na podany przy rezerwacji email.");
                model.addObject("pageTypeInfo","potwierdzić");
                model.addObject("info2","Potwierdzenie");
            }
        }
        return model;
    }

    public ModelAndView myReservationGet() {
        ModelAndView model = new ModelAndView("client/confirmation");
        model.addObject("confirmationCode", new ConfirmationCode());
        model.addObject("info1","Twój unikalny kod wysłaliśmy na podany przy rezerwacji email.");
        model.addObject("pageTypeInfo","edytować");
        model.addObject("info2","Edycja rezerwacji");
        return model;
    }

    public ModelAndView myReservationPost(ConfirmationCode confirmationCode, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView("client/confirmation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addObject("info1","Twój unikalny kod wysłaliśmy na podany przy rezerwacji email.");
            model.addObject("pageTypeInfo","edytować");
            model.addObject("info2","Edycja rezerwacji");
            return model;
        }
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode.getCode());


        if (reservation != null) {

            ModelAndView model2 = new ModelAndView("client/details");
            model2.addObject("information", new String("Reservation confirmed successfully"));
            model2.addObject("therapist", therapistDAO.findByTherapistId(reservation.getEvent().getTherapist().getTherapistId()));
            model2.addObject("event", eventDAO.findByEventId(reservation.getEvent().getEventId()));
            model2.addObject("confirmationCode",reservation.getConfirmationCode());
            return model2;
        } else {
            model.addObject("info1","Twój unikalny kod wysłaliśmy na podany przy rezerwacji email.");
            model.addObject("pageTypeInfo","edytować");
            model.addObject("info2","Edycja rezerwacji");
            model.addObject("confirmationFailed", "Nie odnaleziono rezerwacji. Sprawdź" +
                    " swój kod i spróbuj ponownie później");
        }

        return model;
    }

    public ModelAndView cancelReservation(String confirmationCode) {

        ModelAndView modelAndView = new ModelAndView("client/cancelled");

        Reservation r = reservationDAO.findByConfirmationCode(confirmationCode);
        Event event = r.getEvent();

        //inform therapist
        emailService.sendEmail(r.getEvent().getTherapist().getEmail(),"Odwołano rezerwację",
                "Witaj. \n Osoba o adresie email: "+r.getClient().getEmail()+
                        " ODWOŁAŁA REZERWACJĘ z dnia: "+r.getEvent().getStartDateTime().toLocalDate()
                        +" godz. "+r.getEvent().getStartDateTime().toLocalTime()+"\nTyp spotkania: "
                        +r.getEvent().getEventType().getEventTypeId());

        //delete reservation
        reservationDAO.deleteReservationsByConfirmationCode(confirmationCode);
        //if number of participants is greater than seats then set event free to busy(false)
        if (clientService.nrOfParticipants(event) < eventTypeDAO.findByEventTypeId(
                event.getEventType().getEventTypeId()).getSeats()) {
            event.setFree(Boolean.TRUE);
            try {
                googleCalendar.changeEventAvailabilityAndName(therapistDAO.findByTherapistId(event.getTherapist().
                                getTherapistId()).getEmail(),
                        event.getEventId(), "free", "wolny");
            } catch (Exception e) {
                e.printStackTrace();
            }
            eventDAO.save(event);
        }


        return modelAndView;
    }

}
