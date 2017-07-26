package pl.pwsztar.client;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Agnieszka on 2017-06-20.
 */
public class ClientDTO {


    private String email;

    private String telephone;

    @NotBlank(message = "podaj email")
    @Email(message = "nieprawidlowy email")
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
}
