package fr.insalyon.p2i2.application;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class Information extends Compo {

    JLabel labelCapteur = new JLabel();
    JLabel labelValeur = new JLabel();
    String unite;

    public Information(String capteur, String unite) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        labelCapteur = new JLabel(capteur);
        labelCapteur.setFont(font);
        labelValeur = new JLabel();
        labelValeur.setFont(font);
        this.unite = unite;
        maj(0);
        this.add(labelCapteur);
        this.add(labelValeur);
    }

    public void maj(double valeur) {
        labelValeur.setText(String.valueOf(valeur) + " " + unite);
    }

}
