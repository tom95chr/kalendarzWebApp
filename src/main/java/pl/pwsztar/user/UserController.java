package pl.pwsztar.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.therapists.TherapistDAO;

@Controller
public class UserController {

    @Autowired
    TherapistDAO therapistDAO;
    @Autowired
    EventDAO eventDAO;

    @RequestMapping("/")
    public String therapistsList(Model model) {
        model.addAttribute("therapists", therapistDAO.findAll());
        return "home";
    }

    @RequestMapping(value = { "/therapist-{therapistId}/", "/admin/therapist-{therapistId}/"}, method = RequestMethod.GET)
    public String therapistData(@PathVariable("therapistId") String therapistId, Model model) {
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));
        model.addAttribute("events",eventDAO.findByTherapist_TherapistId(therapistId));
        return "user/therapist";
    }
    @RequestMapping("/therapist-{therapistId}/event-{eventId}/")
    public String eventReservation(@PathVariable("eventId") String eventId,
                                   @PathVariable("therapistId") String therapistId,
                                   Model model){
        model.addAttribute("event", eventDAO.findByEventId(eventId));
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));
        return "user/reservation";
    }
}
