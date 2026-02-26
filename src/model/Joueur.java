package model;

public class Joueur 
{

    private int x;
    private int y;
    private int vies;
    private boolean bouclierActif;
    private int dureeBouclier;

    public Joueur(int x, int y) {
        this.x = x;
        this.y = y;
        this.vies = 3;            // valeur par défaut (modifiable selon ton jeu)
        this.bouclierActif = false;
        this.dureeBouclier = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVies() {
        return vies;
    }

    public boolean isBouclierActif() {
        return bouclierActif;
    }

    public int getDureeBouclier() {
        return dureeBouclier;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void reinitialiser(int xDepart, int yDepart) {
        this.x = xDepart;
        this.y = yDepart;
        this.vies = 3;
        this.bouclierActif = false;
        this.dureeBouclier = 0;
    }

    public void activerBouclier(int duree) {
        this.bouclierActif = true;
        this.dureeBouclier = duree;
    }

    public void perdreVie() {
        if (!bouclierActif) {
            vies--;
        }
    }

    public boolean deplacer(int deltaX, int deltaY, int largeurGrille, int hauteurGrille) 
    {
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

        // Gestion du bouclier (il se désactive quand la durée tombe à 0)
        if (bouclierActif) {
            dureeBouclier--;
            if (dureeBouclier <= 0) {
                bouclierActif = false;
            }
        }// Si le bouclier est actif, on le désactive après un déplacement (optionnel, selon les règles de ton jeu)

        return true;
    }
}
