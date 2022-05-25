package fr.insalyon.p2i2.application;

import javax.swing.*;
import java.awt.*;

public class Product extends Compo {

    public Product() {
        setMySize(400, 100);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nomProduit = new JLabel("Nom produit");
        nomProduit.setFont(boldFont);
        add(nomProduit);
        JLabel marque = new JLabel("Marque");
        marque.setFont(mainFont);
        add(marque);
        JLabel quantite = new JLabel("quantite : 1");
        quantite.setFont(mainFont);
        add(quantite);

    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
