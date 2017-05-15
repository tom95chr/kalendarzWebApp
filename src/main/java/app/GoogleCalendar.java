package app;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GoogleCalendar {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Calendar API Java GoogleCalendar";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */

    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GoogleCalendar.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
    getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Event> getAllUpcomingEvents() throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service = getCalendarService();

        // List all upcoming events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        return items;
    }

    public void printAllUpcomingEvents(List<Event> items){

        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }

                System.out.printf("eventID: "+event.getId()+"  event: "+event.getSummary() + " start "+start+ " status "+event.getStatus()+ " colour" +
                        " "+event.getColorId()+ " location  "+event.getLocation()+ " locked? "+event.getLocked()+
                        " visibility "+event.getVisibility()+ " transparency: " + event.getTransparency()+
                        " timezone: "+event.getStart().getTimeZone()+"\n");
            }
        }
    }

    public void createEvent(String summary, Boolean busy, String startDateTime, String endDateTime)
            throws IOException {

        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service = getCalendarService();

        Event event = new Event()
                .setSummary(summary)
                .setLocation("Tarnów, Polska")// Tarnów, Polska
                .setVisibility("public"); //public

        if (busy){
            event.setColorId("11");//10 green 11 red
            event.setTransparency(null); // transparent or null
        }
        else{
            event.setColorId("10");//10 green
            event.setTransparency("transparent"); // transparent or null
        }

        DateTime startDT = new DateTime(startDateTime);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDT);
        event.setStart(start);

        DateTime endDT = new DateTime(endDateTime);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDT);
        event.setEnd(end);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Free event created: %s\n", event.getHtmlLink());
    }

/*    public void setBusy(String eventId, boolean busy) throws IOException {

        List<Event> eventList = getAllUpcomingEvents();

        if (eventList.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            for (Event event : eventList) {
                if (event.getId().equals(eventId)){

                    event.setColorId(null);
                    //event has to be busy
*//*                    if (busy){
                        event.setColorId("11");//10 green 11 red
                        event.setTransparency(null); // transparent or null
                        System.out.println("jestem w ifie");
                    }
                    //event has to be free
                    else{
                        event.setColorId("10");//10 green
                        event.setTransparency("transparent"); // transparent or null
                        System.out.println("jestem w elsie");
                    }*//*
                }
            }
        }
    }*/

    public static void main(String[] args) throws IOException {
        GoogleCalendar gk = new GoogleCalendar();
        gk.printAllUpcomingEvents(gk.getAllUpcomingEvents());
        //gk.setBusy("8sq3cqqv1inh7mt3aib30i48vo",false);

        //gk.createEvent("niedostepny termin 1",true,"2017-05-17T13:30:00.000+02:00","2017-05-17T14:33:00.000+02:00");



    }

}