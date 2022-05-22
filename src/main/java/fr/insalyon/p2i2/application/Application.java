package fr.insalyon.p2i2.application;

import javax.swing.*; //fenêtre
import java.awt.*; //couleur
import java.awt.event.*; //événements ActionListener
import javax.swing.event.*; //événements ChangeListener
import java.io.*; //images


public class Application extends JFrame{

    public Application(){
        
        //Initialisation de la fenêtre principale
	
		setTitle("Application frigo");
        setLocation(100,200);
        setSize(1280,720);


        JLabel temp = new JLabel (" Température : ");
        temp.setBounds(0,0,150,40);

        JLabel hum = new JLabel (" Humidité : ");
        hum.setBounds(0,50,150,40);

        JLabel ouv = new JLabel (" Ouverture : ");
        ouv.setBounds(0,100,150,40);
        

        JPanel monConteneur = new JPanel();
        monConteneur.setLayout(null);
        monConteneur.add (temp);
        monConteneur.add (hum);
        monConteneur.add(ouv);
        monConteneur.setBounds(10,10,300,200);
        add(monConteneur);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

}
