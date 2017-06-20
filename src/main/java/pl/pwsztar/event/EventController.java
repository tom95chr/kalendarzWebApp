package pl.pwsztar.event;

import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
    GoogleCalendar googleCalendar;
    @Autowired
    EventDAO eventDAO;
    @Autowired
    Type_EventDAO type_eventDAO;
    @Autowired
    TherapistDAO therapistDAO;

    @Autowired
    EventService eventService;


    @RequestMapping("/event/addEvent")
    public String formularz(Model model, HttpServletRequest request, @ModelAttribute("eventad") @Valid EventDTO eventDTO, BindingResult result) throws IOException, ParseException {

        model.addAttribute("typee", type_eventDAO.findAll());
        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {


            Event eve = eventService.checkDates(eventDTO);
            if (eve != null) {
                model.addAttribute("kolidacjapocz", eve);
                return "addEvent";
            } else {

                Date date = eventDTO.getStartDateTime();
                Date date2 = eventDTO.getEndDateTime();
                Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
                String dat = formatter.format(date);
                String dat2 = formatter.format(date2);
                //qwe terapeute zmienić jak będzie logowanie zrobione
                String idEvent = googleCalendar.createEvent(googleCalendar.getGoogleCalendarId("zxczc"), eventDTO.getName(), "busy", dat + ":59.000+02:00", dat2 + ":59.000+02:00");


                Event event = new Event();
                event.setEventId(idEvent);
                event.setName(eventDTO.getName());
                event.setStartDateTime(eventDTO.getStartDateTime());
                event.setEndDateTime(eventDTO.getEndDateTime());
                event.setRoom(eventDTO.getRoom());
                event.setType_Event(type_eventDAO.findByTypeEventId(eventDTO.getTyp()));
                event.setTherapist(therapistDAO.findByTherapistId("zxczc"));
                event.setConfirmed(true);


                eventDAO.save(event);
                return "redirect:/home2";

            }


        }
        return "addEvent";

    }
}





