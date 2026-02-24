package model;

public class Ligne {

    private final int y;
    private final String type;
    private final int vitesse;

    public Ligne(int y, String type, int vitesse) {
        this.y = y;
        this.type = type;
        this.vitesse = vitesse;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void mettreAJour() {
        // Étape 1 : base de la boucle de jeu.
        // Le déplacement des éléments de ligne sera géré à l'étape 3.
    }
}