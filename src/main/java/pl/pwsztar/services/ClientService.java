package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.validators.ClientValidator;
import pl.pwsztar.components.ConfirmationCode;
import pl.pwsztar.daos.ClientDAO;
import pl.pwsztar.entities.Client;
import pl.pwsztar.validators.ConfirmationCodeValidator;
import pl.pwsztar.entities.Reservation;
import pl.pwsztar.daos.ReservationDAO;
import pl.pwsztar.forms.ReservationDTO;
import pl.pwsztar.entities.Event;
import pl.pwsztar.daos.EventDAO;
import pl.pwsztar.daos.EventTypeDAO;
import pl.pwsztar.daos.LoginDetailsDAO;
import pl.pwsztar.validators.RecaptchaFormValidator;
import pl.pwsztar.daos.TherapistDAO;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
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
    ConfirmationCodeValidator confirmationCodeValidator;

    @Autowired
    KeyGeneratorService keyGeneratorService;

    @Autowired
    EmailService emailService;

    @Autowired
    LoginService loginService;

    @Autowired
    LoginDetailsDAO loginDetailsDAO;

    @Autowired
    RecaptchaFormValidator recaptchaFormValidator;

    @Autowired
    EventService eventService;


    public ModelAndView therapistsList(HttpSession session) {
        ModelAndView model = new ModelAndView("home");
        model.addObject("therapists", therapistDAO.findAllByLoginDetails_UserRole("ROLE_DBA"));
        session.setAttribute("loggedUser",loginService.getPrincipal());
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
        //deleting events older than "now"
        //!!!! ONLY PERFORMED EVENTS ARE STORED IN event_history !!!
        while (it.hasNext()) {
            Event e = it.next();
            if (e.getFree() != Boolean.TRUE || e.getStartDateTime().isBefore(now)) {
                //remove from view list
                it.remove();
            }
            if (e.getStartDateTime().isBefore(now)) {
                eventService.moveToHistory(e);
            }
        }

        model.addObject("events", events);

        return model;
    }

    public ModelAndView eventReservationGet(String eventId) {
        ModelAndView model = new ModelAndView("client/reservation");
        model.addObject("reservationDto", new ReservationDTO());
        model.addObject("event", eventDAO.findByEventId(eventId));
        model.addObject("therapist", therapistDAO.findByTherapistId(eventDAO.findByEventId(eventId)
                .getTherapist().getTherapistId()));
        model.addObject("freeSlots", eventDAO.findByEventId(eventId).getEventType().getSeats()
                - eventDAO.findByEventId(eventId).nrOfParticipants());
        return model;
    }

    public ModelAndView eventReservationPost(ReservationDTO reservationDTO, BindingResult bindingResult,
                                             String eventId) {
        clientValidator.validate(reservationDTO, bindingResult);
        recaptchaFormValidator.validate(reservationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("client/reservation");
            model.addObject("therapist", therapistDAO.findByTherapistId(eventDAO.findByEventId(eventId)
                    .getTherapist().getTherapistId()));
            model.addObject("event", eventDAO.findByEventId(eventId));
            model.addObject("freeSlots", eventDAO.findByEventId(eventId).getEventType().
                    getSeats() - eventDAO.findByEventId(eventId).nrOfParticipants());
            return model;
        }
        Client client = new Client();
        client.setEmail(reservationDTO.getEmail());
        client.setTelephone(reservationDTO.getTelephone());

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
        //plus current participant
        if (event.nrOfParticipants()+1 >= eventTypeDAO.findByEventTypeId(
                event.getEventType().getEventTypeId()).getSeats()) {
            event.setFree(Boolean.FALSE);
            eventDAO.save(event);
        }

        return new ModelAndView("redirect:/confirm-reservation");
    }

    public ModelAndView confirmationGet() {
        ModelAndView model = new ModelAndView("client/confirmation");
        model.addObject("confirmationCode", new ConfirmationCode());
        model.addObject("pageTypeInfo","potwierdzić");
        model.addObject("info2","Potwierdzenie");
        return model;
    }

    public ModelAndView confirmationPost(ConfirmationCode confirmationCode, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView("client/confirmation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);
        recaptchaFormValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
          model.addObject("pageTypeInfo","potwierdzić");
            model.addObject("info2","Potwierdzenie");
            return model;
        }
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode.getCode());


        if (reservation != null && reservation.isConfirmed()) {
            model.addObject("confirmationFailed", new String("Ta rezerwacja została już wcześniej " +
                    "potwierdzona"));
            model.addObject("info2","Potwierdzenie");;

        } else {
            if (reservation != null) {

                //confirmed successfully
                reservation.setConfirmed(true);
                //inform therapist
                emailService.sendEmail(reservation.getEvent().getTherapist().getEmail(),"Nowa rezerwacja",
                        "Witaj. \n Osoba o adresie email: "+reservation.getClient().getEmail()+
                                " Zarezerwowała i potwierdziła swoją obecność na spotkaniu, które odbędzie się dnia: "+reservation.getEvent().getStartDateTime().toLocalDate()
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
                model.addObject("pageTypeInfo","potwierdzić");
                model.addObject("info2","Potwierdzenie");
            }
        }
        return model;
    }

    public ModelAndView myReservationGet() {
        ModelAndView model = new ModelAndView("client/confirmation");
        model.addObject("confirmationCode", new ConfirmationCode());
        model.addObject("pageTypeInfo","edytować");
        model.addObject("info2","Edycja rezerwacji");
        return model;
    }

    public ModelAndView myReservationPost(ConfirmationCode confirmationCode, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView("client/confirmation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
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
            model.addObject("pageTypeInfo","edytować");
            model.addObject("info2","Edycja rezerwacji");
            model.addObject("confirmationFailed", "Nie odnaleziono rezerwacji. Sprawdź" +
                    " swój kod i spróbuj ponownie później");
        }

        return model;
    }

    public ModelAndView cancelReservation(String confirmationCode) {

        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("information","Spotkanie zostało anulowane pomyślnie." +
                " Zapraszamy ponownie");

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
        if (event.nrOfParticipants() < eventTypeDAO.findByEventTypeId(
                event.getEventType().getEventTypeId()).getSeats()) {
            event.setFree(Boolean.TRUE);
            eventDAO.save(event);
        }


        return modelAndView;
    }

}
