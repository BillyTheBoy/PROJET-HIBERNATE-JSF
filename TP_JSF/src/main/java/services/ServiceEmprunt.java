package services;

import dao.DAOException;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DAOLivreJPA;
import dao.jpa.DaoLecteurJPA;
import mappedclass.Emprunt;
import mappedclass.Lecteur;
import mappedclass.Livre;

public class ServiceEmprunt {
    private static ServiceEmprunt instance;

    public static ServiceEmprunt getInstance() {
        if (instance == null) instance = new ServiceEmprunt();
        return instance;
    }

    public void emprunterLivre(int idLecteur, int idLivre) throws DAOException {

        DAOConnectionJPA connection = DAOConnectionJPA.getInstance();

        try {
            // 1. Opérations de lecture
            Lecteur lecteur = DaoLecteurJPA.getInstance().read(idLecteur);
            Livre livre = DAOLivreJPA.getInstance().read(idLivre);


            // 2. Logique Métier
            Emprunt emp = new Emprunt(livre, lecteur);

            // 3. Appel DAO (Prépare l'insertion dans la transaction courante)
            DaoLecteurJPA.getInstance().add(emp);

            // 4. COMMIT FINAL
            // C'est ici qu'on valide TOUT ce qui s'est passé avant
            connection.commit();

        } catch (Exception e) {
            // 5. ROLLBACK EN CAS D'ERREUR
            // Si le livre est déjà emprunté (erreur DAO) ou autre soucis
            connection.rollback();

            // On relance l'exception pour l'afficher dans le Bean
            // Si c'est déjà une DAOException, on la laisse passer, sinon on l'emballe
            if (e instanceof DAOException) {
                throw (DAOException) e;
            } else {
                throw new DAOException("Erreur technique : " + e.getMessage());
            }
        }
    }

    public void rendreLivre(int idLecteur, int idLivre) throws DAOException {
        DAOConnectionJPA connection = DAOConnectionJPA.getInstance();

        try {
            // 1. Récupération des entités
            Lecteur lecteur = DaoLecteurJPA.getInstance().read(idLecteur);
            Livre livre = DAOLivreJPA.getInstance().read(idLivre);

            // 2. Logique Métier (Utilisation de la méthode de votre classe Lecteur)
            // Cette méthode doit chercher l'emprunt actif et mettre la date de fin à aujourd'hui
            Emprunt empruntARendre = lecteur.rend(livre);

            if (empruntARendre == null) {
                throw new DAOException("Ce lecteur n'a pas emprunté ce livre (ou il est déjà rendu).");
            }

            // 3. Mise à jour via le DAO (Update)
            // Le DAO fait juste le 'merge', sans commit
            DaoLecteurJPA.getInstance().update(empruntARendre);

            // 4. Validation finale de la transaction
            connection.commit();

        } catch (Exception e) {
            // 5. Annulation en cas d'erreur
            connection.rollback();

            // Gestion propre de l'exception
            if (e instanceof DAOException) {
                throw (DAOException) e;
            } else {
                throw new DAOException("Erreur technique lors du retour : " + e.getMessage());
            }
        }
    }
}
