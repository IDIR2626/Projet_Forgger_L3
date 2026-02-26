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

    public void ajouterPoints(int points) 
    {
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

     // -----------------------------
    //      SAUVEGARDE / CHARGEMENT
    // -----------------------------

    public void sauvegarder() 
    {
        try (FileWriter writer = new FileWriter(FICHIER_SCORE))
        {
        //writer est un objet qui permet d'écrire dans un fichier.
        //  En utilisant try-with-resources, on s'assure que le writer est correctement fermé après l'écriture, même en cas d'exception.
            writer.write(String.valueOf(meilleurScore));
            // On convertit le meilleur score en chaîne de caractères avant de l'écrire dans le fichier.
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du score : " + e.getMessage());
        }
    }

    public void charger() 
    {
        File fichier = new File(FICHIER_SCORE);

        if (!fichier.exists()) {
            return; // Aucun score sauvegardé
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) 
        // reader est un objet qui permet de lire le contenu d'un fichier.
        {
            String ligne = reader.readLine();
            if (ligne != null) {
                meilleurScore = Integer.parseInt(ligne);
                // On lit la première ligne du fichier, qui contient le meilleur score, 
                // et on la convertit en entier.
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erreur lors du chargement du score : " + e.getMessage());
        }
    }
}