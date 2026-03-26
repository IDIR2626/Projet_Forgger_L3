package com.frogger.controller;

import javafx.stage.Stage;
import com.frogger.vue.MenuView;
import com.frogger.vue.ViewManager;
import com.frogger.vue.AudioManager;

public class MenuController {

    private final MenuView view;

    public MenuController(Stage stage) {
        this.view = new MenuView(stage);

        view.getStartButton().setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showGameView(stage);
        });

        view.getRulesButton().setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showRulesView(stage);
        });

        view.getQuitButton().setOnAction(e -> {
            AudioManager.stop();
            stage.close();
        });
    }

    public MenuView getView() {
        return view;
    }
}
