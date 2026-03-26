package com.frogger.vue;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {

    private static MediaPlayer mediaPlayer;
    private static double brightness = 0.0; // -1.0 à 1.0
    private static ColorAdjust globalColorAdjust = new ColorAdjust();

    // Méthode interne pour charger un fichier audio
    private static MediaPlayer load(String path) {
        try {
            Media media = new Media(AudioManager.class.getResource(path).toExternalForm());
            return new MediaPlayer(media);
        } catch (Exception e) {
            System.out.println("Erreur chargement audio : " + path);
            return null;
        }
    }

    // 🎵 Musique d'accueil (Mainview)
    public static void playAccueilMusic() {
        stop();
        mediaPlayer = load("/ressources/Audio/Musique Acceuille.mp3");
        if (mediaPlayer != null) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        }
    }

    // 🎮 Musique pendant le jeu
    public static void playGameMusic() {
        stop();
        mediaPlayer = load("/ressources/Audio/musique jeu 3.mp3");
        if (mediaPlayer != null) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        }
    }

    // 🏆 Musique quand on gagne
    public static void playWinMusic() {
        stop();
        mediaPlayer = load("/ressources/Audio/musique paramètres.mp3");
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.7);
            mediaPlayer.play();
        }
    }

    // 💀 Musique quand on perd
    public static void playLoseMusic() {
        stop();
        mediaPlayer = load("/ressources/Audio/musîque fin jeu.mp3");
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.7);
            mediaPlayer.play();
        }
    }

    // ⛔ Stopper la musique actuelle
    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // 🔊 Ajuster le volume
    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(Math.max(0, Math.min(1, volume)));
        }
    }

    // ☀️ Ajuster la luminosité globale
    public static void setBrightness(double value) {
        brightness = Math.max(-1.0, Math.min(1.0, value));
        globalColorAdjust.setBrightness(brightness);
    }

    // Récupérer la luminosité globale
    public static double getBrightness() {
        return brightness;
    }

    // Récupérer l'effet ColorAdjust global
    public static ColorAdjust getGlobalColorAdjust() {
        return globalColorAdjust;
    }

    // Petit clic audio (boutons, retours)
    public static void playClick() {
        // Optionnel : charger un fichier "click.mp3" si vous en avez un dans ressources
        // pour l'instant on se contente d'arrêter toute lecture existante
        stop();
    }
}
