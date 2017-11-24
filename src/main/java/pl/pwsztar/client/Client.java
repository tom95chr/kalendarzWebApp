package pl.pwsztar.client;

import pl.pwsztar.client.reservation.Reservation;
import pl.pwsztar.event.Event;
import pl.pwsztar.recaptcha.RecaptchaForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {

    private String email;
    private String telephone;
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @Id
    @Column(name = "email")
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

    @OneToMany(mappedBy = "client")
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}