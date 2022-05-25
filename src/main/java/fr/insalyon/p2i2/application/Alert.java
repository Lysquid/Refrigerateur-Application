package fr.insalyon.p2i2.application;

import javax.swing.*;

import fr.insalyon.p2i2.connexionBD.Seuil;

import java.awt.*;

public class Alert extends Compo {

    public Alert(Seuil seuil) {
        setMySize(400, 120);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nomAlerte = new JLabel("Seuil de " + seuil.typeDeMesure + " dépassé");
        nomAlerte.setFont(boldFont);
        add(nomAlerte);
        JLabel valeurActuel = new JLabel("valeur actuel : ");
        valeurActuel.setFont(mainFont);
        add(valeurActuel);
        JLabel valeurSeuil = new JLabel("seuil : " + seuil.seuilMax);
        valeurSeuil.setFont(mainFont);
        add(valeurSeuil);
    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
