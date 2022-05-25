package fr.insalyon.p2i2.connexionBD;

public class Seuil {
    private String categorieProduit;
    private String typeDeMesure;
    private float seuilMin;
    private float seuilMax;

    public Seuil(String CategorieProduit, String TypeDeMesure, float SeuilMin, float SeuilMax){
        categorieProduit = CategorieProduit;
        typeDeMesure = TypeDeMesure;
        seuilMin = SeuilMin;
        seuilMax = SeuilMax;
    }

    public String toString(){
        return("Seuil de " + typeDeMesure + " sur la categorie " + categorieProduit 
        + "de seuil min " + seuilMin + " et de seuil max " + seuilMax);
    }
}
