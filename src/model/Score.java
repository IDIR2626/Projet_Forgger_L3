package model;

public class Score {

    private int scoreActuel;
    private int meilleurScore;

    public void ajouterPoints(int points) {
        scoreActuel += points;
        if (scoreActuel > meilleurScore) {
            meilleurScore = scoreActuel;
        }
    }

    public void reinitialiser() {
        scoreActuel = 0;
    }

    public void sauvegarder() {
        // À implémenter : sauvegarde fichier / BDD
    }

    public void charger() {
        // À implémenter : chargement fichier / BDD
    }

    public int getScoreActuel() { return scoreActuel; }
    public int getMeilleurScore() { return meilleurScore; }
}
