package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Jeu;
import vue.AudioManager;
import vue.GameView;
import vue.ViewManager;
import model.ModeJeu;

public class GameController {

    private final Jeu jeu;
    private final GameView view;
    private final Stage stage;
    private final Timeline gameLoop;

    public GameController(Stage stage) {
        this(stage, ModeJeu.CLASSIQUE);
    }

    public GameController(Stage stage, ModeJeu modeJeu) {
        this.stage = stage;
        this.jeu = new Jeu(modeJeu);
        this.view = new GameView(jeu);

        setupControls(view.getScene());

        this.gameLoop = new Timeline(
                new KeyFrame(Duration.millis(250), e -> tick()));
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);

        AudioManager.playGameMusic();
        jeu.demarrer();
        gameLoop.play();
    }

    private void setupControls(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            switch (code) {
                case UP, Z -> jeu.deplacerJoueurHaut();
                case DOWN, S -> jeu.deplacerJoueurBas();
                case LEFT, Q -> jeu.deplacerJoueurGauche();
                case RIGHT, D -> jeu.deplacerJoueurDroite();
                case ESCAPE -> {
                    stopGame();
                    ViewManager.showMenuView(stage);
                    return;
                }
            }

            view.draw();
            checkGameState();
        });
    }

    private void tick() {
        jeu.update();
        view.draw();
        checkGameState();
    }

    private void checkGameState() {
        if (jeu.isWin()) {
            stopGame();
            AudioManager.playWinMusic();
            ViewManager.showGameOverView(stage, true, jeu.getScore().getScoreActuel());
            return;
        }

        if (jeu.isLose()) {
            stopGame();
            AudioManager.playLoseMusic();
            ViewManager.showGameOverView(stage, false, jeu.getScore().getScoreActuel());
        }
    }

    private void stopGame() {
        gameLoop.stop();
        AudioManager.stop();
        jeu.arreter();
    }

    public GameView getView() {
        return view;
    }
}