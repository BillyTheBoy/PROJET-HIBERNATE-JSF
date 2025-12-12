package dao.jpa;

import dao.DAOException;
import dao.DAOLecteur;
import jakarta.persistence.*;
import mappedclass.Emprunt;
import mappedclass.Lecteur;
import services.ServiceEmprunt;

import java.util.Optional;

public class DaoLecteurJPA implements DAOLecteur {

    private static DaoLecteurJPA instance;

    private DaoLecteurJPA(){

    }

    public static DaoLecteurJPA getInstance(){
        if (instance ==null)
            instance = new DaoLecteurJPA();
        return instance;
    }

    @Override
    public void create(Lecteur lecteur) throws DAOException, PersistenceException {
        if (lecteur == null) throw new DAOException("Lecteur null");
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        em.persist(lecteur);
    }

    @Override
    public Lecteur read(int numero) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        Lecteur l =  em.find(Lecteur.class, numero);
        return Optional.ofNullable(l).orElseThrow(()->new DAOException("Le lecteur n'existe pas"));
    }

    @Override
    public int getNombreLecteur() throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        Query query = em.createQuery("SELECT count(l) from Lecteur l");
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public void add(Emprunt emprunt) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        TypedQuery<Emprunt> query = em.createQuery("SELECT E FROM Emprunt E WHERE E.livre =:livre AND E.dateFin IS NULL ",Emprunt.class);
        query.setParameter("livre", emprunt.getLivre());
        Emprunt e;
        try{
            e = query.getSingleResult();
            throw new DAOException("Livre déjà emprunté");
        }catch(NoResultException ex){
            //on ne fait rien
        }
        em.persist(emprunt);
    }

    @Override
    public void update(Emprunt emprunt) throws DAOException {
        if (emprunt == null) throw new DAOException("Parametre emprunt null");
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        em.merge(emprunt);
    }
}
