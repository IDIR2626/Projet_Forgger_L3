package controller;

import javafx.stage.Stage;
import vue.MenuView;
import vue.ViewManager;
import vue.AudioManager;
import model.ModeJeu;

public class MenuController {

    private final MenuView view;

    public MenuController(Stage stage) {
        this.view = new MenuView(stage);

        view.getStartButton().setOnAction(e -> {
            view.showModeChoice(
                    () -> {
                        AudioManager.stop();
                        ViewManager.showGameView(stage, ModeJeu.CLASSIQUE);
                    },
                    () -> {
                        AudioManager.stop();
                        ViewManager.showGameView(stage, ModeJeu.TOXIQUE_FERROVIAIRE);
                    });
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
