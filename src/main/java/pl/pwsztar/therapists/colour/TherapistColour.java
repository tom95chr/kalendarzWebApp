package pl.pwsztar.therapists.colour;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "therapist_colour")
public class TherapistColour {

    private String colourId;
    private String colourCode;
    private Boolean taken;

    @Id
    @Column(name = "COLOUR_ID")
    public String getColourId() {
        return colourId;
    }

    public void setColourId(String colourId) {
        this.colourId = colourId;
    }
    @Column(name = "CODE")
    public String getColourCode() {
        return colourCode;
    }

    public void setColourCode(String colourCode) {
        this.colourCode = colourCode;
    }
    @Column(name = "taken")
    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }
}
