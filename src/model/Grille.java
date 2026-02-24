
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grille {

    public static final int HAUTEUR = 20;
    public static final int LARGEUR = 15;

    private final List<Ligne> lignes = new ArrayList<>();

    public void ajouterLigne(Ligne ligne) {
        if (ligne == null) {
            throw new IllegalArgumentException("ligne ne peut pas être null");
        }
        lignes.add(ligne);
    }

    public List<Ligne> getLignes() {
        return Collections.unmodifiableList(lignes);
    }

    public void mettreAJour() {
        for (Ligne ligne : lignes) {
            ligne.mettreAJour();
        }
    }
}
