package model;

public class Element {

    private int x;
    private final int y;
    private int vitesse;

    public Element(int x, int y, int vitesse) {
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void deplacer(int largeurGrille) {
        x += vitesse;

        if (largeurGrille <= 0) {
            return;
        }

        x = ((x % largeurGrille) + largeurGrille) % largeurGrille;
    }
}