package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
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
import pl.pwsztar.mainServices.EmailService;
import pl.pwsztar.client.reservation.KeyGeneratorService;
import pl.pwsztar.mainServices.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;

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

    public int nrOfParticipants(Event e){
        List<Reservation> listOfParticipants = reservationDAO.findAllByEvent(e);
        return listOfParticipants.size();
    }

    public ModelAndView therapistsList(){
        ModelAndView model = new ModelAndView("home");
        model.addObject("therapists", therapistDAO.findAll());
        return model;
    }
    public ModelAndView therapistData(String therapistId){
        ModelAndView model = new ModelAndView("client/therapist");

        model.addObject("therapist", therapistDAO.findByTherapistId(therapistId));
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
        model.addObject("events",events);

        return model;
    }

    public ModelAndView eventReservationGet(String therapistId, String eventId){
        ModelAndView model = new ModelAndView("client/reservation");
        model.addObject("client", new Client());
        model.addObject("event", eventDAO.findByEventId(eventId));
        model.addObject("therapist", therapistDAO.findByTherapistId(therapistId));
        model.addObject("freeSlots",eventDAO.findByEventId(eventId).getEventType().getSeats()
                - nrOfParticipants(eventDAO.findByEventId(eventId)));
        return model;
    }

    public ModelAndView eventReservationPost(Client client, BindingResult bindingResult,
                                             String eventId, String therapistId){
        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("client/reservation");
            model.addObject("therapist",therapistDAO.findByTherapistId(therapistId));
            model.addObject("event",eventDAO.findByEventId(eventId));
            model.addObject("freeSlots",eventDAO.findByEventId(eventId).getEventType().
                    getSeats() - nrOfParticipants(eventDAO.findByEventId(eventId)));
            return model;
        }
        clientDAO.save(client);
        Event event = eventDAO.findByEventId(eventId);
        Reservation reservation = reservationDAO.findByClientAndEvent(client,event);

        //if event already reserved by this client
        if(reservation != null){

            if (reservation.isConfirmed()){
                ModelAndView model = new ModelAndView("client/details");
                model.addObject("information",new String("Your reservation has been already confirmed"));
                model.addObject("therapist",therapistDAO.findByTherapistId(therapistId));
                model.addObject("event",eventDAO.findByEventId(eventId));
                return model;
            }
            else{
                return new ModelAndView("redirect:/confirm-reservation");
            }
        }
        else{
            //generate confirmationCode
            String key = keyGeneratorService.generate(eventId+(client.getEmail()));
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
        if (clientService.nrOfParticipants(event) >= eventTypeDAO.findByEventTypeId(
                event.getEventType().getEventTypeId()).getSeats()) {
            event.setFree(Boolean.FALSE);
            try{
                googleCalendar.changeEventAvailabilityAndName(therapistDAO.findByTherapistId(therapistId).getEmail(),
                        eventId,"busy","busy");
            }catch(Exception e){
                e.printStackTrace();
            }
            eventDAO.save(event);
        }

        return new ModelAndView("redirect:/confirm-reservation");
    }

    public ModelAndView confirmationGet(){
        ModelAndView model = new ModelAndView("client/confirmation");
        model.addObject("confirmationCode",new ConfirmationCode());
        return model;
    }

    public ModelAndView confirmationPost(ConfirmationCode confirmationCode, BindingResult bindingResult){

        ModelAndView model = new ModelAndView("client/confirmation");
        confirmationCodeValidator.validate(confirmationCode, bindingResult);

        if (bindingResult.hasErrors()) {
            return model;
        }
        Reservation reservation = reservationDAO.findByConfirmationCode(confirmationCode.getCode());


        if(reservation!=null && reservation.isConfirmed()){
            model.addObject("confirmationFailed", new String("This reservation has been already confirmed"));

        }
        else {
            if (reservation != null) {
                reservation.setConfirmed(true);
                reservationDAO.save(reservation);
                ModelAndView model2 = new ModelAndView("client/details");
                model2.addObject("information", new String("Reservation confirmed successfully"));
                model2.addObject("therapist", therapistDAO.findByTherapistId(reservation.getEvent().getTherapist().getTherapistId()));
                model2.addObject("event", eventDAO.findByEventId(reservation.getEvent().getEventId()));
                return model2;
            }
            else{
                model.addObject("confirmationFailed","Reservation not found, check your reservation code and try again");
            }
        }
        return model;
    }
}
