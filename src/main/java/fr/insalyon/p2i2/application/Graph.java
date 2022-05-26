package fr.insalyon.p2i2.application;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Graph extends Compo {

    private int xA;
    private int xB;
    private int yA;
    private int yB;
    private JLabel titleTag;
    private ArrayList<Double> abscisse;
    private ArrayList<Double> ordonnee;


    public Graph(String title) {
        setPreferredSize(new Dimension(400, 200));

        titleTag = new JLabel(title);
        titleTag.setForeground(Color.gray);
        titleTag.setFont(boldFont);
        titleTag.setBounds(375,10,50,20);
    }

    public double min(ArrayList<Double> list){
        double min = list.get(0);
        for(double m : list){
            if(m < min){
                min = m;
            }
        }
        return min;
    }

    public double max(ArrayList<Double> list){
        double max = list.get(0);
        for(double m : list){
            if(m > max){
                max = m;
            }
        }
        return max;
    }

    public void paint(Graphics g){

    }

}
