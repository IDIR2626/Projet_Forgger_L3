package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import model.Element;
import model.Grille;
import model.Jeu;
import model.Ligne;
import model.TypeLigne;

public class GameView {

    private static final int TAILLE_CASE = 42;
    private static final int LARGEUR_CANVAS = Grille.LARGEUR * TAILLE_CASE;
    private static final int HAUTEUR_CANVAS = Grille.HAUTEUR * TAILLE_CASE;

    private final Jeu jeu;
    private final Canvas canvas;
    private final Scene scene;

    private final Label titleLabel;
    private final Label scoreLabel;
    private final Label bestScoreLabel;
    private final Label helpLabel;

    public GameView(Jeu jeu) {
        this.jeu = jeu;

        this.canvas = new Canvas(LARGEUR_CANVAS, HAUTEUR_CANVAS);

        titleLabel = new Label("FROGGER");
        titleLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 26));
        titleLabel.setTextFill(Color.web("#D6FF6B"));

        scoreLabel = new Label();
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        scoreLabel.setTextFill(Color.WHITE);

        bestScoreLabel = new Label();
        bestScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        bestScoreLabel.setTextFill(Color.WHITE);

        helpLabel = new Label("Déplacement : flèches ou ZQSD   |   ESC : menu");
        helpLabel.setFont(Font.font("Arial", 15));
        helpLabel.setTextFill(Color.web("#CFCFCF"));

        HBox topBar = new HBox(30, titleLabel, scoreLabel, bestScoreLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12, 18, 12, 18));
        topBar.setStyle(
                "-fx-background-color: linear-gradient(to right, #111111, #1d1d1d);" +
                        "-fx-border-color: #3a3a3a;" +
                        "-fx-border-width: 0 0 2 0;");

        HBox bottomBar = new HBox(helpLabel);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setStyle(
                "-fx-background-color: #111111;" +
                        "-fx-border-color: #3a3a3a;" +
                        "-fx-border-width: 2 0 0 0;");

        StackPane centerPane = new StackPane(canvas);
        centerPane.setPadding(new Insets(16));
        centerPane.setStyle("-fx-background-color: radial-gradient(radius 120%, #202020, #0d0d0d);");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(centerPane);
        root.setBottom(bottomBar);

        this.scene = new Scene(root, LARGEUR_CANVAS + 80, HAUTEUR_CANVAS + 120);

        applyStyle();
        draw();
    }

    private void applyStyle() {
        try {
            java.net.URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.out.println("Erreur CSS GameView : " + e.getMessage());
        }
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // fond général
        gc.setFill(Color.web("#101010"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // dessin des lignes
        for (int y = 0; y < jeu.getGrille().getLignes().size(); y++) {
            Ligne ligne = jeu.getGrille().getLignes().get(y);
            double py = y * TAILLE_CASE;

            drawLineBackground(gc, ligne.getType(), py);
            drawGridOverlay(gc, py);

            if (ligne.getType() == TypeLigne.ROUTE) {
                drawRoadMarkers(gc, py);
            }

            if (ligne.getType() == TypeLigne.ARRIVEE) {
                drawArrivalSlots(gc, py);
            }

            drawElements(gc, ligne, y);
        }

        drawPlayer(gc);
        updateHud();
    }

    private void drawLineBackground(GraphicsContext gc, TypeLigne type, double py) {
        switch (type) {
            case HERBE -> {
                gc.setFill(Color.web("#2E7D32"));
                gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

                gc.setFill(Color.web("#3FA34D"));
                for (int x = 0; x < LARGEUR_CANVAS; x += 14) {
                    gc.fillRect(x, py + 6, 4, 4);
                    gc.fillRect(x + 7, py + 22, 3, 3);
                }
            }
            case ROUTE -> {
                gc.setFill(Color.web("#454545"));
                gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

                gc.setFill(Color.web("#3B3B3B"));
                gc.fillRect(0, py + TAILLE_CASE - 6, LARGEUR_CANVAS, 6);
            }
            case ARRIVEE -> {
                gc.setFill(Color.web("#8BC34A"));
                gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

                gc.setFill(Color.web("#76A63A"));
                gc.fillRect(0, py + TAILLE_CASE - 5, LARGEUR_CANVAS, 5);
            }
            case DEPART -> {
                gc.setFill(Color.web("#1976D2"));
                gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

                gc.setFill(Color.web("#42A5F5"));
                gc.fillRect(0, py, LARGEUR_CANVAS, 7);
            }
        }
    }

    private void drawGridOverlay(GraphicsContext gc, double py) {
        gc.setStroke(Color.rgb(255, 255, 255, 0.08));
        gc.setLineWidth(1);

        for (int x = 0; x <= Grille.LARGEUR; x++) {
            double px = x * TAILLE_CASE;
            gc.strokeLine(px, py, px, py + TAILLE_CASE);
        }

        gc.setStroke(Color.rgb(0, 0, 0, 0.25));
        gc.strokeLine(0, py, LARGEUR_CANVAS, py);
    }

    private void drawRoadMarkers(GraphicsContext gc, double py) {
        gc.setFill(Color.web("#F4E04D"));
        for (int x = 10; x < LARGEUR_CANVAS; x += 70) {
            gc.fillRoundRect(x, py + TAILLE_CASE / 2.0 - 3, 34, 6, 6, 6);
        }
    }

    private void drawArrivalSlots(GraphicsContext gc, double py) {
        gc.setFill(Color.rgb(255, 255, 255, 0.18));
        int slotWidth = TAILLE_CASE * 2;

        for (int x = 0; x < Grille.LARGEUR; x += 3) {
            gc.fillRoundRect(x * TAILLE_CASE + 6, py + 7, slotWidth - 12, TAILLE_CASE - 14, 12, 12);
        }
    }

    private void drawElements(GraphicsContext gc, Ligne ligne, int y) {
        int i = 0;
        for (Element e : ligne.getElements()) {
            double px = e.getX() * TAILLE_CASE;
            double py = y * TAILLE_CASE;

            Color bodyColor;
            switch (i % 4) {
                case 0 -> bodyColor = Color.web("#E53935");
                case 1 -> bodyColor = Color.web("#FB8C00");
                case 2 -> bodyColor = Color.web("#8E24AA");
                default -> bodyColor = Color.web("#00ACC1");
            }

            // ombre
            gc.setFill(Color.rgb(0, 0, 0, 0.30));
            gc.fillRoundRect(px + 4, py + 9, TAILLE_CASE - 6, TAILLE_CASE - 12, 12, 12);

            // corps voiture
            gc.setFill(bodyColor);
            gc.fillRoundRect(px + 2, py + 6, TAILLE_CASE - 4, TAILLE_CASE - 12, 12, 12);

            // toit / vitre
            gc.setFill(Color.rgb(255, 255, 255, 0.35));
            gc.fillRoundRect(px + 9, py + 11, TAILLE_CASE - 18, TAILLE_CASE - 22, 8, 8);

            // roues
            gc.setFill(Color.web("#1A1A1A"));
            gc.fillOval(px + 5, py + 4, 8, 8);
            gc.fillOval(px + TAILLE_CASE - 13, py + 4, 8, 8);
            gc.fillOval(px + 5, py + TAILLE_CASE - 12, 8, 8);
            gc.fillOval(px + TAILLE_CASE - 13, py + TAILLE_CASE - 12, 8, 8);

            i++;
        }
    }

    private void drawPlayer(GraphicsContext gc) {
        double px = jeu.getJoueur().getX() * TAILLE_CASE;
        double py = jeu.getJoueur().getY() * TAILLE_CASE;

        // ombre
        gc.setFill(Color.rgb(0, 0, 0, 0.35));
        gc.fillOval(px + 7, py + 8, TAILLE_CASE - 10, TAILLE_CASE - 10);

        // corps
        gc.setFill(Color.web("#7CFC00"));
        gc.fillOval(px + 4, py + 4, TAILLE_CASE - 8, TAILLE_CASE - 8);

        // ventre
        gc.setFill(Color.web("#B9FF7A"));
        gc.fillOval(px + 11, py + 13, TAILLE_CASE - 22, TAILLE_CASE - 22);

        // yeux
        gc.setFill(Color.WHITE);
        gc.fillOval(px + 10, py + 8, 8, 8);
        gc.fillOval(px + 24, py + 8, 8, 8);

        gc.setFill(Color.BLACK);
        gc.fillOval(px + 13, py + 10, 3.5, 3.5);
        gc.fillOval(px + 27, py + 10, 3.5, 3.5);
    }

    private void updateHud() {
        scoreLabel.setText("Score : " + jeu.getScore().getScoreActuel());
        bestScoreLabel.setText("Meilleur score : " + jeu.getScore().getMeilleurScore());
    }

    public Scene getScene() {
        return scene;
    }
}