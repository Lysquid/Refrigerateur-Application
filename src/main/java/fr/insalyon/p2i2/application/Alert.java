package fr.insalyon.p2i2.application;

import javax.swing.*;
import java.awt.*;

public class Alert extends Compo {

    public Alert() {
        setMySize(400, 120);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nomAlerte = new JLabel("Seuil de température dépassé");
        nomAlerte.setFont(boldFont);
        add(nomAlerte);
        JLabel valeurActuel = new JLabel("temperature : 25°C");
        valeurActuel.setFont(mainFont);
        add(valeurActuel);
        JLabel valeurSeuil = new JLabel("température max : 24°C");
        valeurSeuil.setFont(mainFont);
        add(valeurSeuil);
        JLabel heure = new JLabel("à 10h15");
        heure.setFont(mainFont);
        add(heure);
    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
