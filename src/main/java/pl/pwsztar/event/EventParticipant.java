package pl.pwsztar.event;

public class EventParticipant {
    private Event event;
    private Integer participants;

    public EventParticipant(Event e, Integer p){
        this.event = e;
        this.participants = p;
    }
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
}
