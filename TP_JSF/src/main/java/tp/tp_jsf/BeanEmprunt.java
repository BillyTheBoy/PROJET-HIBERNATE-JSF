package tp.tp_jsf;

import dao.DAOException;
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
import services.ServiceEmprunt;

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
        System.out.println("DEBUG - Début ajoutEmprunt");
        System.out.println("DEBUG - Valeurs reçues du formulaire : Lecteur ID=" + numeroLecteur + ", Livre ID=" + numeroLivre);

        try {
            // Appel au service
            services.ServiceEmprunt.getInstance().emprunterLivre(numeroLecteur, numeroLivre);

            System.out.println("DEBUG - Succès appel Service");
            return "Menu_html.xhtml?faces-redirect=true";

        } catch (DAOException ex) {
            // ON AFFICHE L'ERREUR POUR COMPRENDRE
            System.err.println("ERREUR DAO ATTRAPÉE : " + ex.getMessage());
            ex.printStackTrace(); // Affiche la trace complète dans la console Wildfly
            return null;
        } catch (Exception ex) {
            System.err.println("AUTRE ERREUR ATTRAPÉE : " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public List<Emprunt> getListeEmprunt()
    {
        if (numeroLecteur == 0) {
            return null;
        }

        Lecteur l = DaoLecteurJPA.getInstance().read(numeroLecteur);

        if (l == null) {
            return null;
        }

        return l.getEmpruntsEnCours();
    }

    public void rendreLivre(){
        ServiceEmprunt.getInstance().rendreLivre(numeroLecteur,numeroLivre);
    }

}
