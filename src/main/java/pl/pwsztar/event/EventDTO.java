package pl.pwsztar.event;

import org.springframework.format.annotation.DateTimeFormat;
import pl.pwsztar.therapists.Therapist;
import pl.pwsztar.type_event.Type_Event;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;


/**
 * Created by Agnieszka on 2017-06-11.
 */
public class EventDTO {
    private String eventId;
    @NotBlank(message = "Wprowadz date")
    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private Date startDateTime;

    @NotBlank(message = "Wprowadz date")
    @DateTimeFormat(pattern = "dd-MM-yyyy' 'HH:mm")
    private Date endDateTime;
    private String room;
    private Boolean confirmed;
    private Therapist therapist;
    private Type_Event type_event;
    @NotBlank(message = "Wprowadz nazwę ")
    private String name;
    @NotEmpty
    private String typ; //do povierania id type, zeby wsadzic do jsp


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

    public Type_Event getType_event() {
        return type_event;
    }

    public void setType_event(Type_Event type_event) {
        this.type_event = type_event;
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


}
