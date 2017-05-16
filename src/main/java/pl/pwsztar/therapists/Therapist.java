package pl.pwsztar.therapists;


/**
 * Created by Lapek on 16.05.2017.
 */
public class Therapist {

    private Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    private String googleCalendarId;
    private Float visitLenght;
    private Float pauseLenght;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getGoogleCalendarId() {
        return googleCalendarId;
    }

    public void setGoogleCalendarId(String googleCalendarId) {
        this.googleCalendarId = googleCalendarId;
    }

    public Float getVisitLenght() {
        return visitLenght;
    }

    public void setVisitLenght(Float visitLenght) {
        this.visitLenght = visitLenght;
    }

    public Float getPauseLenght() {
        return pauseLenght;
    }

    public void setPauseLenght(Float pauseLenght) {
        this.pauseLenght = pauseLenght;
    }
}
