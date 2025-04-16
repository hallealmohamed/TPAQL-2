package jeu;

import org.emp.gl.jeu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JeuTest {
    @Mock
    private Banque banqueMock;

    @Mock
    private Joueur joueurMock;

    @Mock
    private De de1Mock;

    @Mock
    private De de2Mock;

    private Jeu jeu;

    @BeforeEach
    public void setUp() {
        jeu = new Jeu(banqueMock);
    }

    @Test
    public void testJeuFerme() {
        // Fermeture du jeu
        jeu.fermer();

        // Vérification que le jeu est bien fermé
        assertFalse(jeu.estOuvert());

        // Vérification que jouer lance une exception
        assertThrows(JeuFermeException.class, () -> jeu.jouer(joueurMock, de1Mock, de2Mock));
    }

    @Test
    public void testJoueurInsolvable() throws JeuFermeException, DebitImpossibleException {
        // Configuration du joueur pour qu'il soit insolvable
        when(joueurMock.mise()).thenReturn(100);
        doThrow(new DebitImpossibleException("Joueur insolvable")).when(joueurMock).debiter(100);

        // Appel de la méthode à tester
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Vérification que les dés n'ont pas été lancés
        verify(de1Mock, never()).lancer();
        verify(de2Mock, never()).lancer();

        // Vérification que la banque n'a pas été créditée
        verify(banqueMock, never()).crediter(anyInt());
    }

    @Test
    public void testJoueurPerd() throws JeuFermeException, DebitImpossibleException {
        // Configuration du joueur
        when(joueurMock.mise()).thenReturn(100);

        // Configuration des dés pour que la somme ne soit pas 7
        when(de1Mock.lancer()).thenReturn(2);
        when(de2Mock.lancer()).thenReturn(2);

        // Appel de la méthode à tester
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Vérification des interactions
        verify(joueurMock).debiter(100);
        verify(banqueMock).crediter(100);
        verify(de1Mock).lancer();
        verify(de2Mock).lancer();

        // Vérification que le joueur n'a pas été crédité
        verify(joueurMock, never()).crediter(anyInt());

        // Vérification que la banque n'a pas été débitée
        verify(banqueMock, never()).debiter(anyInt());

        // Vérification que le jeu est toujours ouvert
        assertTrue(jeu.estOuvert());
    }

    @Test
    public void testJoueurGagne_BanqueSolvable() throws JeuFermeException, DebitImpossibleException {
        // Configuration du joueur
        when(joueurMock.mise()).thenReturn(100);

        // Configuration des dés pour que la somme soit 7
        when(de1Mock.lancer()).thenReturn(3);
        when(de2Mock.lancer()).thenReturn(4);

        // Configuration de la banque pour qu'elle soit solvable
        when(banqueMock.est_solvable()).thenReturn(true);

        // Appel de la méthode à tester
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Vérification des interactions
        verify(joueurMock).debiter(100);
        verify(banqueMock).crediter(100);
        verify(de1Mock).lancer();
        verify(de2Mock).lancer();

        // Vérification que le joueur a été crédité
        verify(joueurMock).crediter(200);

        // Vérification que la banque a été débitée
        verify(banqueMock).debiter(200);

        // Vérification que le jeu est toujours ouvert
        assertTrue(jeu.estOuvert());
    }

    @Test
    public void testJoueurGagne_BanqueInsolvable() throws JeuFermeException, DebitImpossibleException {
        // Configuration du joueur
        when(joueurMock.mise()).thenReturn(100);

        // Configuration des dés pour que la somme soit 7
        when(de1Mock.lancer()).thenReturn(3);
        when(de2Mock.lancer()).thenReturn(4);

        // Configuration de la banque pour qu'elle soit insolvable après paiement
        when(banqueMock.est_solvable()).thenReturn(false);

        // Appel de la méthode à tester
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Vérification des interactions
        verify(joueurMock).debiter(100);
        verify(banqueMock).crediter(100);
        verify(de1Mock).lancer();
        verify(de2Mock).lancer();

        // Vérification que le joueur a été crédité
        verify(joueurMock).crediter(200);

        // Vérification que la banque a été débitée
        verify(banqueMock).debiter(200);

        // Vérification de la solvabilité de la banque
        verify(banqueMock).est_solvable();

        // Vérification que le jeu a été fermé
        assertFalse(jeu.estOuvert());
    }

    @Test
    public void testJoueurGagne_AvecBanqueReelle() throws JeuFermeException, DebitImpossibleException {
        // Création d'une vraie banque avec un fond de 500 et un minimum de 300
        BanqueImpl banqueReelle = new BanqueImpl(500, 301);

        // Création d'un jeu avec cette banque réelle
        Jeu jeuAvecBanqueReelle = new Jeu(banqueReelle);

        // Configuration du joueur
        when(joueurMock.mise()).thenReturn(200);

        // Configuration des dés pour que la somme soit 7
        when(de1Mock.lancer()).thenReturn(3);
        when(de2Mock.lancer()).thenReturn(4);

        // Appel de la méthode à tester
        jeuAvecBanqueReelle.jouer(joueurMock, de1Mock, de2Mock);

        // Vérification des interactions
        verify(joueurMock).debiter(200);
        verify(joueurMock).crediter(400);

        // Vérification que le jeu a été fermé car la banque n'est plus solvable (500+200-400=300)
        assertFalse(jeuAvecBanqueReelle.estOuvert());
    }
}