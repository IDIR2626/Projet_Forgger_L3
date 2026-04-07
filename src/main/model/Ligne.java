package model;

import java.util.ArrayList;
import java.util.List;

public class Ligne {

    private final TypeLigne type;
    private final List<Element> elements = new ArrayList<>();

    public Ligne(TypeLigne type) {
        this(type, 0);
    }

    public Ligne(TypeLigne type, int indexLigne) {
        this.type = type;

        if (type == TypeLigne.ROUTE) {
            initialiserRoute(indexLigne);
        }
    }

    private void initialiserRoute(int indexLigne) {
        // alterner les directions et positions pour casser l'effet colonnes fixes
        if (indexLigne % 2 == 0) {
            elements.add(new Element(1, 0, 1, 1));
            elements.add(new Element(6, 0, 1, 1));
            elements.add(new Element(11, 0, 1, 1));
        } else {
            elements.add(new Element(3, 0, 1, -1));
            elements.add(new Element(8, 0, 1, -1));
            elements.add(new Element(13, 0, 1, -1));
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
            if (e.occupeCase(x)) {
                return true;
            }
        }
        return false;
    }
}