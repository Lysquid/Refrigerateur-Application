package fr.insalyon.p2i2.connexionBD;

public class Seuil {
    public String categorieProduit;
    public String typeDeMesure;
    public float seuilMin;
    public float seuilMax;
    public float valeur;
    public String unite;

    public Seuil(String CategorieProduit, String TypeDeMesure, float SeuilMin, float SeuilMax, float Valeur,
            String Unite) {
        categorieProduit = CategorieProduit;
        typeDeMesure = TypeDeMesure;
        seuilMin = SeuilMin;
        seuilMax = SeuilMax;
        valeur = Valeur;
        unite = Unite;
    }

    public String toString() {
        return ("Seuil de " + typeDeMesure + " sur la categorie " + categorieProduit
                + " de seuil min " + seuilMin + " et de seuil max " + seuilMax + " valeur : " + valeur + " unite : "
                + unite);
    }
}
