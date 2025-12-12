package dao.jpa;

import dao.DAOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


public class DAOConnectionJPA {
    private static DAOConnectionJPA instance = null;

    public static DAOConnectionJPA getInstance() throws DAOException {
        if (instance == null) {
            instance = new DAOConnectionJPA();
        }

        return instance;
    }

    private final EntityManager em;
    private final EntityTransaction tx;

    private DAOConnectionJPA() {
        em = Persistence.createEntityManagerFactory("Livres").createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    public EntityManager getEntityManager() {
        if (! tx.isActive())
            tx.begin();

        return em;
    }


    public void commit() {
        if (tx.isActive()) {
            tx.commit();
            em.clear();
        }
    }

    public void rollback() {
        if (tx.isActive())
            tx.rollback();
    }

    public void closeConnection() {
        if (tx.isActive())
            tx.commit();
        em.close();
        instance = null;
    }

    public boolean estAttache(Object object) {
        return em.contains(object);
    }

    public void viderbase() {
        try{
            EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();

            em.createQuery("DELETE FROM Theme").executeUpdate();
            em.createQuery("DELETE FROM Livre").executeUpdate();
            em.createQuery("DELETE FROM Emprunt").executeUpdate();
            em.createQuery("DELETE FROM Lecteur").executeUpdate();
            em.createNativeQuery("ALTER TABLE td2_dao_basic.theme AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE td2_dao_basic.livre AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE td2_dao_basic.lecteur AUTO_INCREMENT = 1").executeUpdate();
            // on effectue tout ça avec la methode commit
            this.commit();
        }catch(Exception ex){
            this.rollback();
            ex.printStackTrace();
            throw new RuntimeException("Erreur lors du vidage de la base");
        }

    }
}
