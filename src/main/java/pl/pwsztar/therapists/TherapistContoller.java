package pl.pwsztar.therapists;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TherapistContoller {


    @RequestMapping(value = "/therapist1")
    public String therapist1() {
        return "therapist1";
    }

    @RequestMapping("/therapists")
    public String therapistList(Model model) {
        //model.addAttribute("therapists", kotDAO.findAll());
        return "therapists";
    }


}