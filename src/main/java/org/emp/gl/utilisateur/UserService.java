package org.emp.gl.utilisateur;

public class UserService {
    private final UtilisateurApi utilisateurApi;

    public UserService(UtilisateurApi utilisateurApi) {
        this.utilisateurApi = utilisateurApi;
    }

    public void creerUtilisateur(Utilisateur utilisateur) throws ServiceException {
        if (utilisateur == null || isEmptyOrNull(utilisateur.getNom()) ||
                isEmptyOrNull(utilisateur.getPrenom()) || isEmptyOrNull(utilisateur.getEmail())) {
            throw new ServiceException("Informations d'utilisateur invalides");
        }

        boolean success = utilisateurApi.creerUtilisateur(utilisateur);

        if (success) {
            int id = utilisateurApi.obtenirIdUtilisateur(utilisateur);
            utilisateur.setId(id);
        }
    }

    private boolean isEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }
}