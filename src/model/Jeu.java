package model;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private boolean enCours = false;
    private Joueur joueur;
    private Score score;
    private Grille grille;
    private List<GameObserver> observers = new ArrayList<>();

    public Jeu() {
        joueur = new Joueur(Grille.LARGEUR / 2, Grille.HAUTEUR - 1); // Position de départ bas centre
        score = new Score();
        grille = new Grille();
    }

}