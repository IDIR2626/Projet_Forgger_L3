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

    public boolean deplacer(int deltaX, int deltaY, int largeurGrille, int hauteurGrille) {
        int nouvelleX = x + deltaX;
        int nouvelleY = y + deltaY;

        if (nouvelleX < 0 || nouvelleX >= largeurGrille) {
            return false;
        }
        if (nouvelleY < 0 || nouvelleY >= hauteurGrille) {
            return false;
        }

        x = nouvelleX;
        y = nouvelleY;
        return true;
    }
}
