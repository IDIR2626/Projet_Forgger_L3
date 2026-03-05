/*package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsView {

    private final Stage stage;
    private final BorderPane root;
    private final Scene scene;

    public SettingsView(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();

        // Utiliser la taille de l'écran pour le plein écran
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        this.scene = new Scene(root, screenWidth, screenHeight);

        // Arrière-plan vert avec dégradé incluant du jaune
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #90EE90, yellow, #006400);");
        
        // Appliquer l'effet de luminosité global
        root.setEffect(AudioManager.getGlobalColorAdjust());

        setupContent();
        applyStyle();
    }

    private void setupContent() {

        // --- Image de fond ---
        Image backgroundImage = new Image(
                getClass().getResource("/ressources/Images/2.png").toExternalForm()
        );

        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setPreserveRatio(true);

        // --- Titre ---
        Label titleLabel = new Label("⚙️ SETTINGS");
        titleLabel.setStyle("-fx-font-size: 56px; -fx-font-family: 'Arial Black'; -fx-font-weight: bold; -fx-text-fill: #FFD700; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.9), 10, 0, 0, 4);");

        // --- Conteneur principal pour les paramètres ---
        VBox settingsContainer = new VBox(20);
        settingsContainer.setStyle("-fx-border-color: #FFD700; -fx-border-radius: 25; -fx-background-color: linear-gradient(to bottom, rgba(70, 130, 70, 0.8), rgba(128, 128, 0, 0.7), rgba(34, 80, 34, 0.8)); -fx-padding: 40; -fx-border-width: 4; -fx-effect: dropshadow(gaussian, rgba(255, 215, 0, 0.6), 20, 0, 0, 0);");
        settingsContainer.setMaxWidth(580);

        // --- Contrôles de volume ---
        Label volumeLabel = new Label("🔊 VOLUME");
        volumeLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700; -fx-font-weight: bold;");

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setPrefWidth(330);
        volumeSlider.setStyle("-fx-font-size: 14px; -fx-control-inner-background: #90EE90; -fx-padding: 8;");

        Label volumeValueLabel = new Label("50%");
        volumeValueLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700; -fx-font-weight: bold; -fx-min-width: 70;");

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            volumeValueLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
            AudioManager.setVolume(newVal.doubleValue() / 100.0);
        });

        HBox volumeBox = new HBox(20, volumeSlider, volumeValueLabel);
        volumeBox.setAlignment(Pos.CENTER_LEFT);
        volumeBox.setStyle("-fx-padding: 15; -fx-background-color: rgba(20, 20, 20, 0.6); -fx-border-radius: 10; -fx-spacing: 15;");

        VBox volumeSection = new VBox(12, volumeLabel, volumeBox);
        volumeSection.setStyle("-fx-border-color: #FFD700; -fx-border-radius: 18; -fx-background-color: linear-gradient(to bottom, rgba(50, 100, 50, 0.9), rgba(100, 100, 0, 0.85)); -fx-padding: 18; -fx-border-width: 3; -fx-effect: dropshadow(gaussian, rgba(255, 215, 0, 0.4), 12, 0, 0, 0);");
        volumeSection.setAlignment(Pos.TOP_LEFT);

        // --- Contrôles de luminosité ---
        Label brightnessLabel = new Label("☀️ BRIGHTNESS");
        brightnessLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700; -fx-font-weight: bold;");

        Slider brightnessSlider = new Slider(-1, 1, AudioManager.getBrightness());
        brightnessSlider.setPrefWidth(330);
        brightnessSlider.setStyle("-fx-font-size: 14px; -fx-control-inner-background: #90EE90; -fx-padding: 8;");

        double currentBrightness = AudioManager.getBrightness();
        int initialPercentage = (int) (currentBrightness * 50 + 50);
        Label brightnessValueLabel = new Label(initialPercentage + "%");
        brightnessValueLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700; -fx-font-weight: bold; -fx-min-width: 70;");

        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = newVal.doubleValue();
            int percentage = (int) (value * 50 + 50);
            brightnessValueLabel.setText(percentage + "%");
            // Appliquer à la luminosité globale
            AudioManager.setBrightness(value);
        });

        HBox brightnessBox = new HBox(20, brightnessSlider, brightnessValueLabel);
        brightnessBox.setAlignment(Pos.CENTER_LEFT);
        brightnessBox.setStyle("-fx-padding: 15; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-border-radius: 10; -fx-spacing: 15;");

        VBox brightnessSection = new VBox(12, brightnessLabel, brightnessBox);
        brightnessSection.setStyle("-fx-border-color: #FFD700; -fx-border-radius: 18; -fx-background-color: linear-gradient(to bottom, rgba(50, 100, 50, 0.9), rgba(100, 100, 0, 0.85)); -fx-padding: 18; -fx-border-width: 3; -fx-effect: dropshadow(gaussian, rgba(255, 215, 0, 0.4), 12, 0, 0, 0);");

        // Ajouter les sections au conteneur
        settingsContainer.getChildren().addAll(volumeSection, brightnessSection);
        settingsContainer.setAlignment(Pos.TOP_CENTER);

        // --- Bouton retour ---
        Button backBtn = new Button("← BACK TO MENU");
        backBtn.getStyleClass().add("menu-button");
        backBtn.setStyle("-fx-font-size: 22px; -fx-font-family: 'Arial Black'; -fx-padding: 15 50;");
        backBtn.setPrefWidth(250);
        backBtn.setOnAction(e -> {
            ViewManager.showMenuView(stage);
        });

        // --- Layout principal ---
        VBox mainContent = new VBox(45);
        mainContent.setPadding(new Insets(50));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.getChildren().addAll(titleLabel, settingsContainer, backBtn);

        // --- Superposition : fond + contenu ---
        StackPane stack = new StackPane(backgroundView, mainContent);
        root.setCenter(stack);
    }

    private void applyStyle() {
        scene.getStylesheets().add(
                getClass().getResource("/ressources/style.css").toExternalForm()
        );
    }

    public Scene getScene() {
        return scene;
    }
}*/
package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsView {

    private final Stage stage;
    private final BorderPane root;
    private final Scene scene;

    public SettingsView(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        this.scene = new Scene(root, screenWidth, screenHeight);

        root.setStyle("-fx-background-color: linear-gradient(to bottom, #90EE90, yellow, #006400);");
        root.setEffect(AudioManager.getGlobalColorAdjust());

        setupContent();
        applyStyle();
    }

    private void setupContent() {

        Image backgroundImage = new Image(
                getClass().getResource("/ressources/Images/2.png").toExternalForm()
        );

        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setPreserveRatio(true);

        Label titleLabel = new Label("⚙️ SETTINGS");
        titleLabel.setStyle(
                "-fx-font-size: 60px;" +
                "-fx-font-family: 'Arial Black';" +
                "-fx-text-fill: #FFD700;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.9), 12, 0, 0, 4);"
        );

        VBox settingsContainer = new VBox(30);
        settingsContainer.setMaxWidth(580);
        settingsContainer.setAlignment(Pos.TOP_CENTER);

        // 🔥 Nouveau style glassmorphism
        settingsContainer.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.12);" +
                "-fx-background-radius: 30;" +
                "-fx-border-radius: 30;" +
                "-fx-border-color: rgba(255, 215, 0, 0.75);" +
                "-fx-border-width: 3;" +
                "-fx-padding: 45;" +
                "-fx-effect: dropshadow(gaussian, rgba(255, 215, 0, 0.45), 25, 0.2, 0, 0);" +
                "-fx-backdrop-filter: blur(15px);"
        );

        // -------------------------
        // 🔊 VOLUME
        // -------------------------
        Label volumeLabel = new Label("🔊 VOLUME");
        volumeLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        Slider volumeSlider = new Slider(0, 100, 50);
        Label volumeValueLabel = new Label("50%");
        volumeValueLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            volumeValueLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
            AudioManager.setVolume(newVal.doubleValue() / 100.0);
        });

        // 🎚️ Style slider moderne
        String sliderStyle =
                "-fx-control-inner-background: transparent;" +
                "-fx-background-color: transparent;" +
                "-fx-padding: 5;" +
                "-fx-accent: #FFD700;";

        volumeSlider.setStyle(sliderStyle);

        HBox volumeBox = new HBox(20, volumeSlider, volumeValueLabel);
        volumeBox.setAlignment(Pos.CENTER_LEFT);

        VBox volumeSection = new VBox(12, volumeLabel, volumeBox);
        volumeSection.setAlignment(Pos.TOP_LEFT);

        // 🔥 Style section premium
        String sectionStyle =
                "-fx-background-color: rgba(0, 0, 0, 0.35);" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: rgba(255, 215, 0, 0.55);" +
                "-fx-border-width: 2;" +
                "-fx-padding: 22;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0.2, 0, 3);";

        volumeSection.setStyle(sectionStyle);

        // -------------------------
        // ☀️ BRIGHTNESS
        // -------------------------
        Label brightnessLabel = new Label("☀️ BRIGHTNESS");
        brightnessLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        Slider brightnessSlider = new Slider(-1, 1, AudioManager.getBrightness());
        brightnessSlider.setStyle(sliderStyle);

        int initialPercentage = (int) (AudioManager.getBrightness() * 50 + 50);
        Label brightnessValueLabel = new Label(initialPercentage + "%");
        brightnessValueLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = newVal.doubleValue();
            int percentage = (int) (value * 50 + 50);
            brightnessValueLabel.setText(percentage + "%");
            AudioManager.setBrightness(value);
        });

        HBox brightnessBox = new HBox(20, brightnessSlider, brightnessValueLabel);
        brightnessBox.setAlignment(Pos.CENTER_LEFT);

        VBox brightnessSection = new VBox(12, brightnessLabel, brightnessBox);
        brightnessSection.setStyle(sectionStyle);

        // Ajouter sections
        settingsContainer.getChildren().addAll(volumeSection, brightnessSection);

        // Bouton retour
        Button backBtn = new Button("← BACK TO MENU");
        backBtn.getStyleClass().add("menu-button");
        backBtn.setStyle("-fx-font-size: 22px; -fx-font-family: 'Arial Black'; -fx-padding: 15 50;");
        backBtn.setPrefWidth(250);
        backBtn.setOnAction(e -> ViewManager.showMenuView(stage));

        VBox mainContent = new VBox(45, titleLabel, settingsContainer, backBtn);
        mainContent.setPadding(new Insets(50));
        mainContent.setAlignment(Pos.TOP_CENTER);

        StackPane stack = new StackPane(backgroundView, mainContent);
        root.setCenter(stack);
    }

    private void applyStyle() {
        scene.getStylesheets().add(
                getClass().getResource("/ressources/style.css").toExternalForm()
        );
    }

    public Scene getScene() {
        return scene;
    }
}

