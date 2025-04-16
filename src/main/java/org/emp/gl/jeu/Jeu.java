package org.emp.gl.jeu;
public class Jeu {
    private Banque banque;
    private boolean ouvert;

    public Jeu(Banque labanque) {
        this.banque = labanque;
        this.ouvert = true;
    }

    public void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException {
        // Vérifier si le jeu est ouvert
        if (!estOuvert()) {
            throw new JeuFermeException("Le jeu est fermé");
        }

        int mise = joueur.mise();

        // Vérifier si le joueur peut payer la mise
        try {
            joueur.debiter(mise);
        } catch (DebitImpossibleException e) {
            return; // Le joueur ne peut pas payer, arrêt du jeu
        }

        // La banque encaisse la mise
        banque.crediter(mise);

        // Lancer les dés
        int resultatDe1 = de1.lancer();
        int resultatDe2 = de2.lancer();
        int somme = resultatDe1 + resultatDe2;

        // Vérifier si le joueur a gagné
        if (somme == 7) {
            int gain = mise * 2;
            joueur.crediter(gain);
            banque.debiter(gain);

            // Vérifier si la banque est encore solvable
            if (!banque.est_solvable()) {
                fermer();
            }
        }
    }

    public void fermer() {
        this.ouvert = false;
    }

    public boolean estOuvert() {
        return this.ouvert;
    }
}