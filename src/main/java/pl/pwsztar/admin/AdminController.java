package pl.pwsztar.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.client.ClientService;
import pl.pwsztar.login.LoginService;

@Controller
public class AdminController {

    @Autowired
    ClientService clientService;

    @Autowired
    AdminService adminService;

    @Autowired
    LoginService loginService;

    @RequestMapping("/admin/therapists")
    public ModelAndView therapistsListAdmin() {
        return adminService.therapistList();
    }

    @RequestMapping(value = "/admin/therapist-{therapistId}/",method = RequestMethod.GET)
    public ModelAndView therapistData(@PathVariable("therapistId") String therapistId) {
        return clientService.therapistData(therapistId);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", loginService.getPrincipal());
        return "admin/admin";
    }
}
