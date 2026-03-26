package com.frogger.vue;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Mainview {

    private final Stage stage;
    private final BorderPane root;
    private final Scene scene;

    public Mainview(Stage stage) {
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
        AudioManager.playAccueilMusic(); // 🎵 Musique d'accueil
    }

    private void setupContent() {

        // Obtenir la taille de l'écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // --- Image de splash (1.png) ---
        Image splashImage = null;
        try {
            splashImage = new Image(
                    getClass().getResource("/ressources/Images/1.png").toExternalForm()
            );
        } catch (Exception e) {
            System.out.println("Erreur chargement image : " + e.getMessage());
            // Fallback : créer une image vide ou utiliser une couleur
        }

        if (splashImage == null) {
            // Si l'image ne charge pas, afficher un fond noir avec du texte
            System.out.println("Image non trouvée, affichage alternatif");
            // Pour l'instant, on continue avec une image vide
            splashImage = new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg=="); // Image transparente 1x1
        }

        ImageView splashView = new ImageView(splashImage);
        splashView.setFitWidth(800);
        splashView.setPreserveRatio(true);
        splashView.setOpacity(0); // Start invisible

        // --- Juste l'image, pas de boutons ---
        StackPane stack = new StackPane(splashView);
        root.setCenter(stack);

        // === TIMELINE D'ANIMATION ===
        // 1. Fade-In l'image (0.5s)
        FadeTransition fadeInImage = new FadeTransition(Duration.millis(500), splashView);
        fadeInImage.setFromValue(0);
        fadeInImage.setToValue(1);

        // 2. Attendre 3 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(5));

        // 3. Fade-Out l'image (0.5s) et puis afficher le menu
        FadeTransition fadeOutImage = new FadeTransition(Duration.millis(500), splashView);
        fadeOutImage.setFromValue(1);
        fadeOutImage.setToValue(0);

        // --- Chaîner les animations ---
        fadeInImage.setOnFinished(e -> pause.play());
        pause.setOnFinished(e -> fadeOutImage.play());

        // Après fade-out, afficher le menu
        fadeOutImage.setOnFinished(e -> {
            AudioManager.stop();
            ViewManager.showMenu(stage);
        });

        // Start
        fadeInImage.play();
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
