package testDAO;

import dao.DAOException;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DAOLivreJPA;
import dao.jpa.DaoLecteurJPA;
import mappedclass.Emprunt;
import mappedclass.Lecteur;
import mappedclass.Livre;
import mappedclass.Theme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDAOLecteur {
    @Test
    public void testDAOLecteur() throws DAOException {
        DAOConnectionJPA.getInstance().viderbase();
        DAOLivreJPA daoLivre = DAOLivreJPA.getInstance();
        DaoLecteurJPA daoLecteur = DaoLecteurJPA.getInstance();

        Theme fantastique = new Theme("Fantastique");
        Livre livre = new Livre("Bilbo le hobbit", fantastique);
        daoLivre.create(livre);

        assertEquals(0, daoLecteur.getNombreLecteur());
        Lecteur lecteur = new Lecteur("John DOE");
        daoLecteur.create(lecteur);
        assertEquals(1, daoLecteur.getNombreLecteur());

        Emprunt emprunt = lecteur.emprunte(livre);
        daoLecteur.add(emprunt);

        Emprunt empruntRendu = lecteur.rend(livre);
        if(empruntRendu != null)
            daoLecteur.update(empruntRendu);

        Lecteur l2 = daoLecteur.read(lecteur.getNumero());
        assertEquals(1, l2.getEmprunts().size());


        Livre _2001 = new Livre("2001", new Theme("Science fiction"));
        daoLivre.create(_2001);
        Emprunt emp = lecteur.emprunte(_2001);
        daoLecteur.add(emp);

        assertThrows(DAOException.class, () -> {Emprunt emp2 = lecteur.emprunte(_2001);});

        l2 = daoLecteur.read(lecteur.getNumero());
        assertEquals(2, l2.getEmprunts().size());

    }
}
