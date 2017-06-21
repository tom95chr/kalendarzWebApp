package pl.pwsztar.event;

import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwsztar.client.ClientService;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.type_event.Type_Event;
import pl.pwsztar.type_event.Type_EventDAO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @RequestMapping("/event/addEvent-{user}")
    public String formularz(Model model, HttpServletRequest request, @ModelAttribute("eventad") @Valid EventDTO eventDTO, BindingResult result, @PathVariable("user") String user) throws IOException, ParseException {

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
    public String editEvent( HttpServletRequest request, @ModelAttribute("eventadd") @Valid EventDTO eventDTO, BindingResult result, Model model,  @PathVariable("eve.eventId") String eventId) throws IOException {
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
}







