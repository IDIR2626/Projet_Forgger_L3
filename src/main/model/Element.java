package model;

public class Element {

    private int x;
    private int y;
    private int taille;
    private int vitesse;

    public Element(int x, int y) {
        this(x, y, 1, 0);
    }

    public Element(int x, int y, int taille) {
        this(x, y, taille, 0);
    }

    public Element(int x, int y, int taille, int vitesse) {
        this.x = x;
        this.y = y;
        this.taille = taille;
        this.vitesse = vitesse;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTaille() {
        return taille;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void deplacer(int largeurGrille) {
        x += vitesse;

        if (x >= largeurGrille) {
            x = -taille + 1;
        } else if (x + taille - 1 < 0) {
            x = largeurGrille - 1;
        }
    }

    public boolean occupeCase(int caseX) {
        return caseX >= x && caseX < x + taille;
    }
}