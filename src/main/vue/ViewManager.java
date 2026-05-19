package vue;

import controller.GameController;
import controller.MenuController;
import javafx.stage.Stage;
import model.ModeJeu;

public class ViewManager {

    public static void showMainView(Stage stage) {
        Mainview main = new Mainview(stage);
        stage.setScene(main.getScene());
    }

    public static void showMenu(Stage stage) {
        showMenuView(stage);
    }

    public static void showMenuView(Stage stage) {
        MenuController controller = new MenuController(stage);
        stage.setScene(controller.getView().getScene());
    }

    public static void showGameView(Stage stage) {
        showGameView(stage, ModeJeu.CLASSIQUE);
    }

    public static void showGameView(Stage stage, ModeJeu modeJeu) {
        GameController controller = new GameController(stage, modeJeu);
        stage.setScene(controller.getView().getScene());
    }

    public static void showRulesView(Stage stage) {
        try {
            System.out.println("=== showRulesView appelé ===");
            RulesView view = new RulesView(stage);
            System.out.println("=== RulesView créé ===");
            stage.setScene(view.getScene());
            System.out.println("=== Scene changée ===");
        } catch (Exception ex) {
            System.out.println("=== ERREUR dans showRulesView ===");
            ex.printStackTrace();
        }
    }

    public static void showGameOverView(Stage stage, boolean win, int score) {
        GameOverView gov = new GameOverView(stage, score, win);
        stage.setScene(gov.getScene());
    }

    public static void showSettingsView(Stage stage) {
        SettingsView settings = new SettingsView(stage);
        stage.setScene(settings.getScene());
    }
}