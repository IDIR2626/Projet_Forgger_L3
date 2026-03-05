package vue;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Jeu;

public class FroggerApp extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Frogger - L3");

        // Activer le mode plein écran
        stage.setFullScreen(true);

        // Le jeu (modèle)
        Jeu jeu = new Jeu();

        // Affiche la splash screen au lancement (MainView)
        ViewManager.showMainView(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

