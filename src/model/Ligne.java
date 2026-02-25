package model;

import java.util.ArrayList;
import java.util.List;

public class Ligne {

    private int positionY;
    private TypeLigne type;
    private List<Element> elements;

    public Ligne(int positionY, TypeLigne type) {
        this.positionY = positionY;
        this.type = type;
        this.elements = new ArrayList<>();
    }

    public void ajouterElement(Element e) {
        elements.add(e);
    }

    public void mettreAJour() {
        for (Element e : elements) {
            e.deplacer();
        }
    }

    public int getPositionY() { return positionY; }
    public TypeLigne getType() { return type; }
    public List<Element> getElements() { return elements; }
}

