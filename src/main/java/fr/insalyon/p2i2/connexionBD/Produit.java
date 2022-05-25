package fr.insalyon.p2i2.connexionBD;

public class Produit {

    private String nom;
    private long codebarre;
    private int quantite;

    public Produit(String nom, int quantite, long codeBarre){
        this.nom = nom;
        this.quantite = quantite;
        this.codebarre = codeBarre;
    }

    public String toString(){
        return(nom + " de quantité " + quantite + " et de code-barre " + codebarre);
    }

}
