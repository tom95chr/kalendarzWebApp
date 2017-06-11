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
import java.util.Date;

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


    @RequestMapping("/event/addEvent")
    public String formularz(HttpServletRequest request, @ModelAttribute("eventad") @Valid EventDTO eventDTO, BindingResult result) throws IOException {

        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {


            Date date =eventDTO.getStartDateTime();
            Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
            String dat = formatter.format(date);
            Date date2 =eventDTO.getEndDateTime();
            Format formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            String dat2 = formatter.format(date);
            //qwe terapeute zmienić jak będzie logowanie zrobione
       //  googleCalendar.createEvent(googleCalendar.getGoogleCalendarId("qwe"),eventDTO.getName(),"busy",dat+":59.000+02:00",dat2+":59.000+02:00");
           // Kotek kotek = new Kotek( form.getImie() , form.getEmail());
          //  kotDAO.save(kotek);

            System.out.print("asdasdasd1231232131232421");
System.out.print(eventDTO.getTyp() + "asdadsadasdasdasd");

            return "redirect:/home";
        }

        return "addEvent";
    }

}
