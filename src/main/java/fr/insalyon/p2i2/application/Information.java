package fr.insalyon.p2i2.application;

import javax.swing.JLabel;

public class Information extends Box {
    
    double valeur  = 0;
    String capteur;
    JLabel info = new JLabel();
        
    public Information(String capt){
        this.capteur = capt;
        info = new JLabel(capteur + " : " + valeur);
    }

    public void maj(double newValeur){
        valeur = newValeur;
        info = new JLabel(capteur + " : "+ valeur);
    }
        
}
