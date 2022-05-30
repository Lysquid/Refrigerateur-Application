package fr.insalyon.p2i2.application;

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import fr.insalyon.p2i2.connexionBD.Produit;

public class ProduitCompo extends Compo {

    public ProduitCompo(Produit produit) {
        setMySize(400, 100);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String nom;
        if (produit.nom.length() > 1) {
            nom = produit.nom.substring(0, 1).toUpperCase() + produit.nom.substring(1);
        } else {
            nom = produit.nom;
        }
        JLabel nomProduit = new JLabel(nom);
        nomProduit.setFont(boldFont);
        add(nomProduit);
        JLabel marque = new JLabel(produit.marque);
        marque.setFont(mainFont);
        add(marque);
        // TODO : Ajouter l'image (chaud)
        JLabel quantite = new JLabel("quantité : " + String.valueOf(produit.quantite));
        quantite.setFont(mainFont);
        add(quantite);

    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
