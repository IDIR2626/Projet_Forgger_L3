package vue;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuView {

    private final Stage stage;
    private final BorderPane root;
    private final Scene scene;

    // buttons need to be fields so other classes can reference them
    private Button startBtn;
    private Button rulesBtn;
    private Button settingsBtn;
    private Button quitBtn;

    public MenuView(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();

        // Utiliser la taille de l'écran pour le plein écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        this.scene = new Scene(root, screenWidth, screenHeight);

        // Arrière-plan vert avec dégradé incluant du jaune
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #90EE90, yellow, #006400);");

        // Appliquer l'effet de luminosité global
        root.setEffect(AudioManager.getGlobalColorAdjust());

        setupContent();
        applyStyle();
        AudioManager.playGameMusic(); // 🎵 Musique du menu (musique jeu 3)
    }

    private void setupContent() {

        // Obtenir la taille de l'écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // --- Image de fond ---
        Image backgroundImage = new Image(
                getClass().getResource("/Images/2.png").toExternalForm());

        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setPreserveRatio(true);

        // --- Logo ---
        Image logoImage = new Image(
                getClass().getResource("/Images/1.png").toExternalForm());

        ImageView logo = new ImageView(logoImage);
        logo.setFitWidth(250);
        logo.setPreserveRatio(true);

        // --- Boutons ---
        startBtn = new Button("Start Game");
        rulesBtn = new Button("Rules");
        settingsBtn = new Button("Settings");
        quitBtn = new Button("Quit");

        startBtn.getStyleClass().add("menu-button");
        rulesBtn.getStyleClass().add("menu-button");
        settingsBtn.getStyleClass().add("menu-button");
        quitBtn.getStyleClass().add("menu-button");

        // Actions avec animation (sans navigation)
        startBtn.setOnAction(e -> {
            playButtonAnimation(startBtn, () -> {
                // Juste l'animation, pas de navigation
            });
        });

        rulesBtn.setOnAction(e -> {
            playButtonAnimation(rulesBtn, () -> {
                // Juste l'animation, pas de navigation
            });
        });

        settingsBtn.setOnAction(e -> {
            playButtonAnimation(settingsBtn, () -> {
                ViewManager.showSettingsView(stage);
            });
        });

        quitBtn.setOnAction(e -> {
            playButtonAnimation(quitBtn, () -> {
                // Juste l'animation, pas de navigation
            });
        });

        // --- Layout des boutons ---
        VBox box = new VBox(40, logo, startBtn, rulesBtn, settingsBtn, quitBtn);
        box.setAlignment(Pos.CENTER);

        // --- Superposition : fond + contenu ---
        StackPane stack = new StackPane(backgroundView, box);
        root.setCenter(stack);
    }

    private void applyStyle() {
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm());
    }

    public Button getStartButton() {
        return startBtn;
    }

    public Button getRulesButton() {
        return rulesBtn;
    }

    public Button getSettingsButton() {
        return settingsBtn;
    }

    public Button getQuitButton() {
        return quitBtn;
    }

    public Scene getScene() {
        return scene;
    }

    // Méthode pour appliquer l'animation au clic du bouton
    private void playButtonAnimation(Button button, Runnable action) {
        // Animation d'agrandissement
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), button);
        scaleUp.setToX(1.15);
        scaleUp.setToY(1.15);

        // Animation de rétrécissement
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        // Animation d'opacité
        FadeTransition fade = new FadeTransition(Duration.millis(200), button);
        fade.setFromValue(1.0);
        fade.setToValue(0.8);
        fade.setAutoReverse(true);
        fade.setCycleCount(1);

        // Chaîner les animations et exécuter l'action à la fin
        SequentialTransition transition = new SequentialTransition(scaleUp, scaleDown, fade);
        transition.setOnFinished(e -> action.run());
        transition.play();
    }
}
