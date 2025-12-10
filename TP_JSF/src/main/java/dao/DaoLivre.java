package dao;

import mappedclass.Livre;

import java.util.List;

public interface DaoLivre {
    int getNombreLivres() throws DAOException;
    void create(Livre bilbo) throws DAOException;
    List<Livre> read(String titre) throws DAOException;
    Livre read(int num) throws DAOException;
    void delete(Livre livre) throws DAOException;
}
