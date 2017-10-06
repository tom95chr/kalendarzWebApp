package pl.pwsztar.therapists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwsztar.event.EventDAO;
import pl.pwsztar.event.eventType.EventType;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.event.eventType.EventTypeValidator;
import pl.pwsztar.login.LoginDetailsDAO;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;


@Controller
public class TherapistContoller {

    @Autowired
    TherapistDAO therapistDAO;

    @Autowired
    GoogleCalendar googleCalendar;

    @Autowired
    LoginDetailsDAO loginDetailsDAO;

    @Autowired
    EventDAO eventDAO;

    @Autowired
    EventTypeDAO eventTypeDAO;

    @Autowired
    EventTypeValidator eventTypeValidator;

    @RequestMapping("/")
    public String therapistsList(Model model) {
        model.addAttribute("therapists", therapistDAO.findAll());
        return "home";
    }
    @RequestMapping("/admin/therapists")
    public String therapistsListAdmin(Model model) {
        model.addAttribute("therapists", therapistDAO.findAll());

        return "admin/therapists";
    }

    @RequestMapping(value = { "/therapist-{therapistId}/", "/admin/therapist-{therapistId}/"}, method = RequestMethod.GET)
    public String therapistData(@PathVariable("therapistId") String therapistId,
                                Model model) {
        model.addAttribute("therapist", therapistDAO.findByTherapistId(therapistId));

        return "therapist";
    }

    @RequestMapping(value = "/therapist-events", method = RequestMethod.GET)
    public String dbaPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        model.addAttribute("events",eventDAO.findByTherapist_Email(getPrincipal()));
        model.addAttribute("therapists",therapistDAO.findAll());
        return "therapist/therapistEvents";
    }

    @RequestMapping(value = "/therapist-events/settings", method = RequestMethod.GET)
    public String therapistSettings(Model model) {
        model.addAttribute("eventTypes",eventTypeDAO.findAll());
        model.addAttribute("eventType",new EventType());
        return "therapist/settings";
    }

    @RequestMapping(value = "/therapist-events/settings", method = RequestMethod.POST)
    public String therapistSettings(Model model, @ModelAttribute("eventType")EventType eventType,
                                    BindingResult bindingResult){

        model.addAttribute("eventTypes",eventTypeDAO.findAll());

        eventTypeValidator.validate(eventType, bindingResult);
        if (bindingResult.hasErrors()) {
            return ("event/settings");
        }
        eventTypeDAO.save(eventType);
        return ("redirect:/therapist-events/settings");
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    /*
        @RequestMapping("admin/therapists/add")
        public String addTherapist(HttpServletRequest request, @ModelAttribute("therapistDto") @Valid TherapistDTO therapistDto,
                                   BindingResult result) {

            if (request.getMethod().equalsIgnoreCase("post") && !result.hasErrors()) {
                Therapist therapist = new Therapist();

                therapist.setTherapistId(therapistDto.getTherapistId());
                therapist.setFirstName(therapistDto.getFirstName());
                therapist.setLastName(therapistDto.getLastName());
                therapist.setSpecialization(therapistDto.getSpecialization());
                therapist.setEmail(therapistDto.getEmail());
                therapist.setTelephone(therapistDto.getTelephone());
                therapist.setDescription(therapistDto.getDescription());

                String googleCalendarId = "";

                try {
                    if (googleCalendar.checkCalendarNameAvailability(therapist.getTherapistId())){
                        googleCalendarId = googleCalendar.createCalendar(therapist.getTherapistId());
                        System.out.println("New calendar created");
                    }
                    else {
                        System.out.println("This login is already existing");
                        return "redirect:/therapists/add";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                therapist.setGoogleCalendarId(googleCalendarId);

                System.out.println("ID: "+therapist.getTherapistId()
                        +"\nname: "+therapist.getLastName()
                        +"\nlast name: "+therapist.getSpecialization()
                        +"\nemail: "+therapist.getEmail()
                        +"\ntelephone: "+therapist.getTelephone()
                        +"\ndescription: "+therapist.getDescription()
                        +"\ngoogleCalendarId: "+therapist.getGoogleCalendarId()
                );

                therapistDAO.save(therapist);

                LoginDetails user = new LoginDetails();
                user.setEmail(therapistDto.getEmail());
                user.setPassword("ęąć");
                user.setEnabled(Boolean.TRUE);
                System.out.println("Enabled: "+ user.getEnabled());
                user.setUserRole("ROLE_DBA");
                loginDetailsDAO.save(user);
                return "redirect:/admin/therapists";
            }
            return "add";
        }

    @RequestMapping("admin/therapist-{therapistId}/drop")
    public String dropTherapist(@PathVariable("therapistId") String therapistId){
        try {
            googleCalendar.deleteCalendar(googleCalendar.getGoogleCalendarId(therapistId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        therapistDAO.delete(therapistId);
        return "redirect:/";
    }
    */
}
