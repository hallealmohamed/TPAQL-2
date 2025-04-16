package org.emp.gl.utilisateur;

public interface UtilisateurApi {
    boolean creerUtilisateur(Utilisateur utilisateur) throws ServiceException;
    int obtenirIdUtilisateur(Utilisateur utilisateur) throws ServiceException;
}