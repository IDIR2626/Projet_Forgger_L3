package com.frogger.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score {

    private static final String FICHIER_SCORE = "meilleur_score.txt";

    private int scoreActuel;
    private int meilleurScore;

    public Score() {
        charger();
    }

    public int getScoreActuel() {
        return scoreActuel;
    }

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public void ajouterPoints(int points) {
        if (points <= 0) return;

        scoreActuel += points;

        if (scoreActuel > meilleurScore) {
            meilleurScore = scoreActuel;
            sauvegarder();
        }
    }

    public void reinitialiser() {
        scoreActuel = 0;
    }

    // -----------------------------
    //      SAUVEGARDE / CHARGEMENT
    // -----------------------------

    public void sauvegarder() {
        try (FileWriter writer = new FileWriter(FICHIER_SCORE)) {
            writer.write(String.valueOf(meilleurScore));
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du score : " + e.getMessage());
        }
    }

    public final void charger() {
        File fichier = new File(FICHIER_SCORE);

        if (!fichier.exists()) {
            meilleurScore = 0;
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne = reader.readLine();
            if (ligne != null) {
                meilleurScore = Integer.parseInt(ligne);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erreur lors du chargement du score : " + e.getMessage());
        }
    }
}
