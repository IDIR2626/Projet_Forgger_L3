package controller;

import javafx.stage.Stage;
import model.ModeJeu;
import vue.AudioManager;
import vue.MenuView;
import vue.ViewManager;

public class MenuController {

    private final MenuView view;

    public MenuController(Stage stage) {
        this.view = new MenuView(stage);

        view.getStartButton().setOnAction(e ->
            view.playButtonAnimation(view.getStartButton(), () ->
                view.showModeChoice(
                    () -> { AudioManager.stop(); ViewManager.showGameView(stage, ModeJeu.CLASSIQUE); },
                    () -> { AudioManager.stop(); ViewManager.showGameView(stage, ModeJeu.TOXIQUE_FERROVIAIRE); }
                )
            )
        );

        view.getRulesButton().setOnAction(e ->
            view.playButtonAnimation(view.getRulesButton(), () -> {
                AudioManager.stop();
                ViewManager.showRulesView(stage);
            })
        );

        view.getQuitButton().setOnAction(e ->
            view.playButtonAnimation(view.getQuitButton(), () -> {
                AudioManager.stop();
                stage.close();
            })
        );
    }

    public MenuView getView() { return view; }
}