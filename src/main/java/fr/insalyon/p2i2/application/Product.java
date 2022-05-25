package fr.insalyon.p2i2.application;

import javax.swing.*;

import fr.insalyon.p2i2.connexionBD.Produit;

import java.awt.*;

public class Product extends Compo {

    public Product(Produit produit) {
        setMySize(400, 100);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nomProduit = new JLabel(produit.nom);
        nomProduit.setFont(boldFont);
        add(nomProduit);
        JLabel marque = new JLabel("Marque");
        marque.setFont(mainFont);
        add(marque);
        JLabel quantite = new JLabel("quantité : " + String.valueOf(produit.quantite));
        quantite.setFont(mainFont);
        add(quantite);

    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
