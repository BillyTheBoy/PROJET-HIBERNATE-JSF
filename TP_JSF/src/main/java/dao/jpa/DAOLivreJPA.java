package dao.jpa;

import dao.DAOException;
import dao.DaoLivre;
import jakarta.persistence.*;
import mappedclass.Livre;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

public class DAOLivreJPA implements DaoLivre {

    private static DAOLivreJPA instance = null;

    private DAOLivreJPA() {

    }

    public static DAOLivreJPA getInstance() {
        if (instance ==null)
            instance = new DAOLivreJPA();
        return instance;
    }


    @Override
    public void create(Livre bilbo) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        try {
            em.persist(bilbo);
        } catch(PersistenceException e){
            throw new DAOException("Le titre du livre est déjà dans la base de données");
        }
    }

    @Override
    public Livre read(int num) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        return Optional.ofNullable(em.find(Livre.class, num)).orElseThrow(() -> new DAOException("Le livre n'existe pas"));
    }
    @Override
    public List<Livre> read(String titre) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        TypedQuery<Livre> query;
        query = em.createQuery("SELECT l FROM Livre l WHERE l.titre_li like :titrelivre", Livre.class);
        query.setParameter("titrelivre", "%" + titre + "%");
        return query.getResultList();
    }

    @Override
    public void delete(Livre livre) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        em.remove(livre);
    }
    @Override
    public int getNombreLivres() throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        Query query = em.createQuery("SELECT COUNT(l) FROM Livre l");
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }
}
