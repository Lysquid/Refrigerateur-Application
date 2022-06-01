package fr.insalyon.p2i2.application;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import fr.insalyon.p2i2.connexionBD.Seuil;

public class Alerte extends Compo {

    public Alerte(Seuil seuil) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (seuil.categorieProduit == "Réfrigérateur"){
            JLabel nomAlerte = new JLabel(
                "Attention, votre " + seuil.categorieProduit + " est resté ouvert trop longtemps !!!");
            nomAlerte.setFont(boldFont);
            add(nomAlerte);
            double valeurSeuil = (seuil.valeur > seuil.seuilMax) ? seuil.seuilMax : seuil.seuilMin;
            JLabel valeurActuel = new JLabel("Durée d'ouverture : " + seuil.valeur + " " + seuil.unite);
            valeurActuel.setFont(mainFont);
            add(valeurActuel);
            JLabel valeurSeuilMin = new JLabel(
                    "Durée " + ((seuil.valeur > seuil.seuilMax) ? "maximale" : "minimale") + " : " + valeurSeuil + " "
                            + seuil.unite);
            valeurSeuilMin.setFont(mainFont);
            add(valeurSeuilMin);

        } else {
            JLabel nomAlerte = new JLabel(
                    "Seuil de " + seuil.typeDeMesure + " dépassé pour les " + seuil.categorieProduit.toLowerCase());
            nomAlerte.setFont(boldFont);
            add(nomAlerte);
            double valeurSeuil = (seuil.valeur > seuil.seuilMax) ? seuil.seuilMax : seuil.seuilMin;

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
            for (String produit : seuil.produits) {
                JLabel nomProduits = new JLabel(produit);
                nomProduits.setFont(italicFont);
                add(nomProduits);
            }
        }
    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(getParent().getWidth(), super.getMaximumSize().height);
    }

}
