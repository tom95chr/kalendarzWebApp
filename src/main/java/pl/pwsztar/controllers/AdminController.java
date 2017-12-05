package pl.pwsztar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.services.AdminService;
import pl.pwsztar.services.ClientService;
import pl.pwsztar.entities.EventType;
import pl.pwsztar.services.LoginService;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    ClientService clientService;

    @Autowired
    AdminService adminService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/admin/therapist-{therapistId}/",method = RequestMethod.GET)
    public ModelAndView therapistData(@PathVariable("therapistId") String therapistId) {
        return clientService.therapistData(therapistId);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage(ModelMap model) {
        model.addAttribute("user", loginService.getPrincipal());
        return adminService.therapistList();
    }

    @RequestMapping(value = "/admin-event-types", method = RequestMethod.GET)
    public ModelAndView therapistSettingsGet(HttpSession session) {
        return  adminService.eventTypeSettingsGet(session);
    }

    @RequestMapping(value = "/admin-event-types", method = RequestMethod.POST)
    public ModelAndView therapistSettings(@ModelAttribute("eventType")EventType eventType, BindingResult bindingResult){
        return adminService.eventTypeSettingsPost(eventType,bindingResult);
    }
    @RequestMapping("/admin/event-types-{eventTypeId}/drop")
    public ModelAndView dropEventType(@PathVariable("eventTypeId") String eventTypeId, HttpSession session){
        return adminService.dropEventType(eventTypeId,session);
    }
}
