package pl.pwsztar.client;

import pl.pwsztar.event.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lapek on 22.05.2017.
 */

@Entity
@Table(name = "client")
public class Client {

    private String email;
    private String telephone;
    private List<Event> events = new ArrayList<Event>();

    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}