package pl.pwsztar.client;

import pl.pwsztar.event.Event;

import javax.persistence.*;

/**
 * Created by Lapek on 22.05.2017.
 */

@Entity
@Table(name = "client")
public class Client {

    private String email;
    private String telephone;
    private Event event;

    private Integer client_id;
    @Id
    @Column(name = "CLIENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }


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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", nullable = false)
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


}