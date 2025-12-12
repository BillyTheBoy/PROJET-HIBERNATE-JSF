package services;

import dao.DAOException;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DaoLecteurJPA;
import jakarta.persistence.PersistenceException;
import mappedclass.Lecteur;

public class ServiceLecteur {
    private static ServiceLecteur instance;

    private ServiceLecteur()  {}

    public static ServiceLecteur getInstance() {
        if (instance == null) instance = new ServiceLecteur();
        return instance;
    }

    public void AjouterLecteur(Lecteur lecteur) throws DAOException
    {
        try{
            DaoLecteurJPA.getInstance().create(lecteur);
            DAOConnectionJPA.getInstance().commit();
        }catch(DAOException e){
            System.err.println(e.getMessage());
        }catch(PersistenceException e){
            Throwable cause = e.getCause();

        }
    }

    public void AjouterLecteur(String nomLecteur) throws DAOException
    {
        Lecteur lecteur = new Lecteur(nomLecteur);
        try{
            DaoLecteurJPA.getInstance().create(lecteur);
            DAOConnectionJPA.getInstance().commit();
        }catch(DAOException e){
            System.err.println(e.getMessage());
        }catch(PersistenceException e){
            Throwable cause = e.getCause();

        }
    }


}
