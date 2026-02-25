
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ligne {

    private final int y;
    private final TypeLigne type;
    private final int vitesse;
    private final List<Element> elements = new ArrayList<>();

    public Ligne(int y, TypeLigne type, int vitesse) {
        if (type == null) {
            throw new IllegalArgumentException("type ne peut pas être null");
        }
        this.y = y;
        this.type = type;
        this.vitesse = vitesse;
    }

    public int getY() {
        return y;
    }

    public TypeLigne getType() {
        return type;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void ajouterElement(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("element ne peut pas être null");
        }

        if (element.getY() != y) {
            throw new IllegalArgumentException("l'élément doit appartenir à la même ligne");
        }

        elements.add(element);
    }

    public List<Element> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public void mettreAJour() {
        for (Element element : elements) {
            if (element.getVitesse() == 0) {
                element.setVitesse(vitesse);
            }
            element.deplacer(Grille.LARGEUR);
        }
    }
}
