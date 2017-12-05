package pl.pwsztar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwsztar.forms.EventDTO;
import pl.pwsztar.forms.EditProfileDTO;
import pl.pwsztar.services.TherapistService;

@Controller
public class TherapistContoller {

    @Autowired
    TherapistService therapistService;

    @RequestMapping(value = "/therapist-events", method = RequestMethod.GET)
    public ModelAndView therapistEventsGet() {
        return therapistService.therapistEventsGet();
    }

    @RequestMapping(value = "/therapist-createEvent",method = RequestMethod.GET)
    public ModelAndView createEventGet(){
        return therapistService.createEventGet();
    }

    @RequestMapping(value = "/therapist-createEvent",method = RequestMethod.POST)
    public ModelAndView createEventPost(@ModelAttribute("eventDTO") EventDTO eventDTO, BindingResult bindingResult){
        return therapistService.createEventPost(eventDTO,bindingResult);
    }
    @RequestMapping("/event-{eventId}/drop")
    public ModelAndView dropEvent(@PathVariable("eventId") String eventId){
        return therapistService.dropEvent(eventId);
    }

    @RequestMapping("/event-{eventId}-participants")
    public ModelAndView eventParticipants(@PathVariable("eventId") String eventId){
        return therapistService.eventParticipants(eventId);
    }

    @RequestMapping(value = "/edit-{eventId}",method = RequestMethod.GET)
    public ModelAndView editEventGet(@PathVariable("eventId") String eventId){
        return therapistService.editEventGet(eventId);
    }

    @RequestMapping(value = "/edit-{eventId}",method = RequestMethod.POST)
    public ModelAndView editEventPost(@ModelAttribute("eventDTO") EventDTO eventDTO, BindingResult bindingResult,
                                      @PathVariable("eventId") String eventId){
        return therapistService.editEventPost(eventId,eventDTO,bindingResult);
    }

    @RequestMapping("/drop-{confirmationCode}")
    public ModelAndView dropParticipant(@PathVariable("confirmationCode") String confirmationCode){
        return therapistService.dropParticipant(confirmationCode);
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
    public ModelAndView editProfileGet(){
        return therapistService.editProfileGet();
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
    public ModelAndView editProfilePost(@ModelAttribute("therapistDTO")EditProfileDTO editProfileDTO, BindingResult bindingResult){
        return therapistService.editProfilePost(editProfileDTO, bindingResult);
    }
}
