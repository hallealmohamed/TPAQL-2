package org.emp.gl.jeu;

public interface Banque {
    public void crediter(int somme);
    public boolean est_solvable();
    public void debiter(int somme);
}