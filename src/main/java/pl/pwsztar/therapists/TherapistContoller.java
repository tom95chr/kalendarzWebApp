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
import pl.pwsztar.client.confirmation.ConfirmationCode;
import pl.pwsztar.client.reservation.ReservationDAO;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.EventDTO;
import pl.pwsztar.event.eventType.EventType;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.event.eventType.EventTypeValidator;
import pl.pwsztar.login.LoginDetails;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.login.LoginService;
import pl.pwsztar.mainServices.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.editProfile.EditProfileDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class TherapistContoller {

    @Autowired
    TherapistService therapistService;

    @RequestMapping(value = "/therapist-events", method = RequestMethod.GET)
    public ModelAndView therapistEventsGet() {
        return therapistService.therapistEventsGet();
    }

    @RequestMapping(value = "/therapist-createEvent",method = RequestMethod.GET)
    public ModelAndView createEventGet(){
        return therapistService.createEventGet();
    }

    @RequestMapping(value = "/therapist-createEvent",method = RequestMethod.POST)
    public ModelAndView createEventPost(@ModelAttribute("eventDTO") EventDTO eventDTO, BindingResult bindingResult){
        return therapistService.createEventPost(eventDTO,bindingResult);
    }
    @RequestMapping("/event-{eventId}/drop")
    public ModelAndView dropEvent(@PathVariable("eventId") String eventId){
        return therapistService.dropEvent(eventId);
    }

    @RequestMapping("/therapist-events-event-{eventId}-participants")
    public ModelAndView eventParticipants(@PathVariable("eventId") String eventId){
        return therapistService.eventParticipants(eventId);
    }

    @RequestMapping(value = "/therapist-events/event-{eventId}/edit",method = RequestMethod.GET)
    public ModelAndView editEventGet(@PathVariable("eventId") String eventId){
        return therapistService.editEventGet(eventId);
    }

    @RequestMapping(value = "/therapist-events/event-{eventId}/edit",method = RequestMethod.POST)
    public ModelAndView editEventPost(@ModelAttribute("eventDTO") EventDTO eventDTO, BindingResult bindingResult,
                                      @PathVariable("eventId") String eventId){
        return therapistService.editEventPost(eventId,eventDTO,bindingResult);
    }

    @RequestMapping("/drop-{confirmationCode}")
    public ModelAndView dropParticipant(@PathVariable("confirmationCode") String confirmationCode){
        return therapistService.dropParticipant(confirmationCode);
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
    public ModelAndView editProfileGet(){
        return therapistService.editProfileGet();
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
    public ModelAndView editProfilePost(@ModelAttribute("therapistDTO")EditProfileDTO editProfileDTO, BindingResult bindingResult){
        return therapistService.editProfilePost(editProfileDTO, bindingResult);
    }
}
