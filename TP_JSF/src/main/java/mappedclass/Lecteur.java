package mappedclass;

import dao.DAOException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Entity
@Table(name = "lecteur")
public class Lecteur {
    public static final int NON_PERSISTANT = -1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_le")
    private int numero = NON_PERSISTANT;

    @Column(name = "nom_le")
    private String nom;

    @OneToMany(mappedBy = "lecteur")
    private List<Emprunt> listeEmprunts = new ArrayList<>();


    public Lecteur() {}

    public Lecteur(String nom) {
        this.nom = nom;
    }


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Emprunt> getListeEmprunts() {
        return listeEmprunts;
    }

    public void setListeEmprunts(ArrayList<Emprunt> listeEmprunts) {
        this.listeEmprunts = listeEmprunts;
    }

    public Emprunt emprunte(Livre livre) {
        for(Emprunt emprunt : listeEmprunts) {
            if(emprunt.getLivre().equals(livre)) {
                throw new DAOException("Le livre existe déjà");
            }
        }
        Emprunt emprunt = new Emprunt(livre,this);
        listeEmprunts.add(emprunt);
        return emprunt;
    }

    public Emprunt getEmpruntByLivreId(int num_li)
    {
        for(Emprunt emprunt : listeEmprunts) {
            if(emprunt.getLivre().getNum_li() == num_li) {
                return emprunt;
            }
        }
        return null;
    }

    public void supprimerEmprunt(Emprunt emprunt) {
        if (emprunt != null) {
            listeEmprunts.remove(emprunt);
            emprunt.setLecteur(null);  // Bidirectional
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecteur lecteur = (Lecteur) o;
        return numero == lecteur.numero && Objects.equals(nom, lecteur.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, nom);
    }

    @Override
    public String toString() {
        return "Lecteur{numero=" + numero + ", nom='" + nom + "', nbEmprunts=" + listeEmprunts.size() + "}";
    }

    public Emprunt rend(Livre livre) {
        for(Emprunt emprunt : listeEmprunts) {
            if(emprunt.getLivre().equals(livre)) {
                emprunt.setDateFin(LocalDate.now());
                return emprunt;
            }
        }
        throw new IllegalArgumentException("L'emprunt en cours de ce livre n'existe pas");
    }

    public List<Emprunt> getEmprunts() {
        return listeEmprunts;
    }

    public List<Emprunt> getEmpruntsEnCours() {
        return listeEmprunts.stream()
                .filter(emprunt -> emprunt.getDateFin()==null)
                .collect(Collectors.toList());
    }
}