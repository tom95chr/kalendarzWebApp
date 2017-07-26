package pl.pwsztar.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwsztar.services.googleCalendar.GoogleCalendar;
import pl.pwsztar.therapists.TherapistDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    @Autowired
    TherapistDAO therapistDAO;

    public String getRandomColour(){

        List<String> colours = Arrays.asList("%235C1158","%23711616","%23BE6D00","%2388880E","%231B887A",
                "%2328754E","%23528800","%230F4B38","%2329527A","%235A6986","%237A367A","%235B123B","%236B3304");

        //random colour from list
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(colours.size());
        String generatedColour = colours.get(index);
        return generatedColour;
    }
}