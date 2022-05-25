package fr.insalyon.p2i2.connexionBD;

public class Produit {

    public String nom;
    public long codebarre;
    public int quantite;

    public Produit(String nom, int quantite, long codeBarre) {
        this.nom = nom;
        this.quantite = quantite;
        this.codebarre = codeBarre;
    }

    public String toString() {
        return (nom + " de quantité " + quantite + " et de code-barre " + codebarre);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Produit) {
            
        }
        return super.equals(obj);
    }

}
