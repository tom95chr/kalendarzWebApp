package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwsztar.client.ClientService;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.type_event.Type_EventDAO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Agnieszka on 2017-06-11.
 */

@Controller
public class EventController {

    @Autowired
    EventDAO eventDAO;
    @Autowired
    Type_EventDAO type_eventDAO;
    @Autowired
    TherapistDAO therapistDAO;
    @Autowired
    EventService eventService;
    @Autowired
    ClientService clientService;
    @Autowired
    GoogleCalendar googleCalendar;

    @RequestMapping("/event/createEvent-{user}/")
    public String createEvent(Model model, HttpServletRequest request, @ModelAttribute("eventDto") @Valid EventDTO eventDTO,
                              BindingResult result, @PathVariable("user") String user){
        model.addAttribute("eventTypes", type_eventDAO.findAll());
        model.addAttribute("therapists",therapistDAO.findAll());

  /*      System.out.println("user:  "+user);
        System.out.println(therapistDAO.findByTherapistId(user).getGoogleCalendarId());
        googleCalendar.createEvent(therapistDAO.findByTherapistId(user).getGoogleCalendarId()
                ,eventDTO.getName(),"free",);*/
        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {

            Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
            String startDate = formatter.format(eventDTO.getStartDateTime());
            String endDate = formatter.format(eventDTO.getEndDateTime());
            System.out.println("format");
            System.out.println(therapistDAO.findByEmail(user).getGoogleCalendarId());
            System.out.println(eventDTO.getName());
            System.out.println(startDate + ":59.000+02:00");
            System.out.println(endDate + ":59.000+02:00");

            Event collidedEvent = eventService.detectColisionsByTherapist(eventDTO,user);

            if (collidedEvent != null){
                model.addAttribute("collidedEvent",collidedEvent);
                System.out.println("colision!!!");
                return "event/createEvent";
            }
            else {
                model.addAttribute("eventCreated","New event created succesfully !");
                System.out.println("colision free");
            }

            try {
                String eventId = googleCalendar.createEvent(therapistDAO.findByEmail(user).getGoogleCalendarId(),
                        eventDTO.getName(), "free", startDate + ":59.000+02:00",
                        endDate + ":59.000+02:00");

                Event event = new Event();
                event.setEventId(eventId);
                event.setName(eventDTO.getName());
                event.setStartDateTime(eventDTO.getStartDateTime());
                event.setEndDateTime(eventDTO.getEndDateTime());
                event.setTherapist(therapistDAO.findByEmail(user));
                event.setRoom(eventDTO.getRoom());
                event.setConfirmed(false);
                event.setType_Event(type_eventDAO.findByTypeEventId(eventDTO.getEventType()));

                eventDAO.save(event);

            } catch (IOException e) {
                System.out.println("event creation failed");
                model.addAttribute("error","Event creation failed");
                e.printStackTrace();
            }
        }
        return "event/createEvent";
    }

/*

    @RequestMapping("/event/addEvent-{user}")
    public String formularz(Model model, HttpServletRequest request, @ModelAttribute("eventad") @Valid EventDTO eventDTO,
                            BindingResult result, @PathVariable("user") String user) throws IOException, ParseException,
            InstantiationException, IllegalAccessException {

        model.addAttribute("typee", type_eventDAO.findAll());
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
        model.addAttribute("typee", type_eventDAO.findAll());
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







