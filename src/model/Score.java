package model;

public class Score {

    private int scoreActuel;
    private int meilleurScore;

    public int getScoreActuel() {
        return scoreActuel;
    }

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public void ajouterPoints(int points) {
        if (points <= 0) {
            return;
        }

        scoreActuel += points;

        if (scoreActuel > meilleurScore) {
            meilleurScore = scoreActuel;
        }
    }

    public void reinitialiser() {
        scoreActuel = 0;
    }
}