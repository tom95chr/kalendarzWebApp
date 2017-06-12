package pl.pwsztar.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.type_event.Type_EventDAO;

/**
 * Created by Lapek on 22.05.2017.
 */
@Controller
public class ClientController {

    @Autowired
    EventDAO eventDAO;
    @Autowired
    Type_EventDAO type_eventDAO;

    @RequestMapping("/home2")
    public String home(Model model) {
        model.addAttribute("therapists", "asdada");
        model.addAttribute("event", eventDAO.findAll());
        model.addAttribute("typem", type_eventDAO.findAll());


        return "home2";
    }
}
