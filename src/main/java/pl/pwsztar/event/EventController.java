package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;


@Controller
public class EventController {

    @Autowired
    EventDAO eventDAO;
    @Autowired
    EventTypeDAO eventTypeDAO;
    @Autowired
    TherapistDAO therapistDAO;
    @Autowired
    EventService eventService;

    @Autowired
    GoogleCalendar googleCalendar;

    @Autowired
    ReservationDAO reservationDAO;

    @RequestMapping("/therapist-events/createEvent-{user}/")
    public String createEvent(Model model, HttpServletRequest request, @ModelAttribute("eventDto") @Valid EventDTO eventDTO,
                              BindingResult result, @PathVariable("user") String user){
        model.addAttribute("eventTypes", eventTypeDAO.findAll());
        model.addAttribute("therapists",therapistDAO.findAll());

        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {

            Format myGoogleFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //date format chenged to googleDateFormat


            for (int i = 0; i <= eventDTO.getNumberOfRepetitions(); i++){
                String startDate = myGoogleFormat.format(eventDTO.getStartDateTime());
                String endDate   = myGoogleFormat.format(eventDTO.getEndDateTime());
                Event collidedEvent = eventService.detectColisionsByTherapist(eventDTO);

                if (collidedEvent != null){
                    model.addAttribute("collidedEvent",collidedEvent);
                    System.out.println("colision!!!");
                    return "therapist/createEvent";
                }
                else {
                    model.addAttribute("eventCreated","New event/events created successfully !");
                    try {
                        String eventId = googleCalendar.createEvent(therapistDAO.findByEmail(user).getGoogleCalendarId(),
                                "free", "free", startDate + ":59.000+02:00",
                                endDate + ":59.000+02:00");

                        Event event = new Event();
                        event.setEventId(eventId);
                        event.setName("free");
                        event.setStartDateTime(eventDTO.getStartDateTime());
                        event.setEndDateTime(eventDTO.getEndDateTime());
                        event.setTherapist(therapistDAO.findByEmail(user));
                        event.setRoom(eventDTO.getRoom());
                        //event.setConfirmed(false);
                        event.setEventType(eventTypeDAO.findByEventTypeId(eventDTO.getEventType()));
                        event.setFree(Boolean.TRUE);
                        eventDAO.save(event);
                        eventDTO = eventService.addOneWeek(eventDTO);
                    } catch (IOException e) {
                        model.addAttribute("error","Event creation failed");
                        e.printStackTrace();
                        return "therapist/createEvent";
                    }
                    catch (Exception e){
                        model.addAttribute("error","Event creation failed");
                        e.printStackTrace();
                        return "therapist/createEvent";
                    }
                }
            }
        }
        return "therapist/createEvent";
    }
    @RequestMapping("/event-{eventId}/drop")
    public String dropEvent(Model model,  @PathVariable("eventId") String eventId){
        try {
            reservationDAO.deleteReservationsByEvent_EventId(eventId);
            googleCalendar.deleteEvent(eventDAO.findByEventId(eventId).getTherapist().getGoogleCalendarId(), eventId);
            eventDAO.deleteByEventId(eventId);
            return "redirect:/therapist-events";

        } catch (IOException e) {
            return "redirect:/therapist-events";
        }
    }

/*

    @RequestMapping("/event/addEvent-{user}")
    public String formularz(Model model, HttpServletRequest request, @ModelAttribute("eventad") @Valid EventDTO eventDTO,
                            BindingResult result, @PathVariable("user") String user) throws IOException, ParseException,
            InstantiationException, IllegalAccessException {

        model.addAttribute("typee", eventTypeDAO.findAll());
        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {

            Event eve =  eventService.addNewEvent(eventDTO, user);
            if(eve == null){
                return "redirect:/";
            }
            else{
                model.addAttribute("kolidacjapocz", eve);
            }

        }
        return "addEvent";

    }


    @RequestMapping("/event/eventList-{user}")
    public String eventList(Model model,  @PathVariable("user") String user)  {
        model.addAttribute("events", clientService.getSortDates(eventDAO.findByTherapist_TherapistId(user)));
        model.addAttribute("therapist", therapistDAO.findByTherapistId(user));
        return "eventList";
    }


    @RequestMapping("/event/editEvent-{eve.eventId}")
    public String editEvent(HttpServletRequest request, @ModelAttribute("eventadd") @Valid EventDTO eventDTO,
                            BindingResult result, Model model, @PathVariable("eve.eventId") String eventId) throws IOException {
        model.addAttribute("typee", eventTypeDAO.findAll());
        model.addAttribute("event", eventDAO.findByEventId(eventId));
        Event event =   eventDAO.findByEventId(eventId);
        Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
        model.addAttribute("datSt", formatter.format(event.getStartDateTime()));
        model.addAttribute("datEn",  formatter.format(event.getEndDateTime()));

        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {

            Event eve = eventService.checkDates(eventDTO);
            if (eve != null) {
                model.addAttribute("kolidacjapocz", eve);
                return "editEvent";
            } else {
                System.out.print(eventDTO.getName());
                eventService.editEvent(eventDTO, eventId);
                return "redirect:/";

            }


        }
        return "editEvent";
    }



    @RequestMapping("event/delEvent-{eve.eventId}")
    public String eventDel(Model model,  @PathVariable("eve.eventId") String eventId) throws IOException {
        model.addAttribute("event", eventDAO.findByEventId(eventId));
        model.addAttribute("user", eventDAO.findByEventId(eventId).getTherapist().getTherapistId());
        eventService.delEvent(eventId);

        return "delEvent";
    }*/
}







