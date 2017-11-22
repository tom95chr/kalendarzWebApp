package pl.pwsztar.login;

import pl.pwsztar.therapists.Therapist;

import javax.persistence.*;

/**
 * Created by Lapek on 01.07.2017.
 */

@Entity
@Table(name = "login_details")
public class LoginDetails {

    private String email;
    private String password;
    private Boolean enabled;
    private String userRole;
    private Therapist therapist;

    @Id
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "user_role")
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email")
    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }
    @Column(name = "enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}