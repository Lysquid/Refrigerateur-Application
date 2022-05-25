package fr.insalyon.p2i2.connexionBD;

public class Produit {

    public String nom;
    public long codebarre;
    public int quantite;

    public Produit(String nom, int quantite, long codeBarre){
        this.nom = nom;
        this.quantite = quantite;
        this.codebarre = codeBarre;
    }

}
