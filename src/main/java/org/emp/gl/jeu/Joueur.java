package org.emp.gl.jeu;

public interface Joueur {
    public int mise(); // on suppose que mise positive
    public void debiter(int somme) throws DebitImpossibleException;
    public void crediter(int somme);
}