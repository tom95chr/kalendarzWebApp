package pl.pwsztar.entities;

import pl.pwsztar.components.ReservationId;

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
    private Boolean confirmed;

    @Column(name = "confirmation_code")
    private String confirmationCode;

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

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
