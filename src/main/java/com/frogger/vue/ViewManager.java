package com.frogger.vue;

import com.frogger.controller.GameController;
import com.frogger.controller.MenuController;
import com.frogger.controller.RulesController;
import javafx.stage.Stage;

public class ViewManager {

    public static void showMainView(Stage stage) {
        Mainview main = new Mainview(stage);
        stage.setScene(main.getScene());
    }

    // convenience alias for old call sites
    public static void showMenu(Stage stage) {
        showMenuView(stage);
    }

    public static void showMenuView(Stage stage) {
        MenuController controller = new MenuController(stage);
        stage.setScene(controller.getView().getScene());
    }

    public static void showGameView(Stage stage) {
        GameController controller = new GameController(stage);
        stage.setScene(controller.getView().getScene());
    }

    public static void showRulesView(Stage stage) {
        RulesController controller = new RulesController(stage);
        stage.setScene(controller.getView().getScene());
    }

    public static void showGameOverView(Stage stage, boolean win, int score) {
        GameOverView gov = new GameOverView(stage, win, score);
        stage.setScene(gov.getScene());
    }

    public static void showSettingsView(Stage stage) {
        SettingsView settings = new SettingsView(stage);
        stage.setScene(settings.getScene());
    }
}


