package fr.insalyon.p2i2.connexionBD;

import java.util.HashMap;

public class Mesures {

    private HashMap<Integer, Double> mesures;
    private HashMap<Integer, Boolean> aJour;
    private final int[] capteurs = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    public static final double coeffLissage = 0.1;

    public Mesures() {
        mesures = new HashMap<>();
        aJour = new HashMap<>();
        for (int capteur : capteurs) {
            mesures.put(capteur, Double.NaN);
            aJour.put(capteur, false);
        }
    }

    public void maj(ConnexionBD connexion) {
        for (int idCapteur : capteurs) {
            double mesureBrute = connexion.getNouvelleMesure(idCapteur);
            if (Double.isNaN(mesureBrute)) {
                aJour.put(idCapteur, false);
            } else {
                if (aJour.get(idCapteur)) {
                    double mesureLissee = coeffLissage * mesureBrute + (1 - coeffLissage) * mesures.get(idCapteur);
                    mesures.put(idCapteur, mesureLissee);
                } else {
                    mesures.put(idCapteur, mesureBrute);
                    aJour.put(idCapteur, true);
                }
            }
        }
    }

    public double get(int idCapteur) {
        return mesures.get(idCapteur);
    }

    public boolean getAJour(int idCapteur) {
        return aJour.get(idCapteur);
    }

}
