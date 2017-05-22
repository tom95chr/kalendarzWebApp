package pl.pwsztar.googleCalendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.*;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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

    private com.google.api.services.calendar.Calendar service;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }


    GoogleCalendar() throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        service = getCalendarService();
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

    public List<Event> getAllUpcomingEvents(String calendarId) throws IOException {

        // List all upcoming events from the selected calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list(calendarId)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        return items;
    }

    public void printAllUpcomingEvents(List<Event> items){

        try{
            if (items.size() == 0) {
                System.out.println("No upcoming events found.");
            } else {
                System.out.println("Upcoming events");
                for (Event event : items) {
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }

                    System.out.printf("eventID: "+event.getId()+"  summary: "+event.getSummary() +
                            " start "+start+ "" + " status "+event.getStatus()+ " colour" + " "+event.getColorId()+
                            "\n\t\t location  " +event.getLocation()+ " locked? "+event.getLocked()+ " visibility "
                            +event.getVisibility()+ " transparency: " + event.getTransparency()+
                            " timezone: "+event.getStart().getTimeZone()+"\n");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createEvent(String calendarId, String summary, String availability, String startDateTime, String endDateTime)
            throws IOException {

        try{
            Event event = new Event()
                    .setSummary(summary)
                    .setLocation("Tarnów, Polska")// Tarnów, Polska
                    .setVisibility("public"); //public

            //busy or free
            event = setAvailability(event,availability);

            DateTime startDT = new DateTime(startDateTime);
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDT);
            event.setStart(start);

            DateTime endDT = new DateTime(endDateTime);
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDT);
            event.setEnd(end);

            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Free event created: %s\n", event.getHtmlLink());
        } catch (WrongAvailabilityException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateEvent(String calendarId, String eventId, String availability) throws IOException {

        try{
            // Retrieve the event from the API
            Event event = service.events().get(calendarId, eventId).execute();

            // Here make changes
            event = setAvailability(event,availability);

            // Update the event
            Event updatedEvent = service.events().update(calendarId, event.getId(), event).execute();

            System.out.println("Event updated: "+updatedEvent);
        } catch (WrongAvailabilityException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Event setAvailability(Event event, String availability) throws WrongAvailabilityException {

        try{
            if (availability.equalsIgnoreCase("busy")) {
                event.setColorId("11");//10 green 11 red
                event.setTransparency(null); // transparent or null
                return event;
            } else {
                if (availability.equalsIgnoreCase("free")) {
                    event.setColorId("10");//10 green
                    event.setTransparency("transparent"); // transparent or null
                    return event;
                } else {
                    throw new WrongAvailabilityException();
                }
            }
        }catch( Exception e){
            e.printStackTrace();
            return event;
        }
    }

    public HashMap<String, String> getCalendars() throws IOException {
        HashMap<String,String> therapeutistsCalendars = new HashMap<String, String>();

        // Iterate through entries in calendar list
        String pageToken = null;
        do {
            CalendarList calendarList = service.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                therapeutistsCalendars.put(calendarListEntry.getSummary(),calendarListEntry.getId());
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);

        return therapeutistsCalendars;
    }

    public void printAllCalendars(HashMap therapeutistsCalendars){
        Set set = therapeutistsCalendars.entrySet();

        // Get an iterator
        Iterator i = set.iterator();

        System.out.println("\nAll calendars printed : ");
        // Display elements
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
    }

    public String getGoogleCalendarId(String googleCallendarName) throws IOException {
        System.out.println("\n"+googleCallendarName+" ID =  "+getCalendars().get(googleCallendarName));
        return getCalendars().get(googleCallendarName);
    }

    public void createCalendar(String calendarSummary) throws IOException {

            // Create a new calendar
            com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
            calendar.setSummary(calendarSummary);
            calendar.setTimeZone("Europe/Warsaw");

            // Insert the new calendar
            com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();

            System.out.println(createdCalendar.getId());
    }

    public void deleteCalendar(String calendarId) throws IOException {
        // Delete a calendar
        service.calendars().delete(calendarId).execute();
        System.out.println("calendar "+calendarId+" removed");
    }

    public static void main(String[] args) throws IOException {
        GoogleCalendar gk = new GoogleCalendar();
        //gk.deleteCalendar("c6ttuf6f9btk6rd9a6eaivqb2k@group.calendar.google.com");
        //gk.createCalendar("nowy");
        //gk.getGoogleCalendarId("terapeuta1");
        //gk.printAllCalendars(gk.getCalendars());
        gk.printAllUpcomingEvents(gk.getAllUpcomingEvents(gk.getGoogleCalendarId("terapeuta1")));
        //gk.updateEvent(gk.getGoogleCalendarId("terapeuta2"),"0djm9hj9344qbqgbacndeoouhk","free");
        //gk.createEvent(gk.getGoogleCalendarId("terapeuta1"),"dostepny termin ","free","2017-05-26T13:31:20.000+02:00","2017-05-26T14:33:59.000+02:00");
    }
}