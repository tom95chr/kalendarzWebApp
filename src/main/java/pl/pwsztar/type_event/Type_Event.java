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

    private String type_event_id;
    private Integer max_seats;
    private String type;
    private List<Event> events = new ArrayList<Event>();


    @Id
    @Column(name = "type_event_id")
    public String getId_type_event() {
        return type_event_id;
    }

    @OneToMany(mappedBy = "type_event")
    java.awt.List Event;
  //  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type_event")
    //public List<Event> getEvents() {
//        return events;
  //  }

    //public void setEvents(List<Event> events) {
      //  this.events = events;
 //   }


    public void setId_type_event(String id_type_event) {
        this.type_event_id = id_type_event;
    }
    @Column(name ="max_seats")
            public Integer getMax_seats() {
        return max_seats;
    }

    public void setMax_seats(Integer max_seats) {
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
