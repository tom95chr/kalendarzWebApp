package pl.pwsztar.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwsztar.client.ClientDAO;
import pl.pwsztar.event.eventType.EventTypeDAO;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by Agnieszka on 2017-06-20.
 */

@Service
public class EventService {
    @Autowired
    EventDAO eventDAO;
    @Autowired
    GoogleCalendar googleCalendar;
    @Autowired
    EventTypeDAO eventTypeDAO;
    @Autowired
    TherapistDAO therapistDAO;
    @Autowired
    ClientDAO clientDAO;

    //returns true if colissions found
    public Event detectColisionsByTherapist(EventDTO eventDTO){
        List<Event> events = eventDAO.findAll();
        //System.out.println("therapistId : "+therapistEmail);
        System.out.println("list size: : "+events.size());

        for (Event event : events) {
         /*   System.out.println("\neventId: "+event.getEventId());
            System.out.println("compare startToStart: "+(event.getStartDateTime().compareTo(eventDTO.getStartDateTime())));
            System.out.println("compare endToStart: "+(event.getEndDateTime().compareTo(eventDTO.getStartDateTime())));
            System.out.println("after :"+(eventDTO.getStartDateTime().after(event.getStartDateTime())));
            System.out.println("before: "+ (eventDTO.getStartDateTime().before(event.getEndDateTime())));
            System.out.println("room: "+event.getRoom().equals(eventDTO.getRoom()));*/

            if (    event.getRoom().equals(eventDTO.getRoom())
                    && (
                        (event.getStartDateTime().compareTo(eventDTO.getStartDateTime()) == 0)
                        || (event.getEndDateTime().compareTo(eventDTO.getStartDateTime()) == 0)
                        || ( (eventDTO.getStartDateTime().after(event.getStartDateTime()))
                            && (eventDTO.getStartDateTime().before(event.getEndDateTime())) ) )
                    ){

                return event;
            }
        }
        return null;
    }

    public EventDTO addOneWeek(EventDTO eventDTO){
        Long start = (eventDTO.getStartDateTime().getTime() + (7 * 24 * 3600 * 1000));
        Long end = (eventDTO.getEndDateTime().getTime() + (7 * 24 * 3600 * 1000));
        eventDTO.setStartDateTime(new Date(start));
        eventDTO.setEndDateTime(new Date(end));

        return eventDTO;
    }
/*
    public Boolean detectColisionsByRoom(EventDTO eventDTO){
        List<Event> events = eventDAO.findByRoom(eventDTO.getRoom());

        for (Event event : events) {

        }
    }*/
/*

    public Event checkDates(EventDTO eventDTO) {

        List<Event> eventList = eventDAO.findByRoom(eventDTO.getRoom());

        for (Event eve : eventList) {

            if (!((eve.getStartDateTime().before(eventDTO.getStartDateTime()) &&
                    (eve.getEndDateTime().before(eventDTO.getStartDateTime()) ||
                            eve.getEndDateTime().compareTo(eventDTO.getStartDateTime()) == 0)) ||
                    ((eve.getStartDateTime().after(eventDTO.getEndDateTime()) ||
                            eve.getStartDateTime().compareTo(eventDTO.getEndDateTime()) == 0) &&
                            eve.getEndDateTime().after(eventDTO.getEndDateTime())))) {
                return eve;
            }
(!((e.s przed d.s)&&e.e przed d.s) ||

        }

        return null;
    }
    public void addEvent(EventDTO eventDTO, String user)  throws IOException, ParseException{

        Date date = eventDTO.getStartDateTime();
        Date date2 = eventDTO.getEndDateTime();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //zmiana formatu daty, zeby pasowała do daty od googla
        String dat = formatter.format(date);
        String dat2 = formatter.format(date2);

        String idEvent = googleCalendar.createEvent(googleCalendar.getGoogleCalendarId(user), eventDTO.getName(), "busy", dat + ":59.000+02:00", dat2 + ":59.000+02:00");


        Event event = new Event();
        event.setEventId(idEvent);

        event.setName(eventDTO.getName());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setEndDateTime(eventDTO.getEndDateTime());
        event.setRoom(eventDTO.getRoom());
        event.setType_Event(eventTypeDAO.findByTypeEventId(eventDTO.getTyp()));
        event.setTherapist(therapistDAO.findByTherapistId(user));
        event.setConfirmed(true);


        eventDAO.save(event);
    }

    public void addOneWeek(EventDTO eventDTO){
        Long datt = (eventDTO.getStartDateTime().getTime() + (7 * 24 * 3600 * 1000));
        Long datt2 = (eventDTO.getEndDateTime().getTime() + (7 * 24 * 3600 * 1000));
        eventDTO.setStartDateTime(new Date(datt));
        eventDTO.setEndDateTime(new Date(datt2));

    }


    public Event addNewEvent(EventDTO eventDTO, String user) throws IOException, ParseException, IllegalAccessException, InstantiationException {


        if(eventDTO.getCykli().equals("nie")) {
            Event eve = checkDates(eventDTO);
            if (eve == null) {
                addEvent(eventDTO, user);
                return null;
            }
            else {
                return eve;
            }


        }
        else{
                 List<Date> StartDate = new ArrayList<Date>();
                    List<Date> EndDate = new ArrayList<Date>();
                 int i =0;

                while(eventDTO.getStartDateTime().before(eventDTO.getEndDateCykl())){
                    Event eve = checkDates(eventDTO);
                    if(eve==null) {
                       StartDate.add(i, eventDTO.getStartDateTime());
                       EndDate.add(i,eventDTO.getEndDateTime());
                       addOneWeek(eventDTO);
                    }
                    else{
                        return eve;
                    }

                }
            for(int j =0; j< StartDate.size();j++) {

                eventDTO.setStartDateTime(StartDate.get(j));
                eventDTO.setEndDateTime(EndDate.get(j));
               addEvent(eventDTO,user);
            }

            }
                return null;
        }



    public void editEvent(EventDTO eventDTO, String eventId) throws IOException {
        System.out.println("user");



        Event event = eventDAO.findByEventId(eventId);
        event.setName(eventDTO.getName());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setEndDateTime(eventDTO.getEndDateTime());
        event.setRoom(eventDTO.getRoom());
        event.setType_Event(eventTypeDAO.findByTypeEventId(eventDTO.getTyp()));

        event.setConfirmed(eventDTO.getConfirmed());

        eventDAO.save(event);
        googleCalendar.editEventGoogle(event);


    }

    public void delEvent(String eventId) throws IOException {
        googleCalendar.deleteEvent(eventDAO.findByEventId(eventId).getTherapist().getGoogleCalendarId(), eventId);
        clientDAO.deleteAllByEvent(eventDAO.findByEventId(eventId));
        eventDAO.deleteByEventId(eventId);
    }*/
}
