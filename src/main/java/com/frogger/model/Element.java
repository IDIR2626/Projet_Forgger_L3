package com.frogger.model;

public class Element {

    private int x;
    private int y;
    private int taille; // or whatever the 3rd param represents
    private int vitesse;

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // added overload to accept three ints
    public Element(int x, int y, int taille) {
        this(x, y);
        this.taille = taille;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void deplacer(int largeurGrille) {
        x += vitesse;
        x = ((x % largeurGrille) + largeurGrille) % largeurGrille;
    }

    public int getTaille() {
        return taille;
    }

    public int getVitesse() {
        return vitesse;
    }
}





