package services;

import dao.DAOException;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DAOLivreJPA;
import dao.jpa.DAOThemeJPA;
import mappedclass.Livre;
import mappedclass.Theme;

public class ServiceLivre {

    private static ServiceLivre instance;

    private ServiceLivre(){}

    public static ServiceLivre getInstance() {
        if (instance == null) instance = new ServiceLivre();
        return instance;
    }

    public void ajouterLivre(String titreLivre,int numeroTheme)
    {
        Theme th = DAOThemeJPA.getInstance().read(numeroTheme);
        Livre l = new Livre(titreLivre, th);
        try{
            DAOLivreJPA.getInstance().create(l);
            DAOConnectionJPA.getInstance().commit();
        }catch(DAOException ex){
            DAOConnectionJPA.getInstance().rollback();
            throw ex;
        }
    }

    public void ajouterLivre(Livre livre)
    {
        try{
            DAOLivreJPA.getInstance().create(livre);
            DAOConnectionJPA.getInstance().commit();
        }catch(DAOException ex){
            DAOConnectionJPA.getInstance().rollback();
            throw ex;
        }
    }
}
