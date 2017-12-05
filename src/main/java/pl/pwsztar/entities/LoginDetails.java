package pl.pwsztar.entities;

import javax.persistence.*;

/**
 * Created by Lapek on 01.07.2017.
 */

@Entity
@Table(name = "login_details")
public class LoginDetails {

    private String therapist_id;
    private String password;
    private Boolean enabled;
    private String userRole;
    private Therapist therapist;

    @Id
    @Column(name = "therapist_id")
    public String getTherapist_id() {
        return therapist_id;
    }

    public void setTherapist_id(String therapist_id) {
        this.therapist_id = therapist_id;
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

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
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