package pl.pwsztar.event;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class EventDTO {
    @NotEmpty(message = "Wprowadz nazwę ")
    private String name;
    //@NotBlank(message = "Wprowadz date rozpoczecia")
    //@NotEmpty
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private Date startDateTime;
    //@NotBlank(message = "Wprowadz date zakonczenia")
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private Date endDateTime;
    @NotEmpty
    private String room;
    @NotEmpty
    private String eventType;

    private Integer numberOfRepetitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    public void setNumberOfRepetitions(Integer numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }
}





















/*
package pl.pwsztar.event;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.event.eventType.EventType;

import java.util.Date;


*/
/**
 * Created by Agnieszka on 2017-06-11.
 *//*

public class EventDTO {
    private String eventId;
    //@NotBlank(message = "Wprowadz date")
    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private Date startDateTime;

    //@NotBlank(message = "Wprowadz date")
    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private Date endDateTime;
    private String room;
    private Boolean confirmed;
    private Therapist therapist;
    private EventType eventType;
    @NotBlank(message = "Wprowadz nazwę ")
    private String name;
    @NotEmpty
    private String typ; //do povierania id type, zeby wsadzic do jsp
    private String cykli;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDateCykl;

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
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

    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }

    public EventType getType_event() {
        return eventType;
    }

    public void setType_event(EventType eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }


    public String getCykli() {
        return cykli;
    }

    public void setCykli(String cykli) {
        this.cykli = cykli;
    }

    public Date getEndDateCykl() {
        return endDateCykl;
    }

    public void setEndDateCykl(Date endDateCykl) {
        this.endDateCykl = endDateCykl;
    }
}
*/
