package vue;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverView {

    private final Scene scene;

    public GameOverView(Stage stage, boolean win, int score) {

        // Titre selon victoire ou défaite
        Label titre = new Label(win ? "YOU WIN !" : "GAME OVER");
        titre.getStyleClass().add("titre");

        // Score affiché
        Label scoreLabel = new Label("Score : " + score);
        scoreLabel.getStyleClass().add("texte");

        // Bouton rejouer
        Button retryBtn = new Button("Retry");
        retryBtn.getStyleClass().add("menu-button");
        retryBtn.setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showGameView(stage);
        });

        // Bouton menu
        Button menuBtn = new Button("Menu");
        menuBtn.getStyleClass().add("menu-button");
        menuBtn.setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showMenuView(stage);
        });

        VBox root = new VBox(25, titre, scoreLabel, retryBtn, menuBtn);
        root.setAlignment(Pos.CENTER);

        this.scene = new Scene(root, 900, 600);
        applyStyle();
    }

    private void applyStyle() {
        scene.getStylesheets().add(
                getClass().getResource("/ressources/style.css").toExternalForm()
        );
    }

    public Scene getScene() {
        return scene;
    }
}
