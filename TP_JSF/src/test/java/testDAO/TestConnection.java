package testDAO;

import dao.jpa.DAOConnectionJPA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestConnection {
    @Test
    public void testConnection() {
        assertDoesNotThrow(()->{
            DAOConnectionJPA.getInstance().getEntityManager();
            DAOConnectionJPA.getInstance().viderbase();
        });
    }
}