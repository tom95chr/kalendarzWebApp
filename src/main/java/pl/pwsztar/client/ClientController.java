package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.therapists.TherapistDAO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Lapek on 22.05.2017.
 */
@Controller
public class ClientController {

    @Autowired
    TherapistDAO therapistDAO;

    @Autowired
    EventDAO eventDAO;


    @Autowired
    ClientService clientService;

    @RequestMapping("/home2")
    public String home(Model model) {
        model.addAttribute("therapists", therapistDAO.findAll());


        return "home2";
    }


    @RequestMapping(value = "/choose-{therapistId}", method = RequestMethod.GET)
    public String therapistData(@PathVariable("therapistId") String therapistId,
                                Model model) {
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));

        model.addAttribute("events", clientService.getSortDates(eventDAO.findByTherapist_TherapistIdAndConfirmedIsTrue(therapistId)));

        return "choose";
    }


    @RequestMapping(value = "/choice-{eventId}")
    public String eventData(HttpServletRequest request, @ModelAttribute("ClientDTO") @Valid ClientDTO clientDTO, BindingResult result, @PathVariable("eventId") String eventId, Model model) throws IOException, ParseException {
        model.addAttribute("event", eventDAO.findByEventId(eventId));
        if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {
            String info = clientService.addClient(clientDTO, eventId);
            if (info == "zapisany") {

                return "redirect:/";
            }
        model.addAttribute("info", info);
        }
        else{
            System.out.print("COS ZSIE ZEL WPISALIII");
        }
        return "choice";
    }
}
