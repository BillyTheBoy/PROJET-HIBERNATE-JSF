package tp.tp_jsf;

import dao.DAOException;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DAOLivreJPA;
import dao.jpa.DAOThemeJPA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import mappedclass.Livre;
import mappedclass.Theme;
import services.ServiceLivre;

import java.util.List;

@Named("beanLivre")
@ApplicationScoped
public class BeanLivre {

    private String titre;
    private int numero_th;
    public List<Livre> getLivres(){
        try{
            return DAOLivreJPA.getInstance().read("");
        }catch(DAOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String ajouterLivre(){
        try{
            ServiceLivre.getInstance().ajouterLivre(titre, numero_th);
            return "Ajouter_Livre_Succes";
        }catch(DAOException ex){
            return "Erreur_Ajouter_Livre";
        }
    }



    public int getNumero_th() {
        return  numero_th;
    }
    public void setNumero_th(int numero_th) {
        this.numero_th = numero_th;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
}
