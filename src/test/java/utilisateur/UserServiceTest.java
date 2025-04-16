package utilisateur;

import org.emp.gl.utilisateur.ServiceException;
import org.emp.gl.utilisateur.UserService;
import org.emp.gl.utilisateur.Utilisateur;
import org.emp.gl.utilisateur.UtilisateurApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UtilisateurApi utilisateurApiMock;

    @Test
    public void testCreerUtilisateur() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");

        // Configuration du comportement du mock
        when(utilisateurApiMock.creerUtilisateur(utilisateur)).thenReturn(true);
        when(utilisateurApiMock.obtenirIdUtilisateur(utilisateur)).thenReturn(1);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        userService.creerUtilisateur(utilisateur);

        // Vérification de l'appel à l'API
        verify(utilisateurApiMock).creerUtilisateur(utilisateur);
        verify(utilisateurApiMock).obtenirIdUtilisateur(utilisateur);
    }

    @Test
    public void testCreerUtilisateurEchecAPI() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");

        // Configuration du mock pour lever une exception
        when(utilisateurApiMock.creerUtilisateur(utilisateur))
                .thenThrow(new ServiceException("Echec de la création de l'utilisateur"));

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Vérification que l'exception est bien levée
        assertThrows(ServiceException.class, () -> userService.creerUtilisateur(utilisateur));
    }

    @Test
    public void testCreerUtilisateurDonneesInvalides() throws ServiceException {
        // Création d'un utilisateur avec données invalides
        Utilisateur utilisateur = new Utilisateur("", "", "");

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Vérification que l'exception est bien levée
        ServiceException exception = assertThrows(ServiceException.class,
                () -> userService.creerUtilisateur(utilisateur));

        assertEquals("Informations d'utilisateur invalides", exception.getMessage());

        // Vérification que l'API n'a jamais été appelée
        verify(utilisateurApiMock, never()).creerUtilisateur(any(Utilisateur.class));
    }

    @Test
    public void testCreerUtilisateurVerificationId() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");

        // Configuration du mock pour renvoyer true
        when(utilisateurApiMock.creerUtilisateur(utilisateur)).thenReturn(true);

        // Définition d'un ID fictif
        int idUtilisateur = 123;

        // Configuration du mock pour renvoyer l'ID
        when(utilisateurApiMock.obtenirIdUtilisateur(utilisateur)).thenReturn(idUtilisateur);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        userService.creerUtilisateur(utilisateur);

        // Vérification de l'ID de l'utilisateur
        assertEquals(idUtilisateur, utilisateur.getId());
    }

    @Test
    public void testCreerUtilisateurArgumentCapture() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");

        // Création du captureur d'arguments
        ArgumentCaptor<Utilisateur> argumentCaptor = ArgumentCaptor.forClass(Utilisateur.class);

        // Configuration du mock
        when(utilisateurApiMock.creerUtilisateur(any(Utilisateur.class))).thenReturn(true);
        when(utilisateurApiMock.obtenirIdUtilisateur(any(Utilisateur.class))).thenReturn(1);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        userService.creerUtilisateur(utilisateur);

        // Capture des arguments
        verify(utilisateurApiMock).creerUtilisateur(argumentCaptor.capture());

        // Récupération de l'argument capturé
        Utilisateur utilisateurCapture = argumentCaptor.getValue();

        // Vérification des arguments capturés
        assertEquals("Jean", utilisateurCapture.getPrenom());
        assertEquals("Dupont", utilisateurCapture.getNom());
        assertEquals("jeandupont@email.com", utilisateurCapture.getEmail());
    }
}