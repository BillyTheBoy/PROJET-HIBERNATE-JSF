package tp.tp_jsf;

import dao.DAOLecteur;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DAOLivreJPA;
import dao.jpa.DaoLecteurJPA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import mappedclass.Emprunt;
import mappedclass.Lecteur;
import mappedclass.Livre;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named("beanEmprunt")
@SessionScoped
public class BeanEmprunt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Recommandé
    private int numeroLecteur;
    private int numeroLivre;

    public int getNumeroLecteur() {
        return numeroLecteur;
    }

    public void setNumeroLecteur(int numeroLecteur) {
        this.numeroLecteur = numeroLecteur;
    }

    public int getNumeroLivre() {
        return numeroLivre;
    }

    public void setNumeroLivre(int numeroLivre) {
        this.numeroLivre = numeroLivre;
    }

    public String ajoutEmprunt()
    {
        System.out.println("DEBUG - Tentative emprunt Livre ID: " + numeroLivre + " pour Lecteur ID: " + numeroLecteur);

        try {
            Livre livre = DAOLivreJPA.getInstance().read(numeroLivre);
            Lecteur lecteur = DaoLecteurJPA.getInstance().read(numeroLecteur);

            System.out.println("DEBUG - Livre trouvé: " + (livre != null ? livre.getTitre_li() : "null"));

            Emprunt emp = new Emprunt(livre, lecteur);
            DaoLecteurJPA.getInstance().add(emp);

            return "Menu_html.xhtml";
        } catch (Exception ex) {
            System.out.println("ERREUR CAPTURÉE : " + ex.getMessage());
            ex.printStackTrace(); // Affiche tout le détail dans la console
            return null; // Reste sur la même page en cas d'erreur
        }
    }

    public List<Emprunt> getListeEmprunt()
    {
        if (numeroLecteur == 0) {
            return null; // Ou une liste vide : new ArrayList<>();
        }

        Lecteur l = DaoLecteurJPA.getInstance().read(numeroLecteur);

        if (l == null) {
            return null; // Evite le NullPointerException si l'ID n'existe pas en base
        }

        return l.getEmpruntsEnCours();
    }

    public void rendreLivre(){
        Lecteur l = DaoLecteurJPA.getInstance().read(numeroLecteur);
        Livre livre = DAOLivreJPA.getInstance().read(numeroLivre);
        Emprunt e = l.rend(livre);
        DaoLecteurJPA.getInstance().update(e);
        DAOConnectionJPA.getInstance().commit();
    }

}
