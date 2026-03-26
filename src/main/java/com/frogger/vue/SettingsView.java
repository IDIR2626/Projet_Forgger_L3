package com.frogger.vue;

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

