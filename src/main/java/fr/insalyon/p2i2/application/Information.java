package fr.insalyon.p2i2.application;

import javax.swing.JLabel;

public class Information extends Box {
    
    double info  = 0; 
    JLabel rendu;
        
    public Information(String capteur){

        double info = 0; //Changer 0 et mettre la valeur du capteur actuek à récupérer avec la base de donnée
            
        rendu = new JLabel (capteur + " : " + info);

    }
        
}
