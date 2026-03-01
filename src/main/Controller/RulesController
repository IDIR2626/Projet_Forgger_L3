package controller;

import javafx.stage.Stage;
import vue.RulesView;
import vue.ViewManager;
import vue.AudioManager;

public class RulesController {

    private final RulesView view;

    public RulesController(Stage stage) {
        this.view = new RulesView(stage);

        view.getRetourButton().setOnAction(e -> {
            AudioManager.stop();
            ViewManager.showMenuView(stage);
        });
    }

    public RulesView getView() {
        return view;
    }
}
