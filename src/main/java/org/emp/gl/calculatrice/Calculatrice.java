package org.emp.gl.calculatrice;

public class Calculatrice {
    private int result;

    public int additionner(int a, int b) {
        result = a + b;
        return result;
    }

    public int getResult() {
        return result;
    }
}