package fr.insalyon.p2i2.application;

import javax.swing.*;

import fr.insalyon.p2i2.connexionBD.Seuil;

import java.awt.*;

public class Alert extends Compo {

    public Alert(Seuil seuil) {
        setMySize(400, 150);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String listeProduit = new String(seuil.produits.get(0));
        if (seuil.produits.size() > 1) {
            for (int i = 1; i < seuil.produits.size() - 1; i++) {
                listeProduit += ", " + seuil.produits.get(i);
            }
            listeProduit += " et " + seuil.produits.get(seuil.produits.size() - 1);
        }

        JLabel nomAlerte = new JLabel("Seuil de " + seuil.typeDeMesure + " dépassé pour ");
        nomAlerte.setFont(boldFont);
        add(nomAlerte);
        double valeurSeuil = (seuil.valeur > seuil.seuilMax) ? seuil.seuilMax : seuil.seuilMin;
        JLabel nomCategorie = new JLabel("les " + seuil.categorieProduit.toLowerCase());
        nomCategorie.setFont(boldFont);
        add(nomCategorie);
        JLabel valeurActuel = new JLabel("Valeur actuelle : " + seuil.valeur + " " + seuil.unite);
        valeurActuel.setFont(mainFont);
        add(valeurActuel);
        JLabel valeurSeuilMin = new JLabel(
                "Seuil " + ((seuil.valeur > seuil.seuilMax) ? "maximal" : "minimal") + " : " + valeurSeuil + " "
                        + seuil.unite);
        valeurSeuilMin.setFont(mainFont);
        add(valeurSeuilMin);
        JLabel produitsConcernes = new JLabel("Produits concernés :");
        produitsConcernes.setFont(mainFont);
        add(produitsConcernes);
        JLabel nomProduits = new JLabel(listeProduit);
        nomProduits.setFont(smallFont);
        add(nomProduits);
    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
