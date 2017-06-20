package pl.pwsztar.type_event;

import pl.pwsztar.event.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agnieszka on 2017-06-11.
 */
@Entity
@Table(name = "type_event")
public class Type_Event {

    private String typeEventId;
    private Integer max_seats;
    private String type;
    private List<Event> events = new ArrayList<Event>();


    @Id
    @Column(name = "type_event_id")
    public String getTypeEventId() {
        return typeEventId;
    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type_event")
   java.awt.List Event;
//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type_event")
//   public List<Event> getEvents() {
   //  return events;
 // }

 //   public void setEvents(List<Event> events) {
   //     this.events = events;
  //  }


    public void setTypeEventId(String idTypeEvent) {
        this.typeEventId = idTypeEvent;
    }
    @Column(name ="max_seats")
            public Integer getMaxSeats() {
        return max_seats;
    }

    public void setMaxSeats(Integer max_seats) {
        this.max_seats = max_seats;
    }
    @Column(name ="type")
            public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
