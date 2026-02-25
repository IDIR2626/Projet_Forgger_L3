package model;

public class Joueur {

    private int x;
    private int y;
    private int vies;
    private boolean bouclierActif;
    private int dureeBouclier;

    public Joueur(int x, int y) {
        this.x = x;
        this.y = y;
        this.vies = 3;
        this.bouclierActif = false;
        this.dureeBouclier = 0;
    }

    public void allerHaut() { y--; }
    public void allerBas() { y++; }
    public void allerDroite() { x++; }
    public void allerGauche() { x--; }

    public void activerBouclier(int duree) {
        bouclierActif = true;
        dureeBouclier = duree;
    }

    public void perdreVie() {
        if (!bouclierActif) {
            vies--;
        }
    }

    public void reinitialiser() {
        vies = 3;
        bouclierActif = false;
        dureeBouclier = 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVies() { return vies; }
    public boolean isBouclierActif() { return bouclierActif; }
    public int getDureeBouclier() { return dureeBouclier; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}

