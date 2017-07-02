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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public String formularz(Model model, HttpServletRequest request, @ModelAttribute("eventad") @Valid EventDTO eventDTO, BindingResult result) throws IOException {

        model.addAttribute("typee", type_eventDAO.findAll());
        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {

        /*     List<Event> eventList = new ArrayList<Event>();
            eventList = eventDAO.findByRoom(eventDTO.getRoom());

            Calendar calEvent = Calendar.getInstance();
            calEvent.setTime(eventDTO.getStartDateTime());

            for (Event eve : eventList) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(eve.getStartDateTime());
                if (eventDTO.getStartDateTime().before(eve.getStartDateTime()) && eventDTO.getEndDateTime().after(eve.getStartDateTime())) {
                    System.out.print("kolidacja");
                } else {
                    System.out.print("jest ok");
                }
*/
                //   if ((cal.get(Calendar.YEAR) == calEvent.get(Calendar.YEAR))  && (cal.get(Calendar.MONTH) == calEvent.get(Calendar.MONTH)) && (cal.get(Calendar.DAY_OF_MONTH) == calEvent.get(Calendar.DAY_OF_MONTH))){
/*
                Date date = eventDTO.getStartDateTime();
                Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
                String dat = formatter.format(date);
                Date date2 = eventDTO.getEndDateTime();
                Format formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                String dat2 = formatter.format(date);
                //qwe terapeute zmienić jak będzie logowanie zrobione
                  googleCalendar.createEvent(googleCalendar.getGoogleCalendarId("asd"),eventDTO.getName(),"busy",dat+":59.000+02:00",dat2+":59.000+02:00");


                Event event = new Event();


                event.setName(eventDTO.getName());
                event.setStartDateTime(eventDTO.getStartDateTime());
                event.setEndDateTime(eventDTO.getEndDateTime());
                event.setRoom(eventDTO.getRoom());
                System.out.print(eventDTO.getTyp() + "asdasads");
                event.setType_Event(type_eventDAO.findByTypeEventId(eventDTO.getTyp()));

                event.setTherapist(therapistDAO.findByTherapistId("asd"));
                event.setConfirmed(true);


                eventDAO.save(event);
*/

                return "redirect:/home2";
            }

            return "addEvent";
        }

    }



