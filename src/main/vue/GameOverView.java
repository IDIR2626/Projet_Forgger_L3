package vue;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOverView {

    private final Scene scene;
    private Button retryButton;
    private Button menuButton;

    public GameOverView(Stage stage, int score, boolean isWin) {

    // ── Fond dégradé thème vert/sombre du jeu responsive ────────────────
    double w = javafx.stage.Screen.getPrimary().getBounds().getWidth();
    double h = javafx.stage.Screen.getPrimary().getBounds().getHeight();
    Rectangle background = new Rectangle(w, h);
    background.widthProperty().bind(stage.getScene() == null ? background.widthProperty() : stage.getScene().widthProperty());
    background.heightProperty().bind(stage.getScene() == null ? background.heightProperty() : stage.getScene().heightProperty());
    background.setFill(new LinearGradient(
        0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
        new Stop(0, Color.web("#90EE90")),
        new Stop(0.5, Color.web("#FFD600")),
        new Stop(1, Color.web("#006400"))));

    // Bordure décorative jaune (comme les boutons)
    Rectangle border = new Rectangle(w * 0.95, h * 0.85);
    border.setFill(Color.TRANSPARENT);
    border.setStroke(Color.web("#FFD600"));
    border.setStrokeWidth(4);
    border.setArcWidth(40);
    border.setArcHeight(40);

        // ── Grenouille + titre ────────────────────────────────────────────────
        Label frog = new Label("🐸");
        frog.setFont(Font.font("Arial", 72));

        String titreText = isWin ? "VICTOIRE !" : "GAME OVER";
        Color  titreColor = isWin ? Color.web("#ADFF2F") : Color.web("#90EE90");

        Label titre = new Label(titreText);
        titre.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 52));
        titre.setTextFill(titreColor);
        DropShadow glow = new DropShadow(25, titreColor);
        glow.setSpread(0.3);
        titre.setEffect(glow);

        // ── Séparateur jaune ──────────────────────────────────────────────────
        Separator sep = new Separator();
        sep.setMaxWidth(400);
        sep.setStyle("-fx-background-color: #FFD600;");


        // ── Score actuel esthétique ──────────────────────────────────────────
        HBox scoreLibelle = new HBox(8);
        Label trophy = new Label("🏆");
        trophy.setFont(Font.font("Arial", 32));
        Label scoreText = new Label("SCORE");
        scoreText.setFont(Font.font("Arial Black", FontWeight.BOLD, 22));
        scoreText.setTextFill(Color.web("#FFD600"));
        scoreLibelle.getChildren().addAll(trophy, scoreText);
        scoreLibelle.setAlignment(Pos.CENTER);

        Label scoreValeur = new Label(String.valueOf(score));
        scoreValeur.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 64));
        scoreValeur.setTextFill(Color.web("#FFFFFF"));
        DropShadow glowScore = new DropShadow(30, Color.web("#FFD600"));
        glowScore.setSpread(0.5);
        scoreValeur.setEffect(glowScore);
        scoreValeur.setStyle("-fx-background-color: rgba(255,255,255,0.10); -fx-background-radius: 30; -fx-padding: 18 60; -fx-border-color: #FFD600; -fx-border-width: 3; -fx-border-radius: 30; -fx-cursor: hand;");
        scoreValeur.setOnMouseEntered(e -> scoreValeur.setStyle("-fx-background-color: rgba(255,255,255,0.18); -fx-background-radius: 30; -fx-padding: 18 60; -fx-border-color: #FFD600; -fx-border-width: 3; -fx-border-radius: 30; -fx-cursor: hand; -fx-text-fill: #FFD600;"));
        scoreValeur.setOnMouseExited(e -> scoreValeur.setStyle("-fx-background-color: rgba(255,255,255,0.10); -fx-background-radius: 30; -fx-padding: 18 60; -fx-border-color: #FFD600; -fx-border-width: 3; -fx-border-radius: 30; -fx-cursor: hand;"));

        // ── Meilleur score ────────────────────────────────────────────────────
        model.Score s = new model.Score();
        // On s'assure que le meilleur score est bien à jour
        s.ajouterPoints(score);

        Label bestLibelle = new Label("MEILLEUR SCORE");
        bestLibelle.setFont(Font.font("Arial Black", FontWeight.BOLD, 14));
        bestLibelle.setTextFill(Color.web("#90EE90"));

        Label bestValeur = new Label(String.valueOf(s.getMeilleurScore()));
        bestValeur.setFont(Font.font("Arial Black", FontWeight.BOLD, 28));
        bestValeur.setTextFill(Color.web("#FFFFFF"));

        VBox scoreBox = new VBox(10, scoreLibelle, scoreValeur);
        scoreBox.setAlignment(Pos.CENTER);

        VBox bestBox = new VBox(10, bestLibelle, bestValeur);
        bestBox.setAlignment(Pos.CENTER);

        // Séparateur vertical entre les deux scores
        Separator sepV = new Separator(javafx.geometry.Orientation.VERTICAL);
        sepV.setStyle("-fx-background-color: #FFD600;");
        sepV.setPrefHeight(90);

        HBox scores = new HBox(60, scoreBox, sepV, bestBox);
        scores.setAlignment(Pos.CENTER);
        scores.setPadding(new Insets(20, 60, 20, 60));
        scores.setStyle(
            "-fx-background-color: rgba(0,100,0,0.25);" +
            "-fx-background-radius: 30;" +
            "-fx-border-color: #FFD600;" +
            "-fx-border-radius: 30;" +
            "-fx-border-width: 3;" +
            "-fx-effect: dropshadow(gaussian, #FFD600AA, 30, 0.2, 0, 0);");

        // ── Boutons ───────────────────────────────────────────────────────────
        retryButton = new Button("🔄  Rejouer");
        retryButton.getStyleClass().add("menu-button");

        menuButton = new Button("🏠  Menu Principal");
        menuButton.getStyleClass().add("menu-button");

        retryButton.setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showGameView(stage);
        });

        menuButton.setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showMenuView(stage);
        });

        HBox buttons = new HBox(30, retryButton, menuButton);
        buttons.setAlignment(Pos.CENTER);

        // ── Assemblage ────────────────────────────────────────────────────────
        VBox content = new VBox(32, frog, titre, sep, scores, buttons);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60));

        StackPane root = new StackPane(background, border, content);

        // Animation d'apparition
        content.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(600), content);
        fadeIn.setFromValue(0); fadeIn.setToValue(1);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(400), content);
        scaleIn.setFromX(0.85); scaleIn.setToX(1.0);
        scaleIn.setFromY(0.85); scaleIn.setToY(1.0);

        new SequentialTransition(new javafx.animation.ParallelTransition(fadeIn, scaleIn)).play();

        scene = new Scene(root, w, h);
        applyStyle();
    }

    private void applyStyle() {
        try {
            java.net.URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());
        } catch (Exception e) {
            System.out.println("Erreur CSS GameOver : " + e.getMessage());
        }
    }

    public Scene getScene()        { return scene; }
    public Button getRetryButton() { return retryButton; }
    public Button getMenuButton()  { return menuButton; }
}