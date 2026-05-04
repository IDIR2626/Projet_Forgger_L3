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
        } else if (type == TypeLigne.RIVIERE) {
            initialiserRiviere(indexLigne);
        } else if (type == TypeLigne.HERBE) {
            initialiserObstaclesHerbe(indexLigne);
        }
    }

    private void initialiserRoute(int indexLigne) {
        if (indexLigne == 7) {
            elements.add(new Element(1, 0, 1, 1));
            elements.add(new Element(7, 0, 1, 1));
            elements.add(new Element(13, 0, 1, 1));
        } else if (indexLigne == 8) {
            elements.add(new Element(3, 0, 1, -1));
            elements.add(new Element(9, 0, 1, -1));
            elements.add(new Element(14, 0, 1, -1));
        }
    }

    private void initialiserRiviere(int indexLigne) {
        if (indexLigne % 2 == 0) {
            elements.add(new Element(0, 0, 3, 1));
            elements.add(new Element(6, 0, 3, 1));
            elements.add(new Element(12, 0, 3, 1));
        } else {
            elements.add(new Element(2, 0, 2, -1));
            elements.add(new Element(7, 0, 2, -1));
            elements.add(new Element(12, 0, 2, -1));
        }
    }

    private void initialiserObstaclesHerbe(int indexLigne) {
        if (indexLigne == 12) {
            elements.add(new Element(3, 0, 1, 0));
            elements.add(new Element(10, 0, 1, 0));
        } else if (indexLigne == 13) {
            elements.add(new Element(5, 0, 1, 0));
            elements.add(new Element(12, 0, 1, 0));
        } else if (indexLigne == 14) {
            elements.add(new Element(2, 0, 1, 0));
            elements.add(new Element(8, 0, 1, 0));
        } else if (indexLigne == 15) {
            elements.add(new Element(6, 0, 1, 0));
            elements.add(new Element(11, 0, 1, 0));
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