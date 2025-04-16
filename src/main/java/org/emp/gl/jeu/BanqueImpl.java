package org.emp.gl.jeu;

public class BanqueImpl implements Banque {
    private int fond;
    private int fondMinimum;

    public BanqueImpl(int fond, int fondMinimum) {
        this.fond = fond;
        this.fondMinimum = fondMinimum;
    }

    @Override
    public void crediter(int somme) {
        this.fond += somme;
    }

    @Override
    public boolean est_solvable() {
        return this.fond >= this.fondMinimum;
    }

    @Override
    public void debiter(int somme) {
        this.fond -= somme;
    }
}