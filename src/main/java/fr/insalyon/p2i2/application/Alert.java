package fr.insalyon.p2i2.application;

import javax.swing.*;

import fr.insalyon.p2i2.connexionBD.Seuil;

import java.awt.*;

public class Alert extends Compo {

    public Alert(Seuil seuil) {
        setMySize(400, 160);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        String listeProduit = new String(seuil.produits.get(0));
        if (seuil.produits.size() > 1) {
            for (int i = 1; i < seuil.produits.size() - 1; i++) {
                listeProduit += ", " + seuil.produits.get(i);
            }
            listeProduit += " et " + seuil.produits.get(seuil.produits.size()-1);
        }

        JLabel nomCategorie = new JLabel("Catégorie : " + seuil.categorieProduit);
        nomCategorie.setFont(boldFont);
        add(nomCategorie);
        JLabel nomProduit = new JLabel("Produit(s) : " + listeProduit);
        nomProduit.setFont(boldFont);
        add(nomProduit);
        JLabel nomAlerte = new JLabel("Seuil de " + seuil.typeDeMesure + " dépassé ");
        nomAlerte.setFont(boldFont);
        add(nomAlerte);
        JLabel valeurActuel = new JLabel("valeur actuel : " + seuil.valeur);
        valeurActuel.setFont(mainFont);
        add(valeurActuel);
        JLabel valeurSeuilMin = new JLabel("Seuil Min : " + seuil.seuilMin);
        valeurSeuilMin.setFont(mainFont);
        add(valeurSeuilMin);
        JLabel valeurSeuilMax = new JLabel("Seuil Max : " + seuil.seuilMax);
        valeurSeuilMax.setFont(mainFont);
        add(valeurSeuilMax);
    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
