package dao;

import mappedclass.Theme;

import java.util.List;

public interface DaoTheme {
    void create(Theme theme) throws DAOException;
    Theme read(int id) throws DAOException;
    List<Theme> read(String libelle) throws DAOException;
    void delete(Theme theme) throws DAOException;
    int getNombreThemes() throws DAOException;
}
