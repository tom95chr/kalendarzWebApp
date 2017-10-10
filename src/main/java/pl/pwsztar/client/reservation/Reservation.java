package pl.pwsztar.client.reservation;

import pl.pwsztar.client.Client;
import pl.pwsztar.event.Event;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "event_reservation")
@IdClass(ReservationId.class)
public class Reservation implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;

    @Id
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Client client;

    @Column(name = "confirmed")
    private boolean confirmed;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}