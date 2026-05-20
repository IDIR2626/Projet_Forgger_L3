package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RulesView {

    private final Scene scene;
    private final Button retourButton;

    public RulesView(Stage stage) {
        // ── Titre ──────────────────────────────────────────────────────────────
        Label frog = new Label("🐸");
        frog.setFont(Font.font("Arial", 48));

        Label titre = new Label("Règles du jeu");
        titre.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 36));
        titre.setTextFill(Color.web("#FFD600"));
        titre.getStyleClass().add("titre");

        Separator sep = new Separator();
        sep.setMaxWidth(500);
        sep.setStyle("-fx-background-color: #FFD600;");

        // ── Règles ─────────────────────────────────────────────────────────────
        String[] regles = {
            "🎮  Utilise les flèches (ou Z/Q/S/D) pour déplacer la grenouille.",
            "🚗  Évite les voitures sur la route.",
            "🌊  Traverse la rivière en sautant sur les troncs.",
            "☠️   Si tu touches un obstacle ou tombes dans l'eau : défaite.",
            "🏆  Atteins la ligne du haut pour gagner !",
            "⎋   Appuie sur ÉCHAP pour revenir au menu."
        };

        VBox reglesBox = new VBox(12);
        reglesBox.setAlignment(Pos.CENTER_LEFT);
        reglesBox.setMaxWidth(580);

        for (String r : regles) {
            Label l = new Label(r);
            l.setFont(Font.font("Arial", FontWeight.BOLD, 22));
            l.setTextFill(Color.web("#FFD600"));
            l.setWrapText(true);
            l.setStyle("-fx-background-color: rgba(0, 100, 0, 0.5); " +
                       "-fx-background-radius: 15; " +
                       "-fx-padding: 12 18; " +
                       "-fx-text-fill: #FFD600; " +
                       "-fx-border-color: #90EE90; " +
                       "-fx-border-width: 2; " +
                       "-fx-border-radius: 15; " +
                       "-fx-effect: dropshadow(gaussian, #90EE90, 8, 0.3, 0, 0);");
            reglesBox.getChildren().add(l);
        }

        // ── Bouton retour ──────────────────────────────────────────────────────
        retourButton = new Button("← Retour");
        retourButton.getStyleClass().add("menu-button");

        // ── Layout avec thème du jeu et plein écran dynamique ──────────────────
        VBox root = new VBox(20, frog, titre, sep, reglesBox, retourButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #90EE90, yellow, #006400);");

        // Utilise la taille de l'écran comme MenuView
        double w = javafx.stage.Screen.getPrimary().getBounds().getWidth();
        double h = javafx.stage.Screen.getPrimary().getBounds().getHeight();
        this.scene = new Scene(root, w, h);
        applyStyle();

        // Permet le retour même sans RulesController
        retourButton.setOnAction(e -> {
            vue.AudioManager.stop();
            vue.ViewManager.showMenuView(stage);
        });
    }

    private void applyStyle() {
        try {
            java.net.URL css = getClass().getResource("/style.css");
            if (css != null) scene.getStylesheets().add(css.toExternalForm());
        } catch (Exception e) {
            System.out.println("Erreur CSS RulesView : " + e.getMessage());
        }
    }

    // Seul getter nécessaire : le controller branche l'action
    public Button getRetourButton() { return retourButton; }

    public Scene getScene() { return scene; }
}