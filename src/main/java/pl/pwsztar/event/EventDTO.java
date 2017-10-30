package pl.pwsztar.event;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class EventDTO {

    /*private String name;*/

    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private LocalDateTime endDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private LocalTime startTime;

    private Long duration;

    private String room;

    private String eventType;

    private Integer numberOfRepetitions;

/*    public Date getStartDateTime() {
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
    }*/

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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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
