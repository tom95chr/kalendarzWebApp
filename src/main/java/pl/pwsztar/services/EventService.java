package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwsztar.daos.EventDAO;
import pl.pwsztar.daos.ReservationDAO;
import pl.pwsztar.entities.Event;
import pl.pwsztar.entities.EventHistory;
import pl.pwsztar.daos.EventHistoryDAO;

@Service
public class EventService {

    @Autowired
    EventHistoryDAO eventHistoryDAO;

    @Autowired
    EventDAO eventDAO;

    @Autowired
    ReservationDAO reservationDAO;

    public void moveToHistory(Event e){

        EventHistory eventHistory = new EventHistory();
        eventHistory.setEventId(e.getEventId());
        eventHistory.setTherapistEmail(e.getTherapist().getEmail());
        eventHistory.setStartDateTime(e.getStartDateTime());
        eventHistory.setEndDateTime(e.getEndDateTime());
        eventHistory.setRoom(e.getRoom());
        eventHistory.setParticipantsNr(e.nrOfParticipants());
        eventHistory.setTherapistSpecialization(e.getTherapist().getSpecialization());
        //save
        eventHistoryDAO.save(eventHistory);
        //delete event reservations
        reservationDAO.deleteReservationsByEvent_EventId(e.getEventId());
        //delete event
        eventDAO.delete(e);
    }
}
