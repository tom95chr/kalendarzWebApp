package pl.pwsztar.entities;

import pl.pwsztar.entities.Event;
import pl.pwsztar.entities.LoginDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "therapist")
public class Therapist {

    private String therapistId;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    private String description;
    private String telephone;
    private String colour;
    private List<Event> events = new ArrayList<Event>();
    private LoginDetails loginDetails;

    @Id
    @Column(name = "THERAPIST_ID")
    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "therapist", orphanRemoval = true)
    @OrderBy("start_date ASC")
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /*@OneToOne(mappedBy = "therapist", fetch = FetchType.EAGER, orphanRemoval = true)*/
    @OneToOne(mappedBy = "therapist", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER, optional = false)
    public LoginDetails getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(LoginDetails loginDetails) {
        this.loginDetails = loginDetails;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}