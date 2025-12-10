package dao;

import mappedclass.Emprunt;
import mappedclass.Lecteur;

public interface DAOLecteur {
    void create(Lecteur lecteur) throws DAOException;

    Lecteur read(int numero) throws DAOException;

    int getNombreLecteur() throws DAOException;

    void add(Emprunt emprunt) throws DAOException;

    void update(Emprunt emprunt) throws DAOException;
}