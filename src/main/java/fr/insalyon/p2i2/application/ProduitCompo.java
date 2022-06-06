package fr.insalyon.p2i2.application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.insalyon.p2i2.connexionBD.Produit;

public class ProduitCompo extends Compo {

    private static final int imageTimeout = 300;
    private JLabel quantite;
    public Produit produit;
    private int IMAGE_HEIGHT = 120;

    public ProduitCompo(Produit produit) {
        JPanel textPanel = new JPanel() {
            @Override
            public Insets getInsets() {
                int insets = Compo.inset;
                return new Insets(insets, insets, insets, insets);
            }
        };
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        try {
            URL url = new URL(produit.url);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(imageTimeout);
            con.setReadTimeout(imageTimeout);
            InputStream in = con.getInputStream();
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(in));
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(image.getWidth(null) * IMAGE_HEIGHT / image.getHeight(null),
                    IMAGE_HEIGHT, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            in.close();
            imageLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            imageLabel.setAlignmentY(Component.TOP_ALIGNMENT);
            add(imageLabel);
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
        JLabel masse = new JLabel(Integer.toString(produit.masse) + " g");
        masse.setFont(mainFont);
        textPanel.add(masse);
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

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Application.scrollCompoSize, super.getMaximumSize().height);
    }

}
