package fr.insalyon.p2i2.application;

import javax.swing.*; //fenêtre
import java.awt.*; //couleur
import java.awt.event.*; //événements ActionListener
import javax.swing.event.*; //événements ChangeListener
import java.io.*; //images


public class Application extends JFrame{

    public Application(){
        
        //Initialisation de la fenêtre principale
	
		this.setTitle("Application frigo");
		this.setSize(1280,720);
		this.setLocation(250,0);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        JLabel CourantY = new JLabel();
        CourantY.setLayout(null);
        CourantY.setBounds(100,100,100,100);
        CourantY.setForeground(new Color(255,227,208));
        CourantY.setFont(new Font("Juice ITC",Font.BOLD,35));
        

        JPanel panneauGlobal = new JPanel();
		panneauGlobal.setBackground(new Color(255,0,0));
		panneauGlobal.setLayout(null);
        panneauGlobal.add(CourantY);

    }

}
