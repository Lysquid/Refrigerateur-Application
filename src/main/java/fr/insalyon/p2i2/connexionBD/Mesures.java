package fr.insalyon.p2i2.connexionBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Mesures {

    private HashMap<Integer, Double> mesures;
    private HashMap<Integer, Integer> idMesuresPrec;
    private HashMap<Integer, Integer> aJour;
    private static final int NB_PAS_A_JOUR = 3;
    private static final int[] CAPTEURS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    public static final double coeffLissage = 0.1;

    public Mesures() {
        mesures = new HashMap<>();
        idMesuresPrec = new HashMap<>();
        aJour = new HashMap<>();
        for (int capteur : CAPTEURS) {
            mesures.put(capteur, Double.NaN);
            aJour.put(capteur, NB_PAS_A_JOUR);
        }
    }

    public void maj(ConnexionBD connexion) {
        ResultSet result = connexion.getNouvelleMesures();
        if (result == null) {
            return;
        }
        try {
            while (result.next()) {
                int idCapteur = result.getInt("idCapteur");
                int idMesure = result.getInt("idMesure");
                double mesure = result.getDouble("valeur");

                if (idMesure == idMesuresPrec.getOrDefault(idCapteur, -1)) { // la mesure est la meme que la précédente
                    aJour.put(idCapteur, aJour.get(idCapteur) + 1);
                } else { // la mesure est une nouvelle mesure
                    if (getAJour(idCapteur)) { // si les mesures d'avant était à jour, on lisse
                        mesure = coeffLissage * mesure + (1 - coeffLissage) * mesures.get(idCapteur);
                    } else { // sinon on dit les mesures sont maintenant à jour, mais lisse pas cette fois
                        aJour.put(idCapteur, 0);
                    }
                    mesures.put(idCapteur, mesure);
                }
                idMesuresPrec.put(idCapteur, idMesure);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double get(int idCapteur) {
        return mesures.get(idCapteur);
    }

    public boolean getAJour(int idCapteur) {
        return aJour.getOrDefault(idCapteur, NB_PAS_A_JOUR) < NB_PAS_A_JOUR;
    }

}
