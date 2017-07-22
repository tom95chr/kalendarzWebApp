package pl.pwsztar.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;

import java.io.IOException;

/**
 * Created by Lapek on 12.07.2017.
 */
@Service
public class RegistrationService {

    @Autowired
    GoogleCalendar googleCalendar;

    public Boolean checkAvailability(String googleCalendarId) {

        try {
            if (googleCalendar.checkCalendarNameAvailability(googleCalendarId)) {
                return Boolean.TRUE;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}