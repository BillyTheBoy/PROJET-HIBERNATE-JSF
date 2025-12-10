package testDAO;


import dao.DAOException;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DAOLivreJPA;
import dao.jpa.DAOThemeJPA;
import mappedclass.Livre;
import mappedclass.Theme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestLivreJPA {
    @Test
    public void testLivre() throws DAOException {
        DAOConnectionJPA.getInstance().viderbase();
        DAOThemeJPA daoTheme = DAOThemeJPA.getInstance();
        DAOLivreJPA daoLivre = DAOLivreJPA.getInstance();

        assertEquals(0, daoTheme.getNombreThemes());
        assertEquals(0, daoLivre.getNombreLivres());


        Theme fantastique =  new Theme("Fantastique");
        Livre bilbo = new Livre("Bilbo le hobbit", fantastique);

        daoLivre.create(bilbo);

        assertEquals(1, daoTheme.getNombreThemes());
        assertEquals(1, daoLivre.getNombreLivres());

        Livre anneaux = new Livre("Le seigneur des anneaux", fantastique);
        daoLivre.create(anneaux);

        assertEquals(1, daoTheme.getNombreThemes());
        assertEquals(2, daoLivre.getNombreLivres());

        assertEquals(2, daoLivre.read("").size());

        daoLivre.delete(bilbo);
        assertEquals(1, daoTheme.getNombreThemes());
        assertEquals(1, daoLivre.getNombreLivres());
        assertNull(daoLivre.read(1));
    }
}
