package fr.insalyon.p2i2.application;

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class Information extends Compo {

    private JLabel labelCapteur = new JLabel();
    private JLabel labelValeur = new JLabel();
    private String unite;
    public String capteur;

    public Information(String capteur, String unite) {
        this.capteur = capteur;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        labelCapteur = new JLabel(capteur);
        labelCapteur.setFont(boldFont);
        labelValeur = new JLabel();
        labelValeur.setFont(biggerFont);
        this.unite = unite;
        this.add(labelCapteur);
        this.add(labelValeur);
    }

    public void maj(double valeur) {
        if (valeur != Double.NaN) {
            labelValeur.setText(String.valueOf(Math.round(valeur * 10) / 10.0) + " " + unite);
        }

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
