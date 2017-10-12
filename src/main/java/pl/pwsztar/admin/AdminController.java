package pl.pwsztar.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.ClientService;

@Controller
public class AdminController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/admin/therapist-{therapistId}/",method = RequestMethod.GET)
    public ModelAndView therapistData(@PathVariable("therapistId") String therapistId) {
        return clientService.therapistData(therapistId);
    }
}
