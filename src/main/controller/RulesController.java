package controller;

import javafx.stage.Stage;
import vue.RulesView;
import vue.ViewManager;
import vue.AudioManager;

public class RulesController {

    private final RulesView view;

    public RulesController(Stage stage) {
        this.view = new RulesView(stage);
        
        // Lancer la musique des règles
        AudioManager.playRulesMusic();

        view.getRetourButton().setOnAction(e -> {
            ViewManager.showMenuView(stage);
        });
    }

    public RulesView getView() {
        return view;
    }
    
}
