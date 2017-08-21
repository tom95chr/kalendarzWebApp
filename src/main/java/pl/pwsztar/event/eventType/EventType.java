package pl.pwsztar.event.eventType;

import pl.pwsztar.event.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_type")
public class EventType {

    private String eventTypeId;
    private Integer seats;
    private List<pl.pwsztar.event.Event> events = new ArrayList<pl.pwsztar.event.Event>();

    @Id
    @Column(name = "event_type_id")
    public String getEventTypeId() {
        return eventTypeId;
    }


    //java.awt.List Event;

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

    public void setEvents(List<pl.pwsztar.event.Event> events) {
        this.events = events;
    }
}
