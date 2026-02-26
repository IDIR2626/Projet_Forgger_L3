package model;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private static final int POINTS_PAR_TICK = 1;

    private boolean enCours = false;
    private final Joueur joueur;
    private final Score score;
    private final Grille grille;
    private final List<GameObserver> observers = new ArrayList<>();

    public Jeu() {
        joueur = new Joueur(Grille.LARGEUR / 2, Grille.HAUTEUR - 1); // Position de départ bas centre
        score = new Score();
        grille = new Grille();
    }

    public boolean estEnCours() {
        return enCours;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Score getScore() {
        return score;
    }

    public Grille getGrille() {
        return grille;
    }

    public void demarrer() {
        enCours = true;
        notifierObservers();
    }

    public void arreter() {
        enCours = false;
        notifierObservers();
    }

    public void reinitialiser() {
        enCours = false;
        joueur.reinitialiser(Grille.LARGEUR / 2, Grille.HAUTEUR - 1);
        score.reinitialiser();
        notifierObservers();
    }

    public void miseAJour() {
        if (!enCours) {
            return;
        }

        grille.mettreAJour();
        score.ajouterPoints(POINTS_PAR_TICK);
        notifierObservers();
    }

    public boolean deplacerJoueurHaut() {
        return deplacerJoueur(0, -1);
    }

    public boolean deplacerJoueurBas() {
        return deplacerJoueur(0, 1);
    }

    public boolean deplacerJoueurGauche() {
        return deplacerJoueur(-1, 0);
    }

    public boolean deplacerJoueurDroite() {
        return deplacerJoueur(1, 0);
    }

    public void ajouterObserver(GameObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void retirerObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private boolean deplacerJoueur(int deltaX, int deltaY) {
        if (!enCours) {
            return false;
        }

        boolean deplacementEffectue = joueur.deplacer(deltaX, deltaY, Grille.LARGEUR, Grille.HAUTEUR);
        if (deplacementEffectue) {
            notifierObservers();
        }
        return deplacementEffectue;
    }

    private void notifierObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
}