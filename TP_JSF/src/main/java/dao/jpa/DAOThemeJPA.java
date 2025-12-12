package dao.jpa;

import dao.DAOException;
import dao.DaoTheme;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import mappedclass.Theme;

import java.util.List;

public class DAOThemeJPA implements DaoTheme {

    private static DAOThemeJPA instance;

    private DAOThemeJPA() {

    }

    public static DAOThemeJPA getInstance() {
        if (instance == null) {
            instance = new DAOThemeJPA();
        }
        return instance;
    }

    @Override
    public void create(Theme theme) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        Query q = em.createQuery("Select t from Theme t where t.lib_th = :themeName");
        q.setParameter("themeName", theme.getLib_th());
        if (!q.getResultList().isEmpty()) {
            throw new DAOException("Le theme que vous essayer d'inserer existe déjà");
        }
        em.persist(theme);
    }

    @Override
    public Theme read(int id) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        return em.find(Theme.class, id);
    }

    @Override
    public List<Theme> read(String libelle) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        TypedQuery<Theme> query;
        query = em.createQuery("SELECT t FROM Theme t WHERE lib_th LIKE :libelleT", Theme.class);
        query.setParameter("libelleT", "%"+libelle+"%");
        return query.getResultList();
    }

    @Override
    public void delete(Theme theme) throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        em.remove(theme);
    }

    @Override
    public int getNombreThemes() throws DAOException {
        EntityManager em = DAOConnectionJPA.getInstance().getEntityManager();
        Query q = em.createQuery("SELECT COUNT(t) FROM Theme  t");
        Long nb = (Long) q.getSingleResult();
        return nb.intValue();
    }
}
