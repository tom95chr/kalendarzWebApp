package pl.pwsztar;

import org.junit.Assert;
import org.junit.Test;
import pl.pwsztar.entities.Event;

import java.time.LocalDateTime;

public class EventTest {

    @Test
    public void calculateDurationWhenParamsAreOk() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusMinutes(30);
        Event event = new Event();
        event.setStartDateTime(start);
        event.setEndDateTime(end);
        Assert.assertEquals("duration = 30 min", new Long(30), event.calculateDuration());
    }

    @Test
    public void calculateDurationWhenParamsAreReversed() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusMinutes(30);
        Event event = new Event();
        event.setStartDateTime(end);
        event.setEndDateTime(start);
        Assert.assertEquals("duration = -30 ", new Long(-30), event.calculateDuration());
    }
}