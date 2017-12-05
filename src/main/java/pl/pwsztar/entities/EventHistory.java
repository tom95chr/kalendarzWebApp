package pl.pwsztar.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_history")
public class EventHistory {

    private String eventId;
    private String therapistEmail;
    private String therapistSpecialization;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String room;
    private Integer participantsNr;


    @Id
    @Column(name = "EVENT_ID")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Column(name = "THERAPIST_EMAIL")
    public String getTherapistEmail() {
        return therapistEmail;
    }

    public void setTherapistEmail(String therapistEmail) {
        this.therapistEmail = therapistEmail;
    }

    @Column(name = "THERAPIST_SPECIALIZATION")
    public String getTherapistSpecialization() {
        return therapistSpecialization;
    }

    public void setTherapistSpecialization(String therapistSpecialization) {
        this.therapistSpecialization = therapistSpecialization;
    }

    @Column(name = "START_DATE_TIME")
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Column(name = "END_DATE_TIME")
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Column(name = "PARTICIPANTS_NR")
    public Integer getParticipantsNr() {
        return participantsNr;
    }

    public void setParticipantsNr(Integer participantsNr) {
        this.participantsNr = participantsNr;
    }

}
