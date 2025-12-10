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
        Theme th = DAOThemeJPA.getInstance().read(numero_th);
        Livre l = new Livre(titre, th);
        try{
            DAOLivreJPA.getInstance().create(l);
        }catch(DAOException ex){
            return "Erreur_Ajouter_Livre";
        }
        return "Ajouter_Livre_Succes";
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
