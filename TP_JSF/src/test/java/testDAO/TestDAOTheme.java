package testDAO;

import dao.DAOException;
import dao.jpa.DAOConnectionJPA;
import dao.jpa.DAOThemeJPA;
import mappedclass.Theme;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDAOTheme {
    @Test
    public void testScenarioTheme() throws DAOException {
        // On s'assure que la base est vide
        DAOConnectionJPA.getInstance().viderbase();
        DAOThemeJPA dao = DAOThemeJPA.getInstance();
        assertEquals(0, dao.getNombreThemes());

        // On crée 3 objets métier
        Theme   policier = new Theme("Policier"),
                fantastitique = new Theme("Fantastique"),
                sf = new Theme("Science fiction");

        // On les ajoute un par un en s'assurant que la base de données évolue correctement
        // et que les numéros de thème sont bien mis à jour dans les objets métier
        dao.create(policier);
        assertEquals(1, dao.getNombreThemes());
        assertEquals(1, policier.getNum_th());

        dao.create(fantastitique);
        assertEquals(2, fantastitique.getNum_th());

        dao.create(sf);
        assertEquals(3, sf  .getNum_th());
        assertEquals(3, dao.getNombreThemes());

        // Quelques lectures dans la base
        // On les lit tous
        List<Theme> listeThemes = dao.read("");
        assertEquals(3, listeThemes.size());

        // On en lit un à partir de son numéro
        Theme fant = dao.read(2);
        assertEquals(fantastitique, fant);

        // On en lit un à partir du début de son libellé
        listeThemes = dao.read("Fant");
        assertEquals(1, listeThemes.size());
        assertEquals(fantastitique, listeThemes.get(0));

        // On en détruit un et on vérifie qu'il n'existe plus
        dao.delete(fantastitique);
        assertFalse(DAOConnectionJPA.getInstance().getEntityManager().contains(fantastitique));
        assertEquals(2, dao.getNombreThemes());
        listeThemes = dao.read("Fant");
        assertEquals(0, listeThemes.size());

        // On vérifie que la contrainte d'unicité est bien prise en compte
        assertThrows(DAOException.class, () -> dao.create(new Theme("Policier")));


    }
}