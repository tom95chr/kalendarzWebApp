package pl.pwsztar.client;

import javafx.print.Collation;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwsztar.event.Event;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.therapists.TherapistDAO;
import pl.pwsztar.type_event.Type_Event;
import pl.pwsztar.type_event.Type_EventDAO;

import javax.swing.event.DocumentEvent;
import java.util.*;

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
    Type_EventDAO type_eventDAO;

    @Autowired
    ClientService clientService;

    @RequestMapping("/home2")
    public String home(Model model) {
        model.addAttribute("therapists", therapistDAO.findAll());


        return "home2";
    }

    @RequestMapping( value = "/choose-{therapistId}" , method = RequestMethod.GET)
    public String therapistData(@PathVariable("therapistId") String therapistId,
                                Model model) {
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));

        model.addAttribute("events", clientService.getSortDates( eventDAO.findByTherapist_TherapistId(therapistId)));




        return "choose";
    }
}
