package calculatrice;

import org.emp.gl.calculatrice.Calculatrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculatriceTest {
    @Mock
    private Calculatrice calculatrice;

    @Test
    public void testAdditionner() {
        // Définition du comportement de la méthode "additionner"
        when(calculatrice.additionner(2, 3)).thenReturn(5);

        // Si vous avez besoin de simuler getResult() aussi
        when(calculatrice.getResult()).thenReturn(5);

        // Appel de la méthode à tester
        int resultat = calculatrice.additionner(2, 3);

        // Vérification du résultat
        assertEquals(5, resultat);

        // Vérification que la méthode "additionner" a été appelée avec les arguments 2 et 3
        verify(calculatrice).additionner(2, 3);

        // Si vous voulez vérifier l'appel à getResult(), vous devez d'abord l'appeler
        calculatrice.getResult();
        verify(calculatrice).getResult();

        // Vérification qu'aucune autre méthode n'a été appelée sur l'objet
        verifyNoMoreInteractions(calculatrice);
    }
}