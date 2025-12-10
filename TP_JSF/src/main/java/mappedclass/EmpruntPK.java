package mappedclass;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class  EmpruntPK {

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "num_li")
    private Integer numLivre;

    @Column(name = "num_le")
    private Integer numLecteur;

    public EmpruntPK() {}

    public EmpruntPK(LocalDate dateDebut, Integer numLivre, Integer numLecteur) {
        this.dateDebut = dateDebut;
        this.numLivre = numLivre;
        this.numLecteur = numLecteur;
    }


    // getters / setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpruntPK)) return false;
        EmpruntPK that = (EmpruntPK) o;
        return Objects.equals(dateDebut, that.dateDebut) &&
                Objects.equals(numLivre, that.numLivre) &&
                Objects.equals(numLecteur, that.numLecteur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateDebut, numLivre, numLecteur);
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
}
