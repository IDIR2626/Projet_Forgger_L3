package com.frogger.vue;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RulesView {

    private final Scene scene;
    private Button retour;

    public Button getRetourButton() { return retour; }


    public RulesView(Stage stage) {

        Label titre = new Label("Règles du jeu Frogger");
        titre.getStyleClass().add("titre");

        Label texte = new Label("""
                • Utilise les flèches du clavier pour déplacer la grenouille.
                • Évite les voitures sur la route.
                • Traverse toutes les lignes pour atteindre la zone d'arrivée.
                • Si tu touches un obstacle : défaite.
                • Si tu atteins la ligne du haut : victoire !
                """);
        texte.getStyleClass().add("texte");

        retour = new Button("Retour");
        retour.setOnAction(e -> {
            AudioManager.playClick();
            ViewManager.showMenu(stage);
        });

        VBox root = new VBox(25, titre, texte, retour);
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
