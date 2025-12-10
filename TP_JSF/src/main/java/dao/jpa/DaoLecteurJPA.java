package dao.jpa;

import dao.DAOException;
import dao.DAOLecteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import mappedclass.Emprunt;
import mappedclass.Lecteur;

public class DaoLecteurJPA implements DAOLecteur {

    private static DaoLecteurJPA instance;
    private static EntityManager em =  DAOConnectionJPA.getInstance().getEntityManager();

    private DaoLecteurJPA(){

    }

    public static DaoLecteurJPA getInstance(){
        if (instance ==null)
            instance = new DaoLecteurJPA();
        return instance;
    }

    @Override
    public void create(Lecteur lecteur) throws DAOException {
        em.persist(lecteur);
    }

    @Override
    public Lecteur read(int numero) throws DAOException {
        return em.find(Lecteur.class, numero);
    }

    @Override
    public int getNombreLecteur() throws DAOException {
        Query query = em.createQuery("SELECT count(l) from Lecteur l");
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public void add(Emprunt emprunt) throws DAOException {

        TypedQuery<Emprunt> query = em.createQuery("SELECT E FROM Emprunt E WHERE E.livre =:livre AND E.dateFin IS NULL ",Emprunt.class);
        query.setParameter("livre", emprunt.getLivre());
        Emprunt e;
        try{
            e = query.getSingleResult();
            throw new DAOException("Livre déjà emprunté");
        }catch(NoResultException ex){
            //on ne fait rien
        }
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.persist(emprunt);
        DAOConnectionJPA.getInstance().commit();
    }

    @Override
    public void update(Emprunt emprunt) throws DAOException {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.merge(emprunt);
        DAOConnectionJPA.getInstance().commit();
    }
}
