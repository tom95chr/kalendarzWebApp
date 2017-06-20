package pl.pwsztar.client;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Agnieszka on 2017-06-20.
 */
public class ClientDTO {

    @NotEmpty(message = "podaj email")
    @Email(message = "nieprawidlowy email")
    private String email;

    private String telephone;


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
