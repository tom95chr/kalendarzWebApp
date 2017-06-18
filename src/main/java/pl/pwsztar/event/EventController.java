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


    @RequestMapping("/event/addEvent")
    public String formularz(Model model, HttpServletRequest request, @ModelAttribute("eventad") @Valid EventDTO eventDTO, BindingResult result) throws IOException, ParseException {

        model.addAttribute("typee", type_eventDAO.findAll());
        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {

            List<Event> eventList = new ArrayList<Event>();
            eventList = eventDAO.findByRoom(eventDTO.getRoom());

            for (Event eve : eventList) {

                if (!((eve.getStartDateTime().before(eventDTO.getStartDateTime()) && (eve.getEndDateTime().before(eventDTO.getStartDateTime()) || eve.getEndDateTime().compareTo(eventDTO.getStartDateTime()) == 0)) || ((eve.getStartDateTime().after(eventDTO.getEndDateTime()) || eve.getStartDateTime().compareTo(eventDTO.getEndDateTime()) == 0) && eve.getEndDateTime().after(eventDTO.getEndDateTime())))) {


                    System.out.print("koliduje z kimś innym ");
                    model.addAttribute("kolidacjapocz", eve.getStartDateTime());
                    model.addAttribute("kolidacjakon", eve.getEndDateTime());
                    model.addAttribute("kolidacjakto", eve.getTherapist().getTherapistId());

                    return "addEvent";

                }
            }
             System.out.print("jestt gites");


                Date date = eventDTO.getStartDateTime();
                Date date2 = eventDTO.getEndDateTime();
                Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
                String dat = formatter.format(date);
                String dat2 = formatter.format(date2);
                //qwe terapeute zmienić jak będzie logowanie zrobione
                 String idEvent =  googleCalendar.createEvent(googleCalendar.getGoogleCalendarId("zxczc"),eventDTO.getName(),"busy",dat+":59.000+02:00",dat2+":59.000+02:00");


                Event event = new Event();
                event.setEventId(idEvent);
                event.setName(eventDTO.getName());
                event.setStartDateTime(eventDTO.getStartDateTime());
                event.setEndDateTime(eventDTO.getEndDateTime());
                event.setRoom(eventDTO.getRoom());

                event.setType_Event(type_eventDAO.findByTypeEventId(eventDTO.getTyp()));

                event.setTherapist(therapistDAO.findByTherapistId("qweqweq"));
                event.setConfirmed(true);


                eventDAO.save(event);
                return "redirect:/home2";

                }

                return "addEvent";
            }


}





