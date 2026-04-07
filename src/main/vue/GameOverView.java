package vue;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverView {

    private Scene scene;
    private Button retryButton;
    private Button menuButton;

    public GameOverView(Stage stage, int score, boolean isWin) {
        String imageName = isWin ? "youwin.jpg" : "youlose.jpg";

        ImageView background = null;
        try {
            java.net.URL imgUrl = getClass().getResource("/Images/" + imageName);
            if (imgUrl != null) {
                background = new ImageView(new Image(imgUrl.toExternalForm()));
                background.setFitWidth(900);
                background.setFitHeight(700);
                background.setPreserveRatio(false);
                background.setSmooth(true);
            }
        } catch (Exception e) {
            System.out.println("Erreur image Game Over : " + e.getMessage());
        }

        Label scoreLabel = new Label("Score : " + score);
        scoreLabel.getStyleClass().add("score-label");

        retryButton = new Button("Rejouer");
        retryButton.getStyleClass().add("frogger-button");

        menuButton = new Button("Menu Principal");
        menuButton.getStyleClass().add("frogger-button");

        // Actions des boutons
        retryButton.setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showGameView(stage);
        });

        menuButton.setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showMenuView(stage);
        });

        VBox content = new VBox(20, scoreLabel, retryButton, menuButton);
        content.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        if (background != null) {
            root.getChildren().add(background);
        }
        root.getChildren().add(content);

        scene = new Scene(root, 900, 700);
        applyStyle();
    }

    private void applyStyle() {
        try {
            java.net.URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.out.println("Erreur CSS : " + e.getMessage());
        }
    }

    public Scene getScene() {
        return scene;
    }

    public Button getRetryButton() {
        return retryButton;
    }

    public Button getMenuButton() {
        return menuButton;
    }
}