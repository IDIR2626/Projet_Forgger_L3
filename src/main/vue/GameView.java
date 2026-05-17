package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.scene.image.Image;

import model.*;

public class GameView {

    // ── Taille d'une case ──────────────────────────────────────────────────────
    private static final int TAILLE_CASE = 52; // un peu plus grand = route plus haute
    private static final int LARGEUR_CANVAS = Grille.LARGEUR * TAILLE_CASE;
    private static final int HAUTEUR_CANVAS = Grille.HAUTEUR * TAILLE_CASE;

    private final Jeu jeu;
    private final Canvas canvas;
    private final Scene scene;

    // HUD labels
    private Label scoreValue;
    private Label bestScoreValue;
    private Label livesValue;

    // Images
    private final Image[] frogFrames = new Image[10];
    private int frogAnimationFrame = 0;

    private Image audiImage;
    private Image jeepImage;
    private Image teslaCyberImage;

    // ── Couleurs / palette ────────────────────────────────────────────────────
    private static final Color HUD_TOP = Color.web("#0D1B2A");
    private static final Color HUD_BOTTOM = Color.web("#1B3A4B");
    private static final Color ACCENT = Color.web("#00E5FF");
    private static final Color GOLD = Color.web("#FFD600");
    private static final Color HEART_RED = Color.web("#FF4757");
    private static final Color TEXT_DIM = Color.web("#90CAF9");

    public GameView(Jeu jeu) {
        this.jeu = jeu;
        this.canvas = new Canvas(LARGEUR_CANVAS, HAUTEUR_CANVAS);
        loadFrogImages();
        loadCarImages();

        HBox topBar = buildHud();

        StackPane gameWrapper = new StackPane(canvas);
        gameWrapper.setAlignment(Pos.TOP_CENTER);

        StackPane center = new StackPane(gameWrapper);
        center.setStyle("-fx-background-color: #071421;");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(center);
        root.setStyle("-fx-background-color: #0D1B2A;");

        scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());

        center.widthProperty().addListener((obs, oldVal, newVal) -> resizeGame(gameWrapper, center, topBar));
        center.heightProperty().addListener((obs, oldVal, newVal) -> resizeGame(gameWrapper, center, topBar));

        draw();
    }

    // ══════════════════════════════════════════════════════════════════════════
    // HUD
    // ══════════════════════════════════════════════════════════════════════════

    private HBox buildHud() {
        // ── Logo ──────────────────────────────────────────────────────────────
        Label logo = new Label("🐸 FROGGER");
        logo.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 26));
        logo.setTextFill(ACCENT);
        DropShadow glowLogo = new DropShadow(12, ACCENT);
        logo.setEffect(glowLogo);

        // ── Score card ────────────────────────────────────────────────────────
        scoreValue = valueLabel("0");
        bestScoreValue = valueLabel("0");
        livesValue = valueLabel("♥ ♥ ♥");
        livesValue.setTextFill(HEART_RED);

        VBox scoreCard = hudCard("SCORE", scoreValue, ACCENT);
        VBox bestCard = hudCard("MEILLEUR", bestScoreValue, GOLD);
        VBox livesCard = hudCard("VIES", livesValue, HEART_RED);

        // ── Séparateurs verticaux ─────────────────────────────────────────────
        Region sep1 = vSep();
        Region sep2 = vSep();

        // ── Barre principale ──────────────────────────────────────────────────
        HBox bar = new HBox(24, logo, sep1, scoreCard, sep2, bestCard, vSep(), livesCard);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(10, 20, 10, 20));
        bar.setPrefHeight(68);

        // Fond dégradé sombre avec bordure lumineuse en bas
        BackgroundFill fill = new BackgroundFill(
                new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, HUD_TOP),
                        new Stop(1, HUD_BOTTOM)),
                CornerRadii.EMPTY, Insets.EMPTY);
        bar.setBackground(new Background(fill));

        // Bordure lumineuse en bas
        BorderStroke border = new BorderStroke(
                Color.TRANSPARENT, ACCENT, Color.TRANSPARENT, Color.TRANSPARENT,
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0), Insets.EMPTY);
        bar.setBorder(new Border(border));

        return bar;
    }

    /** Carte de stat : libellé petit + valeur grande */
    private VBox hudCard(String label, Label value, Color accent) {
        Label lbl = new Label(label);
        lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 9));
        lbl.setTextFill(accent.darker());

        VBox box = new VBox(1, lbl, value);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private Label valueLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        l.setTextFill(Color.WHITE);
        return l;
    }

    private Region vSep() {
        Region r = new Region();
        r.setPrefWidth(1);
        r.setPrefHeight(36);
        r.setStyle("-fx-background-color: rgba(0,229,255,0.25);");
        return r;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // CHARGEMENT IMAGES
    // ══════════════════════════════════════════════════════════════════════════

    private void loadCarImages() {
        try {
            audiImage = new Image(getClass().getResourceAsStream("/Images/gameView/cars/audi.png"));
        } catch (Exception ignored) {
        }
        try {
            jeepImage = new Image(getClass().getResourceAsStream("/Images/gameView/cars/jeep.png"));
        } catch (Exception ignored) {
        }
        try {
            teslaCyberImage = new Image(getClass().getResourceAsStream("/Images/gameView/cars/tesla-cybertruck.png"));
        } catch (Exception ignored) {
        }
    }

    private void loadFrogImages() {
        for (int i = 0; i < frogFrames.length; i++) {
            try {
                String fn = String.format("/Images/gameView/frogger/%02d.png", i + 1);
                frogFrames[i] = new Image(getClass().getResourceAsStream(fn));
            } catch (Exception ignored) {
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // DESSIN PRINCIPAL
    // ══════════════════════════════════════════════════════════════════════════

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
            case TOXIQUE -> drawToxic(gc, py, y);
            case FERROVIAIRE -> drawRailway(gc, py);
            case ARRIVEE -> drawGoal(gc, py);
            case DEPART -> drawGrass(gc, py, y);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TUILES
    // ══════════════════════════════════════════════════════════════════════════

    private void drawRailway(GraphicsContext gc, double py) {
        gc.setFill(Color.web("#343434"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        gc.setFill(Color.rgb(255, 255, 255, 0.04));
        for (int x = 0; x < LARGEUR_CANVAS; x += 5) {
            for (int yy = 0; yy < TAILLE_CASE; yy += 5) {
                if ((x + yy) % 10 == 0) {
                    gc.fillRect(x, py + yy, 2, 2);
                }
            }
        }

        gc.setStroke(Color.web("#B0BEC5"));
        gc.setLineWidth(4);
        gc.strokeLine(0, py + 14, LARGEUR_CANVAS, py + 14);
        gc.strokeLine(0, py + TAILLE_CASE - 14, LARGEUR_CANVAS, py + TAILLE_CASE - 14);

        gc.setStroke(Color.web("#6D4C41"));
        gc.setLineWidth(4);
        for (int x = 0; x < LARGEUR_CANVAS; x += 38) {
            gc.strokeLine(x, py + 8, x + 16, py + TAILLE_CASE - 8);
        }

        gc.setFill(Color.rgb(0, 0, 0, 0.35));
        gc.fillRect(0, py, LARGEUR_CANVAS, 4);
        gc.fillRect(0, py + TAILLE_CASE - 4, LARGEUR_CANVAS, 4);
    }

    private void drawToxic(GraphicsContext gc, double py, int y) {
        gc.setFill(Color.web("#4A0072"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        gc.setFill(Color.web("#7B1FA2"));
        for (int x = 0; x < LARGEUR_CANVAS; x += 34) {
            gc.fillRect(x, py, 16, TAILLE_CASE);
        }

        double offset = (y * 19) % 45;

        gc.setFill(Color.rgb(0, 255, 120, 0.25));
        for (int x = -(int) offset; x < LARGEUR_CANVAS; x += 65) {
            gc.fillOval(x, py + 8, 42, 11);
        }

        gc.setFill(Color.rgb(255, 255, 255, 0.12));
        for (int x = (int) offset; x < LARGEUR_CANVAS; x += 80) {
            gc.fillOval(x, py + TAILLE_CASE - 17, 28, 7);
        }

        gc.setFill(Color.rgb(0, 0, 0, 0.25));
        gc.fillRect(0, py, LARGEUR_CANVAS, 3);
        gc.fillRect(0, py + TAILLE_CASE - 3, LARGEUR_CANVAS, 3);
    }

    private void drawGrass(GraphicsContext gc, double py, int y) {
        if (y == 6 || y == 9) {
            drawSidewalk(gc, py);
            return;
        }

        // Herbe base
        gc.setFill(Color.web("#4A9C3A"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        // Bandes légèrement plus claires
        gc.setFill(Color.web("#52AB40"));
        for (int x = 0; x < LARGEUR_CANVAS; x += 24) {
            gc.fillRect(x, py, 12, TAILLE_CASE);
        }

        // Points d'herbe aléatoires stylisés
        gc.setFill(Color.rgb(255, 255, 255, 0.08));
        for (int x = 10; x < LARGEUR_CANVAS; x += 56) {
            gc.fillOval(x, py + 6, 22, 8);
        }
        for (int x = 30; x < LARGEUR_CANVAS; x += 56) {
            gc.fillOval(x, py + TAILLE_CASE - 14, 18, 7);
        }
    }

    private void drawSidewalk(GraphicsContext gc, double py) {
        // Base béton
        gc.setFill(Color.web("#B8B0A0"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        // Dalles
        gc.setStroke(Color.web("#A09890"));
        gc.setLineWidth(1);
        for (int x = 0; x < LARGEUR_CANVAS; x += TAILLE_CASE) {
            gc.strokeRect(x + 1, py + 1, TAILLE_CASE - 2, TAILLE_CASE - 2);
        }

        // Ombre portée sur le bord bas
        gc.setFill(Color.rgb(0, 0, 0, 0.18));
        gc.fillRect(0, py + TAILLE_CASE - 4, LARGEUR_CANVAS, 4);
    }

    private void drawRoad(GraphicsContext gc, double py) {
        // Asphalte texturé
        gc.setFill(Color.web("#2C303A"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        // Grain subtil
        gc.setFill(Color.rgb(255, 255, 255, 0.025));
        for (int x = 0; x < LARGEUR_CANVAS; x += 4) {
            for (int yy = 0; yy < TAILLE_CASE; yy += 4) {
                if ((x + yy) % 8 == 0)
                    gc.fillRect(x, py + yy, 2, 2);
            }
        }

        // Bordures de voie
        gc.setFill(Color.rgb(0, 0, 0, 0.45));
        gc.fillRect(0, py, LARGEUR_CANVAS, 4);
        gc.fillRect(0, py + TAILLE_CASE - 4, LARGEUR_CANVAS, 4);

        // Ligne centrale pointillée jaune
        double cy = py + TAILLE_CASE / 2.0 - 1.5;
        gc.setStroke(Color.web("#FFD600"));
        gc.setLineWidth(3.5);
        gc.setLineDashes(22, 14);
        gc.strokeLine(0, cy, LARGEUR_CANVAS, cy);
        gc.setLineDashes(0);

        // Effet de brillance
        gc.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(255, 255, 255, 0.04)),
                new Stop(0.5, Color.TRANSPARENT)));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);
    }

    private void drawRiver(GraphicsContext gc, double py, int y) {
        // Eau profonde
        gc.setFill(Color.web("#0077B6"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        // Reflets animés (basés sur y pour variation)
        double offset = (y * 17) % 40;
        gc.setFill(Color.rgb(0, 180, 255, 0.22));
        for (int x = -(int) offset; x < LARGEUR_CANVAS; x += 60) {
            gc.fillOval(x, py + 8, 40, 10);
        }
        gc.setFill(Color.rgb(255, 255, 255, 0.10));
        for (int x = (int) offset; x < LARGEUR_CANVAS; x += 80) {
            gc.fillOval(x, py + TAILLE_CASE - 18, 30, 7);
        }

        // Ombre en bord haut/bas
        gc.setFill(Color.rgb(0, 0, 0, 0.2));
        gc.fillRect(0, py, LARGEUR_CANVAS, 3);
        gc.fillRect(0, py + TAILLE_CASE - 3, LARGEUR_CANVAS, 3);
    }

    private void drawGoal(GraphicsContext gc, double py) {
        // Fond sombre
        gc.setFill(Color.web("#0D2B1F"));
        gc.fillRect(0, py, LARGEUR_CANVAS, TAILLE_CASE);

        // Zones d'arrivée
        int zoneW = TAILLE_CASE * 2;
        int gap = (LARGEUR_CANVAS - 3 * zoneW) / 4;
        for (int i = 0; i < 3; i++) {
            int zx = gap + i * (zoneW + gap);
            gc.setFill(Color.web("#00C853"));
            gc.fillRoundRect(zx, py + 6, zoneW, TAILLE_CASE - 12, 8, 8);
            gc.setFill(Color.rgb(0, 0, 0, 0.3));
            gc.fillRoundRect(zx + 2, py + 8, zoneW - 4, TAILLE_CASE - 16, 6, 6);

            // Texte "🏠"
            gc.setFill(Color.web("#69F0AE"));
            gc.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            gc.fillText("🏠", zx + zoneW / 2.0 - 10, py + TAILLE_CASE / 2.0 + 7);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // ÉLÉMENTS MOBILES
    // ══════════════════════════════════════════════════════════════════════════

    private void drawElements(GraphicsContext gc, Ligne ligne, int y) {
        int i = 0;
        for (Element e : ligne.getElements()) {
            double px = e.getX() * TAILLE_CASE;
            double py = y * TAILLE_CASE;

            if (ligne.getType() == TypeLigne.ROUTE) {
                drawCar(gc, px, py, i, e.getVitesse());
            } else if (ligne.getType() == TypeLigne.RIVIERE) {
                drawLog(gc, px, py, e.getTaille());
            } else if (ligne.getType() == TypeLigne.HERBE) {
                drawObstacle(gc, px, py, i);
            } else if (ligne.getType() == TypeLigne.FERROVIAIRE) {
                drawTrain(gc, px, py, e.getTaille(), e.getVitesse());
            } else if (ligne.getType() == TypeLigne.TOXIQUE) {
                drawToxicPlatform(gc, px, py, e.getTaille());
            }
            i++;
        }
    }

    private void drawToxicPlatform(GraphicsContext gc, double px, double py, int taille) {
        double w = taille * TAILLE_CASE - 6;
        double h = TAILLE_CASE * 0.55;
        double y = py + (TAILLE_CASE - h) / 2.0;

        gc.setFill(Color.rgb(0, 0, 0, 0.35));
        gc.fillOval(px + 8, y + h - 4, w - 16, 10);

        gc.setFill(Color.web("#00C853"));
        gc.fillRoundRect(px + 3, y, w, h, 14, 14);

        gc.setFill(Color.web("#69F0AE"));
        gc.fillRoundRect(px + 8, y + 4, w - 16, h * 0.35, 10, 10);

        gc.setStroke(Color.web("#1B5E20"));
        gc.setLineWidth(2);
        for (int i = 1; i < taille; i++) {
            double lx = px + i * TAILLE_CASE - 3;
            gc.strokeLine(lx, y + 4, lx, y + h - 4);
        }
    }

    private void drawTrain(GraphicsContext gc, double px, double py, int taille, int vitesse) {
        double trainW = taille * TAILLE_CASE - 4;
        double trainH = TAILLE_CASE * 0.78;
        double x = px + 2;
        double y = py + (TAILLE_CASE - trainH) / 2.0;

        gc.setFill(Color.rgb(0, 0, 0, 0.45));
        gc.fillOval(x + 10, y + trainH - 3, trainW - 20, 10);

        gc.setFill(Color.web("#D32F2F"));
        gc.fillRoundRect(x, y, trainW, trainH, 8, 8);

        gc.setFill(Color.web("#FF5252"));
        gc.fillRoundRect(x + 5, y + 5, trainW - 10, trainH * 0.35, 6, 6);

        gc.setFill(Color.web("#263238"));
        for (int i = 0; i < taille; i++) {
            double wx = x + 12 + i * TAILLE_CASE;
            gc.fillRoundRect(wx, y + 9, 24, 12, 4, 4);
        }

        gc.setFill(Color.web("#FFD600"));
        if (vitesse > 0) {
            gc.fillOval(x + trainW - 10, y + trainH / 2 - 4, 8, 8);
        } else {
            gc.fillOval(x + 2, y + trainH / 2 - 4, 8, 8);
        }
    }

    // ── Voiture ───────────────────────────────────────────────────────────────
    private void drawCar(GraphicsContext gc, double px, double py, int index, int vitesse) {
        Image carImage;
        if (vitesse < 0) {
            carImage = teslaCyberImage;
        } else {
            carImage = (index % 2 == 0) ? audiImage : jeepImage;
        }

        // Taille réaliste : largeur = 2.2 cases, hauteur = 0.85 case
        double w = TAILLE_CASE * 2.2;
        double h = TAILLE_CASE * 0.85;

        double x = px + (TAILLE_CASE - w) / 2.0;
        double y = py + (TAILLE_CASE - h) / 2.0;

        // Ombre portée sous la voiture
        gc.setFill(Color.rgb(0, 0, 0, 0.40));
        gc.fillOval(x + 12, y + h - 6, w - 24, 10);

        if (carImage != null && !carImage.isError()) {
            gc.drawImage(carImage, x, y, w, h);
        } else {
            // Fallback stylisé si image manquante
            gc.setFill(vitesse < 0 ? Color.web("#607D8B")
                    : (index % 2 == 0 ? Color.web("#E53935") : Color.web("#1E88E5")));
            gc.fillRoundRect(x, y, w, h, 10, 10);
            gc.setFill(Color.rgb(255, 255, 255, 0.3));
            gc.fillRoundRect(x + 8, y + 6, w * 0.4, h * 0.4, 4, 4);
        }
    }

    // ── Tronc ─────────────────────────────────────────────────────────────────
    private void drawLog(GraphicsContext gc, double px, double py, int taille) {
        double logW = taille * TAILLE_CASE - 6;
        double logH = TAILLE_CASE * 0.55;
        double logY = py + (TAILLE_CASE - logH) / 2.0;

        // Ombre
        gc.setFill(Color.rgb(0, 0, 0, 0.30));
        gc.fillOval(px + 8, logY + logH - 4, logW - 16, 10);

        // Corps du tronc
        gc.setFill(Color.web("#6D4C41"));
        gc.fillRoundRect(px + 2, logY, logW, logH, 14, 14);

        // Lignes de bois
        gc.setStroke(Color.web("#5D4037"));
        gc.setLineWidth(1.5);
        for (int i = 1; i < taille; i++) {
            double lx = px + i * TAILLE_CASE - 3;
            gc.strokeLine(lx, logY + 4, lx, logY + logH - 4);
        }

        // Reflet
        gc.setFill(Color.rgb(255, 255, 255, 0.12));
        gc.fillRoundRect(px + 6, logY + 3, logW - 12, logH * 0.35, 10, 10);
    }

    // ── Obstacles herbe ───────────────────────────────────────────────────────
    private void drawObstacle(GraphicsContext gc, double px, double py, int index) {
        if (index % 3 == 0)
            drawTree(gc, px, py);
        else if (index % 3 == 1)
            drawRock(gc, px, py);
        else
            drawBush(gc, px, py);
    }

    private void drawTree(GraphicsContext gc, double px, double py) {
        // Tronc
        gc.setFill(Color.web("#795548"));
        gc.fillRoundRect(px + 20, py + 24, 10, 22, 4, 4);
        // Feuillage (3 couches)
        gc.setFill(Color.web("#2E7D32"));
        gc.fillOval(px + 6, py + 14, 36, 28);
        gc.setFill(Color.web("#388E3C"));
        gc.fillOval(px + 10, py + 6, 28, 24);
        gc.setFill(Color.web("#43A047"));
        gc.fillOval(px + 14, py + 2, 20, 18);
        // Reflet
        gc.setFill(Color.rgb(255, 255, 255, 0.15));
        gc.fillOval(px + 16, py + 4, 8, 6);
    }

    private void drawRock(GraphicsContext gc, double px, double py) {
        gc.setFill(Color.web("#546E7A"));
        gc.fillOval(px + 8, py + 16, 32, 22);
        gc.setFill(Color.web("#607D8B"));
        gc.fillOval(px + 10, py + 14, 28, 18);
        gc.setFill(Color.rgb(255, 255, 255, 0.20));
        gc.fillOval(px + 12, py + 15, 10, 6);
        gc.setFill(Color.rgb(0, 0, 0, 0.25));
        gc.fillOval(px + 12, py + 32, 24, 6);
    }

    private void drawBush(GraphicsContext gc, double px, double py) {
        gc.setFill(Color.web("#2E7D32"));
        gc.fillOval(px + 4, py + 18, 20, 16);
        gc.fillOval(px + 20, py + 20, 18, 14);
        gc.fillOval(px + 12, py + 14, 22, 18);
        gc.setFill(Color.web("#388E3C"));
        gc.fillOval(px + 14, py + 12, 16, 14);
        gc.setFill(Color.rgb(255, 255, 255, 0.12));
        gc.fillOval(px + 16, py + 13, 6, 4);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // JOUEUR
    // ══════════════════════════════════════════════════════════════════════════

    private void drawPlayer(GraphicsContext gc) {
        double px = jeu.getJoueur().getX() * TAILLE_CASE;
        double py = jeu.getJoueur().getY() * TAILLE_CASE;

        // Ombre
        gc.setFill(Color.rgb(0, 0, 0, 0.30));
        gc.fillOval(px + 8, py + TAILLE_CASE - 10, TAILLE_CASE - 16, 9);

        Image frog = frogFrames[frogAnimationFrame];
        if (frog != null && !frog.isError()) {
            double size = TAILLE_CASE * 0.88;
            double x = px + (TAILLE_CASE - size) / 2.0;
            double y = py + (TAILLE_CASE - size) / 2.0;
            gc.drawImage(frog, x, y, size, size);
        } else {
            // Fallback grenouille simple
            gc.setFill(Color.web("#00C853"));
            gc.fillOval(px + 8, py + 8, TAILLE_CASE - 16, TAILLE_CASE - 16);
        }

        frogAnimationFrame = (frogAnimationFrame + 1) % frogFrames.length;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // HUD UPDATE
    // ══════════════════════════════════════════════════════════════════════════

    private void updateHud() {
        scoreValue.setText(String.valueOf(jeu.getScore().getScoreActuel()));
        bestScoreValue.setText(String.valueOf(jeu.getScore().getMeilleurScore()));

        int v = jeu.getVies();
        livesValue.setText("♥ ".repeat(Math.max(0, v)).trim());
    }

    private void resizeGame(StackPane gameWrapper, StackPane center, HBox topBar) {
        double availableWidth = center.getWidth();
        double availableHeight = center.getHeight();

        if (availableWidth <= 0 || availableHeight <= 0) {
            return;
        }

        double scaleX = availableWidth / LARGEUR_CANVAS;
        double scaleY = availableHeight / HAUTEUR_CANVAS;

        // On garde les proportions du design
        double scale = Math.min(scaleX, scaleY);

        gameWrapper.setScaleX(scale);
        gameWrapper.setScaleY(scale);
    }

    public Scene getScene() {
        return scene;
    }
}