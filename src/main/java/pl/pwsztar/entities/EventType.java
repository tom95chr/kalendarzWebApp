package pl.pwsztar.entities;

import pl.pwsztar.entities.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_type")
public class EventType {

    private String eventTypeId;
    private Integer seats;
    private List<Event> events = new ArrayList<Event>();

    @Id
    @Column(name = "event_type_id")
    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String idTypeEvent) {
        this.eventTypeId = idTypeEvent;
    }

    @Column(name ="seats")
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "eventType")
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
