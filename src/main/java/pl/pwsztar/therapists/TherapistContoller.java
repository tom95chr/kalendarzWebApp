package pl.pwsztar.therapists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.EventDTO;
import pl.pwsztar.event.eventType.EventType;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.event.eventType.EventTypeValidator;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.login.LoginService;
import pl.pwsztar.mainServices.googleCalendar.GoogleCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

@Controller
public class TherapistContoller {

    @Autowired
    TherapistService therapistService;

    @RequestMapping(value = "/therapist-events", method = RequestMethod.GET)
    public ModelAndView therapistEventsGet() {
        return therapistService.therapistEventsGet();
    }

    @RequestMapping(value = "/therapist-events/settings", method = RequestMethod.GET)
    public ModelAndView therapistSettingsGet() {
        return  therapistService.therapistSettingsGet();
    }

    @RequestMapping(value = "/therapist-events/settings", method = RequestMethod.POST)
    public ModelAndView therapistSettings(@ModelAttribute("eventType")EventType eventType, BindingResult bindingResult){
        return therapistService.therapistSettingsPost(eventType,bindingResult);
    }

    @RequestMapping("/therapist-events/createEvent-{user}/")
    public ModelAndView createEvent(HttpServletRequest request, @ModelAttribute("eventDto") @Valid EventDTO eventDTO,
                              BindingResult result, @PathVariable("user") String user){
        return therapistService.createEvent(request, eventDTO, result, user);
    }
    @RequestMapping("/event-{eventId}/drop")
    public ModelAndView dropEvent(@PathVariable("eventId") String eventId){
        return therapistService.dropEvent(eventId);
    }
}
