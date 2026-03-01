package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Jeu;
import vue.AudioManager;
import vue.GameView;
import vue.ViewManager;

public class GameController {

    private final Jeu jeu;
    private final GameView view;
    private final Stage stage;

    public GameController(Stage stage) {
        this.stage = stage;
        this.jeu = new Jeu();
        this.view = new GameView(stage, jeu);

        setupControls(view.getScene());
        AudioManager.playGameMusic();
    }

    private void setupControls(Scene scene) {
        scene.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case UP -> jeu.deplacerJoueurHaut();
                case DOWN -> jeu.deplacerJoueurBas();
                case LEFT -> jeu.deplacerJoueurGauche();
                case RIGHT -> jeu.deplacerJoueurDroite();
                case ESCAPE -> ViewManager.showMenuView(stage);
            }

            jeu.miseAJour();
            view.draw();

            if (jeu.isWin()) {
                AudioManager.playWinMusic();
                ViewManager.showGameOverView(stage, true, jeu.getScore().getScoreActuel());
            }

            if (jeu.isLose()) {
                AudioManager.playLoseMusic();
                ViewManager.showGameOverView(stage, false, jeu.getScore().getScoreActuel());
            }
        });
    }

    public GameView getView() {
        return view;
    }
}

