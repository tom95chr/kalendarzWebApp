package pl.pwsztar.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.therapists.TherapistDAO;

@Service
public class AdminService {

    @Autowired
    TherapistDAO therapistDAO;

    public ModelAndView therapistList(){
        ModelAndView model = new ModelAndView("admin/therapists");
        model.addObject("therapists", therapistDAO.findAll());
        return model;
    }
}
