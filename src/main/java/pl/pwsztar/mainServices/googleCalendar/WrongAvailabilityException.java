package pl.pwsztar.mainServices.googleCalendar;

/**
 * Created by Lapek on 15.05.2017.
 */
public class WrongAvailabilityException extends Exception {

    public WrongAvailabilityException() {
        super("Wrong availability ! Set availability to: \"free\" or \"busy\"");
    }


    public WrongAvailabilityException(String message)
    {
        super(message);
    }
}
