package model;

import java.util.ArrayList;
import java.util.List;

public class Jeu {

    private static final int POINTS_PAR_MONTEE = 10;
    private static final int VIES_DEPART = 3;

    private boolean enCours = false;
    private boolean win = false;
    private boolean lose = false;

    private int vies = VIES_DEPART;

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
            } else if (y >= 2 && y <= 5) {
                grille.ajouterLigne(new Ligne(TypeLigne.RIVIERE, y));
            } else if (y == 6 || y == 9) {
                grille.ajouterLigne(new Ligne(TypeLigne.HERBE, y)); // trottoir
            } else if (y == 7 || y == 8) {
                grille.ajouterLigne(new Ligne(TypeLigne.ROUTE, y)); // seulement 2 voies
            } else if (y == Grille.HAUTEUR - 1) {
                grille.ajouterLigne(new Ligne(TypeLigne.DEPART, y));
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

    public int getVies() {
        return vies;
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
        vies = VIES_DEPART;
        joueur.reinitialiser(Grille.LARGEUR / 2, Grille.HAUTEUR - 1);
        score.reinitialiser();
        notifierObservers();
    }

    public void miseAJour() {
        if (!enCours) {
            return;
        }

        grille.mettreAJour();

        int joueurX = joueur.getX();
        int joueurY = joueur.getY();

        if (joueurY == 0) {
            win = true;
            enCours = false;
            notifierObservers();
            return;
        }

        Ligne ligneJoueur = grille.getLignes().get(joueurY);

        if (ligneJoueur.getType() == TypeLigne.ROUTE && grille.estCollision(joueurX, joueurY)) {
            perdreUneVie();
            notifierObservers();
            return;
        }

        if (ligneJoueur.getType() == TypeLigne.RIVIERE && !grille.estCollision(joueurX, joueurY)) {
            perdreUneVie();
            notifierObservers();
            return;
        }

        if (ligneJoueur.getType() == TypeLigne.HERBE && grille.estCollision(joueurX, joueurY)) {
            perdreUneVie();
            notifierObservers();
            return;
        }

        notifierObservers();
    }

    public void update() {
        miseAJour();
    }

    private void perdreUneVie() {
        vies--;

        if (vies <= 0) {
            lose = true;
            enCours = false;
        } else {
            joueur.reinitialiser(Grille.LARGEUR / 2, Grille.HAUTEUR - 1);
        }
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
        if (!enCours) {
            return false;
        }

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