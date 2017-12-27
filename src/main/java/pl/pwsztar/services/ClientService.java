package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${site.url}")
    String siteUrl;

    public ModelAndView therapistsList(HttpSession session) {
        ModelAndView model = new ModelAndView("home");
        model.addObject("therapists", therapistDAO.findAllByLoginDetails_UserRole("ROLE_DBA"));
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
        //10 minutes added for lazy students :>
        // (when he will by late to event and want to check event details)
        now = now.plusMinutes(10);
        //time before eventStart when clients are not allowed to make reservation
        LocalDateTime nowPlus = now.plusHours(24);
        //deleting events older than "now"
        //!!!! ONLY PERFORMED EVENTS ARE STORED IN event_history !!!
        while (it.hasNext()) {
            Event e = it.next();
            if (e.getFree() != Boolean.TRUE || e.getStartDateTime().isBefore(nowPlus)) {
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
                model.addObject("information", "Twoja rezerwacja została już wcześniej" +
                        " potwierdzona.");
                model.addObject("therapist", therapistDAO.findByTherapistId(eventDAO.findByEventId(eventId)
                        .getTherapist().getTherapistId()));
                model.addObject("event", eventDAO.findByEventId(eventId));
                model.addObject("confirmationCode", eventDAO.findByEventId(eventId));
                return model;
            } else {
                return new ModelAndView("redirect:/confirm-reservation");
            }
        } else {
            //generate confirmationCode
            String key = keyGeneratorService.generate(eventId + (client.getEmail()));
            //save reservation
            Reservation rr = new Reservation();
            rr.setClient(client);
            rr.setEvent(event);
            rr.setConfirmed(false);
            rr.setConfirmationCode(key);
            reservationDAO.save(rr);
            //send confirmation email to client
            String confirmPageUrl = siteUrl+"confirm-"+key;
            emailService.sendHtmlEmail(rr, client.getEmail(), 'n', confirmPageUrl);

        }
        //if number of participants is greater than seats then set event free to busy(false)
        //plus current participant
        if (event.nrOfParticipants() + 1 >= eventTypeDAO.findByEventTypeId(
                event.getEventType().getEventTypeId()).getSeats()) {
            event.setFree(Boolean.FALSE);
            eventDAO.save(event);
        }

        return new ModelAndView("redirect:/confirm-reservation");
    }

    public ModelAndView confirmationGet() {
        ModelAndView model = new ModelAndView("client/confirmation");
        model.addObject("confirmationCode", new ConfirmationCode());
        model.addObject("pageTypeInfo", "potwierdzić");
        model.addObject("info2", "Potwierdzenie");
        return model;
    }

    public ModelAndView confirmationPost(ConfirmationCode confirmationCode, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView("client/confirmation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);
        recaptchaFormValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addObject("pageTypeInfo", "potwierdzić");
            model.addObject("info2", "Potwierdzenie");
            return model;
        }
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode.getCode());


        if (reservation != null && reservation.isConfirmed()) {
            model.addObject("confirmationFailed","Ta rezerwacja została już wcześniej " +
                    "potwierdzona");
            model.addObject("info2", "Potwierdzenie");

        } else {
            if (reservation != null) {

                //confirmed successfully
                reservation.setConfirmed(true);
                //inform therapist
                emailService.sendEmail(reservation.getEvent().getTherapist().getEmail(), "Nowa rezerwacja",
                        "Witaj. \nOsoba o adresie email: " + reservation.getClient().getEmail() +
                                " numerze telefonu: " + reservation.getClient().getTelephone() +
                                " Zarezerwowała i potwierdziła swoją obecność na spotkaniu, które odbędzie się dnia: " +
                                reservation.getEvent().getStartDateTime().toLocalDate() + " o godzinie " +
                                reservation.getEvent().getStartDateTime().toLocalTime() + "\nTyp spotkania: "
                                + reservation.getEvent().getEventType().getEventTypeId());

                //save reservation
                reservationDAO.save(reservation);
                ModelAndView model2 = new ModelAndView("client/details");
                model2.addObject("information", "Rezerwacja potwierdzona");
                model2.addObject("therapist", therapistDAO.findByTherapistId(reservation.getEvent().
                        getTherapist().getTherapistId()));
                model2.addObject("event", eventDAO.findByEventId(reservation.getEvent().getEventId()));
                model2.addObject("confirmationCode", reservation.getConfirmationCode());
                return model2;
            } else {
                model.addObject("confirmationFailed", "Nie odnaleziono rezerwacji. Sprawdź" +
                        " swój kod i spróbuj ponownie później");
                model.addObject("pageTypeInfo", "potwierdzić");
                model.addObject("info2", "Potwierdzenie");
            }
        }
        return model;
    }

    public ModelAndView myReservationGet() {
        ModelAndView model = new ModelAndView("client/confirmation");
        model.addObject("confirmationCode", new ConfirmationCode());
        model.addObject("pageTypeInfo", "podglądnąć");
        model.addObject("info2", "Podgląd rezerwacji");
        return model;
    }

    public ModelAndView myReservationPost(ConfirmationCode confirmationCode, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView("client/confirmation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addObject("pageTypeInfo", "podglądnąć");
            model.addObject("info2", "Podgląd rezerwacji");
            return model;
        }
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode.getCode());


        if (reservation != null) {

            ModelAndView model2 = new ModelAndView("client/details");
            model2.addObject("information","Reservation confirmed successfully");
            model2.addObject("therapist", therapistDAO.findByTherapistId(reservation.getEvent().getTherapist().getTherapistId()));
            model2.addObject("event", eventDAO.findByEventId(reservation.getEvent().getEventId()));
            model2.addObject("confirmationCode", reservation.getConfirmationCode());
            return model2;
        } else {
            model.addObject("pageTypeInfo", "podglądnąć");
            model.addObject("info2", "Podgląd rezerwacji");
            model.addObject("confirmationFailed", "Nie odnaleziono rezerwacji. Sprawdź" +
                    " swój kod i spróbuj ponownie później");
        }

        return model;
    }

    public ModelAndView cancelReservation(String confirmationCode) {

        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("information", "Spotkanie zostało anulowane pomyślnie." +
                " Zapraszamy ponownie");

        Reservation r = reservationDAO.findByConfirmationCode(confirmationCode);
        Event event = r.getEvent();

        //inform therapist
        emailService.sendEmail(r.getEvent().getTherapist().getEmail(), "Odwołano rezerwację",
                "Witaj. \nOsoba o adresie email: " + r.getClient().getEmail() +
                        " numerze telefonu: " + r.getClient().getTelephone() +
                        " ODWOŁAŁA REZERWACJĘ z dnia: " + r.getEvent().getStartDateTime().toLocalDate()
                        + " godz. " + r.getEvent().getStartDateTime().toLocalTime() + "\nTyp spotkania: "
                        + r.getEvent().getEventType().getEventTypeId());

        //inform client
        emailService.sendHtmlEmail(r, r.getClient().getEmail(), 'c', "");
        //delete reservation
        reservationDAO.deleteReservationsByConfirmationCode(confirmationCode);
        //if number of participants is greater than seats then set event free to busy(false)
        if (event.nrOfParticipants() - 1 < event.getEventType().getSeats()){
            event.setFree(Boolean.TRUE);
            eventDAO.save(event);
        }

        return modelAndView;
    }

    public ModelAndView confirmReservationByLink(String confirmationCode) {
        ModelAndView model = new ModelAndView("client/details");
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode);

        if (reservation == null) {
            ModelAndView modelAndView = new ModelAndView("result");
            modelAndView.addObject("information", "Nie możemy potwierdzić tej rezerwacji, " +
                    "ponieważ nie odnaleziono rezerwacji. Została ona usunięta lub termin spotkania już minął.");
            return modelAndView;
        } else {
            if (!reservation.isConfirmed()) {
                reservation.setConfirmed(Boolean.TRUE);
            }
            //inform therapist
            emailService.sendEmail(reservation.getEvent().getTherapist().getEmail(), "Nowa rezerwacja",
                    "Witaj. \nOsoba o adresie email: " + reservation.getClient().getEmail() +
                            " numerze telefonu: " + reservation.getClient().getTelephone() +
                            " Zarezerwowała i potwierdziła swoją obecność na spotkaniu, które odbędzie się dnia: " +
                            reservation.getEvent().getStartDateTime().toLocalDate() + " o godzinie " +
                            reservation.getEvent().getStartDateTime().toLocalTime() + "\nTyp spotkania: "
                            + reservation.getEvent().getEventType().getEventTypeId());
            model.addObject("information", ("Rezerwacja potwierdzona"));
            model.addObject("therapist", therapistDAO.findByTherapistId(reservation.getEvent().
                    getTherapist().getTherapistId()));
            model.addObject("event", eventDAO.findByEventId(reservation.getEvent().getEventId()));
            model.addObject("confirmationCode", reservation.getConfirmationCode());
            return model;
        }
    }
}