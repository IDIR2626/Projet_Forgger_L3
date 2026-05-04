package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import model.*;

public class GameView {

    private static final int TAILLE_CASE = 48;
    private static final int LARGEUR_CANVAS = Grille.LARGEUR * TAILLE_CASE;
    private static final int HAUTEUR_CANVAS = Grille.HAUTEUR * TAILLE_CASE;

    private final Jeu jeu;
    private final Canvas canvas;
    private final Scene scene;

    private final Label scoreLabel;
    private final Label bestScoreLabel;
    private final Label livesLabel;

    public GameView(Jeu jeu) {
        this.jeu = jeu;
        this.canvas = new Canvas(LARGEUR_CANVAS, HAUTEUR_CANVAS);

        scoreLabel = label("", 18);
        bestScoreLabel = label("", 18);
        livesLabel = label("", 18);

        HBox topBar = new HBox(30, label("FROGGER", 30), scoreLabel, bestScoreLabel, livesLabel);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #0A6B5B;");

        StackPane center = new StackPane(canvas);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(center);

        scene = new Scene(root);
        draw();
    }

    private Label label(String text, int size) {
        Label l = new Label(text);
        l.setFont(Font.font("Verdana", FontWeight.BOLD, size));
        l.setTextFill(Color.WHITE);
        return l;
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int y = 0; y < jeu.getGrille().getLignes().size(); y++) {
            Ligne ligne = jeu.getGrille().getLignes().get(y);
            double py = y * TAILLE_CASE;

            drawLine(gc, ligne.getType(), py, y);
            drawElements(gc, ligne, y);
        }

        drawPlayer(gc);
        updateHud();
    }

    private void drawLine(GraphicsContext gc, TypeLigne type, double py, int y) {
        switch (type) {
            case HERBE -> drawGrass(gc, py, y);
            case ROUTE -> drawRoad(gc, py);
            case RIVIERE -> drawRiver(gc, py, y);
            case ARRIVEE -> drawGoal(gc, py);
            case DEPART -> drawGrass(gc, py, y);
        }
    }

    // =========================
    // 🌿 HERBE + TROTTOIR
    // =========================
    private void drawGrass(GraphicsContext gc, double py, int y) {

        if (y == 6 || y == 9) {
            drawSidewalk(gc, py);
            return;
        }

        gc.setFill(Color.web("#7ED957"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        gc.setFill(Color.rgb(255, 255, 255, 0.2));
        for (int x = 0; x < LARGEUR_CANVAS; x += 80) {
            gc.fillOval(x, py + 10, 30, 10);
        }
    }

    private void drawSidewalk(GraphicsContext gc, double py) {
        gc.setFill(Color.web("#CFC8B8"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        gc.setStroke(Color.GRAY);
        for (int x = 0; x < LARGEUR_CANVAS; x += 48) {
            gc.strokeRect(x, py, 48, TAILLE_CASE);
        }
    }

    // =========================
    // 🛣️ ROUTE
    // =========================
    private void drawRoad(GraphicsContext gc, double py) {

        gc.setFill(Color.web("#3E434D"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(3);

        for (int x = 0; x < LARGEUR_CANVAS; x += 70) {
            gc.strokeLine(x, py + TAILLE_CASE / 2.0, x + 30, py + TAILLE_CASE / 2.0);
        }
    }

    // =========================
    // 🌊 RIVIERE
    // =========================
    private void drawRiver(GraphicsContext gc, double py, int y) {
        gc.setFill(Color.web("#00AEEF"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        gc.setFill(Color.rgb(255, 255, 255, 0.2));
        for (int x = 0; x < LARGEUR_CANVAS; x += 80) {
            gc.fillOval(x, py + 10, 30, 10);
        }
    }

    // =========================
    // 🎯 ARRIVEE
    // =========================
    private void drawGoal(GraphicsContext gc, double py) {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);
    }

    // =========================
    // 🚗 ELEMENTS
    // =========================
    private void drawElements(GraphicsContext gc, Ligne ligne, int y) {

        int i = 0;

        for (Element e : ligne.getElements()) {
            double px = e.getX() * TAILLE_CASE;
            double py = y * TAILLE_CASE;

            if (ligne.getType() == TypeLigne.ROUTE) {
                drawCar(gc, px, py);
            } else if (ligne.getType() == TypeLigne.RIVIERE) {
                drawLog(gc, px, py, e.getTaille());
            } else if (ligne.getType() == TypeLigne.HERBE) {
                drawObstacle(gc, px, py, i);
            }

            i++;
        }
    }

    private void drawCar(GraphicsContext gc, double px, double py) {
        gc.setFill(Color.ORANGE);
        gc.fillRoundRect(px + 5, py + 10, 35, 20, 10, 10);

        gc.setFill(Color.BLACK);
        gc.fillOval(px + 8, py + 25, 8, 8);
        gc.fillOval(px + 28, py + 25, 8, 8);
    }

    private void drawLog(GraphicsContext gc, double px, double py, int taille) {
        gc.setFill(Color.BROWN);
        gc.fillRoundRect(px, py + 15, taille * TAILLE_CASE - 5, 20, 10, 10);
    }

    // =========================
    // 🌳 OBSTACLES
    // =========================
    private void drawObstacle(GraphicsContext gc, double px, double py, int index) {

        if (index % 3 == 0)
            drawTree(gc, px, py);
        else if (index % 3 == 1)
            drawRock(gc, px, py);
        else
            drawHouse(gc, px, py);
    }

    private void drawTree(GraphicsContext gc, double px, double py) {
        gc.setFill(Color.GREEN);
        gc.fillOval(px + 10, py + 5, 25, 25);
    }

    private void drawRock(GraphicsContext gc, double px, double py) {
        gc.setFill(Color.GRAY);
        gc.fillOval(px + 10, py + 10, 20, 15);
    }

    private void drawHouse(GraphicsContext gc, double px, double py) {
        gc.setFill(Color.RED);
        gc.fillRect(px + 10, py + 10, 20, 20);
    }

    // =========================
    // 🐸 JOUEUR
    // =========================
    private void drawPlayer(GraphicsContext gc) {
        double px = jeu.getJoueur().getX() * TAILLE_CASE;
        double py = jeu.getJoueur().getY() * TAILLE_CASE;

        gc.setFill(Color.LIMEGREEN);
        gc.fillOval(px + 10, py + 10, 25, 25);
    }

    private void updateHud() {
        scoreLabel.setText("Score : " + jeu.getScore().getScoreActuel());
        bestScoreLabel.setText("Meilleur score : " + jeu.getScore().getMeilleurScore());
        livesLabel.setText("Vies : " + "♥ ".repeat(jeu.getVies()));
    }

    public Scene getScene() {
        return scene;
    }
}