package fr.insalyon.p2i2.connexionBD;

import java.util.ArrayList;

public class Seuil {
    public String categorieProduit;
    public ArrayList<String> produits = new ArrayList<String>();
    public String typeDeMesure;
    public float seuilMin;
    public float seuilMax;
    public float valeur;
    public String unite;

    public Seuil(ArrayList<String> Produits, String CategorieProduit, String TypeDeMesure, float SeuilMin, float SeuilMax, float Valeur, String Unite) {
        produits = Produits;
        categorieProduit = CategorieProduit;
        typeDeMesure = TypeDeMesure;
        seuilMin = SeuilMin;
        seuilMax = SeuilMax;
        valeur = Valeur;
        unite = Unite;
    }

    public Seuil(String CategorieProduit, float SeuilMax, String TypeDeMesure, float Valeur, String Unite){
        categorieProduit = CategorieProduit;
        seuilMax = SeuilMax;
        typeDeMesure = TypeDeMesure;
        valeur = Valeur;
        unite = Unite;
    }

    public String toString(){
        return("Seuil de " + typeDeMesure + " sur la categorie " + categorieProduit 
        + ": Seuil Min  = " + seuilMin + ". Seuil Max  = " + seuilMax + ". Valeur Actuelle = "+ valeur + " " + unite);
    }
}
