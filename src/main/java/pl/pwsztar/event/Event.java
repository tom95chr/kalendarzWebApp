package pl.pwsztar.event;

import pl.pwsztar.client.Client;
import pl.pwsztar.therapists.Therapist;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lapek on 22.05.2017.
 */
@Entity
@Table(name = "event")
public class Event {

    private String eventId;
    private Date startDateTime;
    private Date endDateTime;
    private Client client;

    @Id
    @Column(name = "EVENT_ID")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    @Column(name = "START_DATE")
    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }
    @Column(name = "END_DATE")
    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL", nullable = false)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}