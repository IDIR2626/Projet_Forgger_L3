package vue;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuView {

    private final Stage      stage;
    private final BorderPane root;
    private final Scene      scene;

    public  Button startBtn;
    public Button rulesBtn;
    public Button settingsBtn;
    public Button quitBtn;

    public MenuView(Stage stage) {
        this.stage = stage;
        this.root  = new BorderPane();

        double w = Screen.getPrimary().getBounds().getWidth();
        double h = Screen.getPrimary().getBounds().getHeight();
        this.scene = new Scene(root, w, h);

        root.setStyle("-fx-background-color: linear-gradient(to bottom, #90EE90, yellow, #006400);");
        root.setEffect(AudioManager.getGlobalColorAdjust());

        setupContent();
        applyStyle();
        AudioManager.playGameMusic();
    }

    void setupContent() {

        ImageView backgroundView = new ImageView(
                new Image(getClass().getResource("/Images/2.png").toExternalForm()));
        backgroundView.setFitWidth(800);
        backgroundView.setPreserveRatio(true);

        ImageView logo = new ImageView(
                new Image(getClass().getResource("/Images/1.png").toExternalForm()));
        logo.setFitWidth(250);
        logo.setPreserveRatio(true);

        startBtn    = new Button("Start Game");
        rulesBtn    = new Button("Rules");
        settingsBtn = new Button("Settings");
        quitBtn     = new Button("Quit");

        startBtn.getStyleClass().add("menu-button");
        rulesBtn.getStyleClass().add("menu-button");
        settingsBtn.getStyleClass().add("menu-button");
        quitBtn.getStyleClass().add("menu-button");

        // Branche le bouton Settings
        settingsBtn.setOnAction(e ->
            playButtonAnimation(settingsBtn, () -> {
                try {
                    ViewManager.showSettingsView(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Erreur lors de l'ouverture des paramètres : " + ex.getMessage());
                }
            })
        );

        // Branche le bouton Rules pour affichage direct dans le thème du jeu
        rulesBtn.setOnAction(e ->
            playButtonAnimation(rulesBtn, () -> {
                try {
                    ViewManager.showRulesView(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Erreur lors de l'ouverture des règles : " + ex.getMessage());
                }
            })
        );

        VBox box = new VBox(40, logo, startBtn, rulesBtn, settingsBtn, quitBtn);
        box.setAlignment(Pos.CENTER);

        root.setCenter(new StackPane(backgroundView, box));
    }

    public  void applyStyle() {
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm());
    }

    public Button getStartButton()    { return startBtn; }
    public Button getRulesButton()    { return rulesBtn; }
    public Button getSettingsButton() { return settingsBtn; }
    public Button getQuitButton()     { return quitBtn; }
    public Scene  getScene()          { return scene; }

    public void showModeChoice(Runnable classicAction, Runnable toxicRailAction) {

        ImageView backgroundView = new ImageView(
                new Image(getClass().getResource("/Images/2.png").toExternalForm()));
        backgroundView.setFitWidth(800);
        backgroundView.setPreserveRatio(true);

        ImageView logo = new ImageView(
                new Image(getClass().getResource("/Images/1.png").toExternalForm()));
        logo.setFitWidth(220);
        logo.setPreserveRatio(true);

        Button classicBtn   = new Button("Mode classique\nRoute + Rivière");
        Button toxicRailBtn = new Button("Mode avancé\nToxique + Ferroviaire");
        Button backBtn      = new Button("Retour");

        classicBtn.getStyleClass().add("menu-button");
        toxicRailBtn.getStyleClass().add("menu-button");
        backBtn.getStyleClass().add("menu-button");

        classicBtn.setOnAction(e   -> playButtonAnimation(classicBtn,   classicAction));
        toxicRailBtn.setOnAction(e -> playButtonAnimation(toxicRailBtn, toxicRailAction));
        backBtn.setOnAction(e      -> playButtonAnimation(backBtn,      this::setupContent));

        VBox box = new VBox(35, logo, classicBtn, toxicRailBtn, backBtn);
        box.setAlignment(Pos.CENTER);

        root.setCenter(new StackPane(backgroundView, box));
    }

    public void playButtonAnimation(Button button, Runnable action) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), button);
        scaleUp.setToX(1.15);
        scaleUp.setToY(1.15);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        FadeTransition fade = new FadeTransition(Duration.millis(200), button);
        fade.setFromValue(1.0);
        fade.setToValue(0.8);
        fade.setAutoReverse(true);
        fade.setCycleCount(1);

        SequentialTransition transition = new SequentialTransition(scaleUp, scaleDown, fade);
        transition.setOnFinished(e -> action.run());
        transition.play();
    }
}