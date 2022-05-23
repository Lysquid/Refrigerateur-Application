package fr.insalyon.p2i2.application;

import javax.swing.JLabel;

public class Information extends Box {

    String capteur;
    JLabel info = new JLabel();

    public Information(String capt) {
        this.capteur = capt;
        info = new JLabel();
        this.add(info);
    }

    public void maj(double newValeur) {
        info.setText(capteur + " : " + newValeur);
    }

}
