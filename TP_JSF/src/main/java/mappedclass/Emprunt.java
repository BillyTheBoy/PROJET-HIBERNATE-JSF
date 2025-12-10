package mappedclass;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
@Entity
@Table(name = "emprunt")
public class Emprunt {

    @EmbeddedId
    EmpruntPK id;

    @ManyToOne
    @MapsId("numLecteur")
    @JoinColumn(name = "num_le")
    private Lecteur lecteur;

    @ManyToOne
    @MapsId("numLivre")
    @JoinColumn(name = "num_li")
    private Livre livre;



    @Column(name = "date_fin")
    private LocalDate dateFin;

    public Emprunt() {}

    public Emprunt(Livre livre, Lecteur lecteur) {
        this.livre = livre;
        this.lecteur = lecteur;
        this.id = new EmpruntPK(LocalDate.now(), livre.getNum_li(), lecteur.getNumero());
    }




    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public Lecteur getLecteur() { return lecteur; }
    public void setLecteur(Lecteur lecteur) {
        this.lecteur = lecteur;
    }

    public LocalDate getDateDebut() {
        return id.getDateDebut();
    }
    public  void setDateDebut(LocalDate dateDebut) {
        id.setDateDebut(dateDebut);
    }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }



    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return Objects.equals(getDateDebut(), emprunt.getDateDebut()) &&
                Objects.equals(dateFin, emprunt.dateFin) &&
                Objects.equals(lecteur, emprunt.lecteur) &&
                Objects.equals(livre, emprunt.livre);
    }


    @Override
    public String toString() {
        return "Emprunt{dateDebut=" + getDateDebut() +
                ", dateFin=" + dateFin +
                ", lecteur=" + (lecteur != null ? lecteur.getNom() : "null") +
                ", livre=" + (livre != null ? livre.getTitre_li() : "null") + "}";
    }
}
