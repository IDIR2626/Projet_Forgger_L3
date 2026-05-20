package controller;

import javafx.stage.Stage;
import vue.SettingsView;
import vue.ViewManager;
import vue.AudioManager;

public class SettingsViewController {

    private final SettingsView view;

    public SettingsViewController(Stage stage) {
        this.view = new SettingsView(stage);
    }

    public SettingsView getView() {
        return view;
    }
}
