package fr.insalyon.p2i2.connexionBD;

public class Seuil {
    public String categorieProduit;
    public String produit;
    public String typeDeMesure;
    public float seuilMin;
    public float seuilMax;
    public float valeur;
    public String unite;

    public Seuil(String Produit, String CategorieProduit, String TypeDeMesure, float SeuilMin, float SeuilMax, float Valeur,
            String Unite) {
        produit = Produit;
        categorieProduit = CategorieProduit;
        typeDeMesure = TypeDeMesure;
        seuilMin = SeuilMin;
        seuilMax = SeuilMax;
        valeur = Valeur;
        unite = Unite;
    }

    public String toString(){
        return("Seuil de " + typeDeMesure + " sur la categorie " + categorieProduit 
        + ": Seuil Min  = " + seuilMin + ". Seuil Max  = " + seuilMax + ". Valeur Actuelle = "+ valeur + unite);
    }
}
