package pl.pwsztar.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.pwsztar.entities.Client;
import pl.pwsztar.entities.Reservation;
import pl.pwsztar.entities.Event;

import java.util.List;

public interface ReservationDAO extends CrudRepository<Reservation, String> {
    Reservation findByClientAndEvent(Client client, Event event);
    List<Reservation> findAllByEvent(Event event);
    Reservation findByConfirmationCode(String code);

    @Transactional
    void deleteReservationsByEvent_EventId(String eventId);

    @Transactional
    void deleteReservationsByEvent_Therapist_TherapistId(String therapistId);

    @Transactional
    void deleteReservationsByConfirmationCode(String confirmationCode);
}