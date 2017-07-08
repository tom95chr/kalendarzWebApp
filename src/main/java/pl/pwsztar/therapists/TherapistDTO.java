/*
package pl.pwsztar.therapists;




import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class TherapistDTO {

    @NotBlank(message = "Podaj login")
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$", message = "Login musi miec od 4 do 20 znakow. Dopuszczalne znaki a-z 0-9 i podkreslniki. ")
    private String therapistId;



    @NotBlank(message = "Wprowadz imie")
    private String firstName;

    @NotBlank(message = "Wprowadz nazwisko")
    private String lastName;

    @NotBlank(message = "Podaj specjalizacje")
    private String specialization;

    @NotBlank(message = "podaj email")
    @Email(message = "nieprawidlowy email")
    private String email;

    private String telephone;

    private String description;

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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }
}
*/
