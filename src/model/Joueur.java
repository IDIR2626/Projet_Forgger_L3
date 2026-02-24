package model;

public class Joueur {

    private int x;
    private int y;

    public Joueur(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void reinitialiser(int xDepart, int yDepart) {
        setPosition(xDepart, yDepart);
    }
}