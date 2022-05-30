package fr.insalyon.p2i2.application;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

public class Graph extends Compo {

    private double xA;
    private double xB;
    private double yA;
    private double yB;
    private JLabel titleTag;
    private LinkedList<Double> abscisse;
    private LinkedList<Double> ordonnee;
    private Color color;

    public static final int SIZE = 180;

    public Graph(String title, Color colour) {
        super();
        setPreferredSize(new Dimension(400, 200));

        abscisse = new LinkedList<Double>();
        ordonnee = new LinkedList<Double>();

        color = colour;

        titleTag = new JLabel(title);
        titleTag.setForeground(Color.gray);
        titleTag.setFont(boldFont);
        titleTag.setBounds(375, 10, 50, 20);
        xA = -5;
        xB = 180;
        yA = -10;
        yB = 10;
    }

    public double min(LinkedList<Double> list) {
        double min = list.get(0);
        for (double m : list) {
            if (m < min) {
                min = m;
            }
        }
        return min;
    }

    public double max(LinkedList<Double> list) {
        double max = list.get(0);
        for (double m : list) {
            if (m > max) {
                max = m;
            }
        }
        return max;
    }

    public Point ToScreen(double x, double y) {
        int HauteurVisibleFenetre = getSize().height - getInsets().top - getInsets().bottom;
        int LargeurVisibleFenetre = getSize().width - getInsets().left - getInsets().right;
        int Xecran = (int) ((x - xA) / (xB - xA) * LargeurVisibleFenetre);
        int Yecran = (int) ((y - yA) / (yB - yB) * HauteurVisibleFenetre);
        return new Point(Xecran + getInsets().left, Yecran + getInsets().top);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Point A, B;
        A = ToScreen(0, yA);
        B = ToScreen(0, yB);
        g.setColor(Color.black);
        g.drawLine(A.x, A.y, B.x, B.y);
        A = ToScreen(xA, 0);
        B = ToScreen(xB, 0);
        g.drawLine(A.x, A.y, B.x, B.y);

        g.setColor(color);
        double x = abscisse.get(0);
        double y = ordonnee.get(0);
        A = ToScreen(x, y);
        for (int i = 1; i < abscisse.size(); i++) {
            x = abscisse.get(i);
            y = ordonnee.get(i);
            B = ToScreen(x, y);
            g.drawLine(A.x, A.y, B.x, B.y);
            A = B;
        }
    }

    public void update(double nY) {
        if (abscisse.size() < SIZE) {
            abscisse.add((double) abscisse.size());
            ordonnee.add(nY);
        } else if (abscisse.size() == SIZE) {
            ordonnee.remove(0);
            ordonnee.add(nY);
        } else {
            do {
                abscisse.remove(0);
                ordonnee.remove(0);
            } while (abscisse.size() > SIZE);
            abscisse.add((double) SIZE);
            ordonnee.add(nY);
        }
        yA = min(ordonnee) - 2;
        yB = max(ordonnee) + 2;
        repaint();
    }

    public void init(LinkedList<Double> l) {
        for (int i = 0; i < l.size(); i++) {
            abscisse.add((double) i + 1);
            ordonnee.add(l.get(i));
        }
        repaint();
    }

    /*
     * L'idée c'est qu'on limite la taille des deux listes à 120 (ou 180) élements
     * ce qui
     * correspond à 1 point par seconde pendant 2 (ou 3) minutes et à chaque fois
     * qu'il y a
     * une nouvelle mesure on vire l'element d'indice 0 et on rajoute la nouvelle
     * valeur à
     * la fin puis on repaint.
     */

}
