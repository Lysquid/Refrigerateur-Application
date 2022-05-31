package fr.insalyon.p2i2.application;

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class Information extends Compo {

    JLabel labelCapteur = new JLabel();
    JLabel labelValeur = new JLabel();
    String unite;

    public Information(String capteur, String unite) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        labelCapteur = new JLabel(capteur);
        labelCapteur.setFont(boldFont);
        labelValeur = new JLabel();
        labelValeur.setFont(biggerFont);
        this.unite = unite;
        maj(0);
        this.add(labelCapteur);
        this.add(labelValeur);
    }

    public void maj(double valeur) {
        labelValeur.setText(String.valueOf(Math.round(valeur * 10) / 10.0) + " " + unite);
    }

    public void maj(int valeur) {
        labelValeur.setText(valeur + " " + unite);
    }

    public void maj(String valeur) {
        labelValeur.setText(valeur + " " + unite);
    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
