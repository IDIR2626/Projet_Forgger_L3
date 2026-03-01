package model;

import java.util.ArrayList;
import java.util.List;

public class Ligne {

    private final TypeLigne type;
    private final List<Element> elements = new ArrayList<>();

    public Ligne(TypeLigne type) {
        this.type = type;

        // Exemple : obstacles sur les routes
        if (type == TypeLigne.ROUTE) {
            elements.add(new Element(3, 0));
            elements.add(new Element(10, 0));
        }
    }

    public TypeLigne getType() {
        return type;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void mettreAJour() {
        for (Element e : elements) {
            e.deplacer(Grille.LARGEUR);
        }
    }

    public boolean estCollision(int x) {
        for (Element e : elements) {
            if (e.getX() == x) return true;
        }
        return false;
    }
}



