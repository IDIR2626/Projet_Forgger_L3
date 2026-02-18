package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Jeu {
    private boolean enCours;
    private Joueur joueur;
    private Score score;
    private Grille grille;
    private List<GameObserver> observers = new ArrayList<>();

    public Jeu() {
        this.joueur = new Joueur(Grille.LARGEUR / 2, Grille.HAUTEUR / 2 - 1);
        this.score = new Score();
        this.grille = new Grille();
        initLignes();

    }

    private void initLignes() {
        // Exemple : Route (obstacles)
        grille.ajouterLigne(new Ligne(Grille.HAUTEUR - 3, TypeLigne.ROUTE, 3));
        // Rivière (Platformes)
        grille.ajouterLigne(new Ligne(Grille.HAUTEUR - 2, TypeLigne.RIVIERE, 2));
        // fERROVIAIRE (Trains)
        grille.ajouterLigne(new Ligne(Grille.HAUTEUR - 1, TypeLigne.FERROVIAIRE, 4));
        // Zone toxique(Dégats)
        grille.ajouterLigne(new Ligne(Grille.HAUTEUR - 4, TypeLigne.TOXIQUE, 1));

    }

    public void demarrer() {
        // Timer pour faire avancer les lignes pour game loop
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (enCours)
                    miseAjour();
            }

        }, 0, 16);
    }

    private void miseAjour() {
        grille.mettreAJour();
        gererCollisions();
        notifierObservers();

    }

    private void gererCollisions() {
        // logique : si joeur touche obstacle -> perdreVie()
        // Si zone toxique + !bouclier -> dégats/sec
    }

    public void notifierObservers() {
        observers.forEach(GameObserver::update);
    }
}