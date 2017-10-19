package pl.pwsztar.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.event.eventType.EventType;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.event.eventType.EventTypeValidator;
import pl.pwsztar.therapists.TherapistDAO;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Service
public class AdminService {

    @Autowired
    TherapistDAO therapistDAO;

    @Autowired
    EventTypeValidator eventTypeValidator;

    @Autowired
    EventTypeDAO eventTypeDAO;

    public ModelAndView therapistList(){
        ModelAndView model = new ModelAndView("admin/therapists");
        model.addObject("therapists", therapistDAO.findAll());
        return model;
    }
    public ModelAndView eventTypeSettingsGet(HttpSession session){

        ModelAndView model = new ModelAndView("admin/eventTypes");
        model.addObject("eventTypes",eventTypeDAO.findAll());
        model.addObject("eventType",new EventType());
        model.addObject("info",session.getAttribute("information"));
        session.setAttribute("information",null);
        return model;
    }

    public ModelAndView eventTypeSettingsPost(EventType eventType, BindingResult bindingResult){

        ModelAndView model = new ModelAndView("admin/eventTypes");
        model.addObject("eventTypes",eventTypeDAO.findAll());

        eventTypeValidator.validate(eventType, bindingResult);
        if (bindingResult.hasErrors()) {
            return model;
        }
        eventTypeDAO.save(eventType);
        model.addObject("eventTypes",eventTypeDAO.findAll());
        return model;
    }

    public ModelAndView dropEventType(String eventTypeId, HttpSession session){

        ModelAndView model = new ModelAndView("redirect:/admin/event-types");
        try {
            eventTypeDAO.delete(eventTypeId);
        }catch (RuntimeException e) {
            session.setAttribute("information","Cannot drop type: '"+eventTypeId +
                    "', because some events of this type found");
        }catch (Exception e){
            session.setAttribute("information","Cannot drop this event type. Unexpected error");
            e.printStackTrace();
        }
        finally {
            return model;
        }
    }
}
