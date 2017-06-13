package pl.pwsztar.event;

import org.hibernate.annotations.GenericGenerator;
import pl.pwsztar.client.Client;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.type_event.Type_Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lapek on 22.05.2017.
 */
@Entity
@Table(name = "event")
public class Event {

    private String eventId;
    private Date startDateTime;
    private Date endDateTime;
    private String room;
    private Boolean confirmed;
    private Therapist therapist;
    private List<Client> clients = new ArrayList<Client>();
    private Type_Event type_event;
    private String name;

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
    @JoinColumn(name = "type_event_id", nullable = false)
    public Type_Event getType_Event() {
        return type_event;
    }

    public void setType_Event(Type_Event type_event) {
        this.type_event = type_event;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THERAPIST_ID", nullable = false)
    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
     public List<Client> getClients() {
        return clients;
      }

    public void setClients(List<Client> events) {
             this.clients = clients;
           }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }


    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}