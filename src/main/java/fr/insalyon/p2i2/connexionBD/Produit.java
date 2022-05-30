package fr.insalyon.p2i2.connexionBD;

public class Produit {

    public String nom;
    public long codebarre;
    public int quantite;
    public String marque;
    public String url;

    public Produit(String nom, int quantite, long codeBarre) {
        this.nom = nom;
        this.quantite = quantite;
        this.codebarre = codeBarre;
    }

    public Produit(String nom, int quantite, long codeBarre, String marque, String url) {
        this.nom = nom;
        this.quantite = quantite;
        this.codebarre = codeBarre;
        this.marque = marque;
        this.url = url;
    }

    public String toString() {
        return (nom + " de quantité " + quantite + " et de code-barre " + codebarre);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Produit) {
            Produit produit2 = (Produit) obj;
            return codebarre == produit2.codebarre;
        }
        return super.equals(obj);
    }

}
