package model;

import java.util.ArrayList;
import java.util.List;

public class Jeu {

    private static final int POINTS_PAR_MONTEE = 10;

    private boolean enCours = false;
    private boolean win = false;
    private boolean lose = false;

    private final Joueur joueur;
    private final Score score;
    private final Grille grille;
    private final List<GameObserver> observers = new ArrayList<>();

    public Jeu() {
        joueur = new Joueur(Grille.LARGEUR / 2, Grille.HAUTEUR - 1);
        score = new Score();
        grille = new Grille();
        initialiserGrille();
    }

    private void initialiserGrille() {
        for (int y = 0; y < Grille.HAUTEUR; y++) {
            if (y == 0) {
                grille.ajouterLigne(new Ligne(TypeLigne.ARRIVEE, y));
            } else if (y == Grille.HAUTEUR - 1) {
                grille.ajouterLigne(new Ligne(TypeLigne.DEPART, y));
            } else if (y >= 5 && y <= 14) {
                grille.ajouterLigne(new Ligne(TypeLigne.ROUTE, y));
            } else {
                grille.ajouterLigne(new Ligne(TypeLigne.HERBE, y));
            }
        }
    }

    public boolean estEnCours() {
        return enCours;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isLose() {
        return lose;
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
        win = false;
        lose = false;
        notifierObservers();
    }

    public void arreter() {
        enCours = false;
        notifierObservers();
    }

    public void reinitialiser() {
        enCours = false;
        win = false;
        lose = false;
        joueur.reinitialiser(Grille.LARGEUR / 2, Grille.HAUTEUR - 1);
        score.reinitialiser();
        notifierObservers();
    }

    public void miseAJour() {
        if (!enCours)
            return;

        grille.mettreAJour();

        if (joueur.getY() == 0) {
            win = true;
            enCours = false;
        }

        if (grille.estCollision(joueur.getX(), joueur.getY())) {
            lose = true;
            enCours = false;
        }

        notifierObservers();
    }

    public void update() {
        miseAJour();
    }

    public boolean deplacerJoueurHaut() {
        boolean ok = deplacerJoueur(0, -1);
        if (ok) {
            score.ajouterPoints(POINTS_PAR_MONTEE);
        }
        return ok;
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

    private boolean deplacerJoueur(int deltaX, int deltaY) {
        if (!enCours)
            return false;

        boolean deplacementEffectue = joueur.deplacer(deltaX, deltaY, Grille.LARGEUR, Grille.HAUTEUR);

        if (deplacementEffectue) {
            notifierObservers();
        }

        return deplacementEffectue;
    }

    public void ajouterObserver(GameObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void retirerObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifierObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
}