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
                getClass().getResource("/Images/2.png").toExternalForm()
        );

        ImageView backgroundView = new ImageView(backgroundImage);
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(true);
        backgroundView.setOpacity(0.24);

        Label titleLabel = new Label("⚙️ SETTINGS");
        titleLabel.setStyle(
                "-fx-font-size: 64px;" +
                "-fx-font-family: 'Arial Black';" +
                "-fx-text-fill: linear-gradient(from 0% 0% to 100% 100%, #FFE97B, #FFD700);" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 22, 0, 0, 6);"
        );

        VBox settingsContainer = new VBox(24);
        settingsContainer.setMaxWidth(720);
        settingsContainer.setMinWidth(620);
        settingsContainer.setAlignment(Pos.TOP_CENTER);
        settingsContainer.setPadding(new Insets(28));
        settingsContainer.setStyle(
                "-fx-background-color: rgba(12, 18, 30, 0.72);" +
                "-fx-background-radius: 34;" +
                "-fx-border-radius: 34;" +
                "-fx-border-color: rgba(255, 215, 0, 0.45);" +
                "-fx-border-width: 2;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.55), 30, 0.3, 0, 10);"
        );

        // -------------------------
        // 🔊 VOLUME
        // -------------------------
        Label volumeLabel = new Label("🔊 VOLUME");
        volumeLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setMinWidth(420);
        Label volumeValueLabel = new Label("50%");
        volumeValueLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            volumeValueLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
            AudioManager.setVolume(newVal.doubleValue() / 100.0);
        });

        // 🎚️ Style slider moderne
        String sliderStyle =
                "-fx-control-inner-background: rgba(255,255,255,0.12);" +
                "-fx-background-color: rgba(255,255,255,0.08);" +
                "-fx-padding: 8;" +
                "-fx-accent: #FFD700;";

        volumeSlider.setStyle(sliderStyle);

        HBox volumeBox = new HBox(18, volumeSlider, volumeValueLabel);
        volumeBox.setAlignment(Pos.CENTER_LEFT);

        Label volumeHelp = new Label("Ajuste le son sans casser l'ambiance du jeu.");
        volumeHelp.setStyle("-fx-text-fill: rgba(255,255,255,0.75); -fx-font-size: 14px;");

        VBox volumeSection = new VBox(14, volumeLabel, volumeBox, volumeHelp);
        volumeSection.setAlignment(Pos.TOP_LEFT);

        // 🔥 Style section premium
        String sectionStyle =
                "-fx-background-color: rgba(255, 255, 255, 0.05);" +
                "-fx-background-radius: 22;" +
                "-fx-border-radius: 22;" +
                "-fx-border-color: rgba(255, 215, 0, 0.22);" +
                "-fx-border-width: 1.5;" +
                "-fx-padding: 22;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 14, 0.2, 0, 3);";

        volumeSection.setStyle(sectionStyle);

        // -------------------------
        // ☀️ BRIGHTNESS
        // -------------------------
        Label brightnessLabel = new Label("☀️ BRIGHTNESS");
        brightnessLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        Slider brightnessSlider = new Slider(-0.5, 0.5, AudioManager.getBrightness());
        brightnessSlider.setMinWidth(420);
        brightnessSlider.setStyle(sliderStyle);

        int initialPercentage = (int) ((AudioManager.getBrightness() + 0.5) * 100);
        Label brightnessValueLabel = new Label(initialPercentage + "%");
        brightnessValueLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial Black'; -fx-text-fill: #FFD700;");

        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = newVal.doubleValue();
            int percentage = (int) ((value + 0.5) * 100);
            brightnessValueLabel.setText(percentage + "%");
            AudioManager.setBrightness(value);
        });

        HBox brightnessBox = new HBox(18, brightnessSlider, brightnessValueLabel);
        brightnessBox.setAlignment(Pos.CENTER_LEFT);

        Label brightnessHelp = new Label("Modifie la luminosité globale sans écraser les couleurs.");
        brightnessHelp.setStyle("-fx-text-fill: rgba(255,255,255,0.75); -fx-font-size: 14px;");

        VBox brightnessSection = new VBox(14, brightnessLabel, brightnessBox, brightnessHelp);
        brightnessSection.setStyle(sectionStyle);

        // Ajouter sections
        settingsContainer.getChildren().addAll(volumeSection, brightnessSection);

        // Bouton retour
        Button backBtn = new Button("← BACK TO MENU");
        backBtn.getStyleClass().add("menu-button");
        backBtn.setStyle(
                "-fx-font-size: 22px;" +
                "-fx-font-family: 'Arial Black';" +
                "-fx-padding: 16 52;"
        );
        backBtn.setPrefWidth(280);
        backBtn.setOnAction(e -> ViewManager.showMenuView(stage));

        VBox mainContent = new VBox(40, titleLabel, settingsContainer, backBtn);
        mainContent.setPadding(new Insets(42));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setMaxWidth(760);

        StackPane stack = new StackPane(backgroundView, mainContent);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.setPadding(new Insets(18));

        root.setCenter(stack);
    }

    private void applyStyle() {
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
    }

    public Scene getScene() {
        return scene;
    }
}

