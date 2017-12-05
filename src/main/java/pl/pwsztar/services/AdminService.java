package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.daos.EventDAO;
import pl.pwsztar.entities.EventType;
import pl.pwsztar.daos.EventTypeDAO;
import pl.pwsztar.validators.EventTypeValidator;
import pl.pwsztar.daos.TherapistDAO;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminService {

    @Autowired
    TherapistDAO therapistDAO;

    @Autowired
    EventTypeValidator eventTypeValidator;

    @Autowired
    EventTypeDAO eventTypeDAO;

    @Autowired
    EventDAO eventDAO;

    public ModelAndView therapistList(){
        ModelAndView model = new ModelAndView("admin/admin");
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

        ModelAndView model = new ModelAndView("redirect:/admin-event-types");
        Integer eventsNr = eventDAO.findByEventType_EventTypeId(eventTypeId).size();
        if(eventsNr>0){
            session.setAttribute("information","Nie można usunąć typu: '"+eventTypeId +
                    "', ponieważ odnaleziono "+eventsNr+" istniejących wydarzeń tego typu.");
            return model;
        }
        try {
            eventTypeDAO.delete(eventTypeId);
        }catch (Exception e){
            session.setAttribute("information","Nieoczekiwany błąd. Spróbuj pnownie później");
            e.printStackTrace();
        }
        finally {
            return model;
        }
    }
}
