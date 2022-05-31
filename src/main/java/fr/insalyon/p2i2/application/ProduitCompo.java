package fr.insalyon.p2i2.application;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.*;

import fr.insalyon.p2i2.connexionBD.Produit;

public class ProduitCompo extends Compo {

    public Produit produit;
    private JLabel quantite;

    public ProduitCompo(Produit produit) {
        super(true);

        JPanel textPanel = new JPanel() {
            @Override
            public Insets getInsets() {
                int insets = 10;
                return new Insets(insets, insets, insets, insets);
            }
        };
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        try {
            URL url = new URL(produit.url);
            JLabel image = new JLabel(new ImageIcon(ImageIO.read(url)));
            image.setLocation(100, 0);
            image.setAlignmentX(Component.RIGHT_ALIGNMENT);
            image.setAlignmentY(Component.TOP_ALIGNMENT);
            add(image);
        } catch (IOException e) {
        }

        this.produit = produit;
        String nom;
        if (produit.nom.length() > 1) {
            nom = produit.nom.substring(0, 1).toUpperCase() + produit.nom.substring(1);
        } else {
            nom = produit.nom;
        }
        JLabel nomProduit = new JLabel(nom);
        nomProduit.setFont(boldFont);
        textPanel.add(nomProduit);
        JLabel marque = new JLabel(produit.marque);
        marque.setFont(mainFont);
        textPanel.add(marque);
        quantite = new JLabel();
        majQuantite(produit);
        quantite.setFont(mainFont);
        textPanel.add(quantite);
        textPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        add(textPanel);

    }

    public void majQuantite(Produit nouveauProduit) {
        produit = nouveauProduit;
        quantite.setText("quantité : " + String.valueOf(produit.quantite));
    }

}
