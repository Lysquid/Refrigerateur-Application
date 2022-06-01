package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JLabel;

public class Graph extends Compo {

    private JLabel titleTag;
    private LinkedList<Double> abscisse;
    private LinkedList<Double> ordonnee;
    private Color color;
    private String title;
    double valMax;
    double valMin;
    int abscisseMax;

    public static final int POINTS = 180;

    private static final int marginRight = 20;
    private static final int marginLeft = 40;
    private static final int marginTop = 40;
    private static final int marginBottom = 20;

    public Graph(String title, Color color) {
        this.title = title;
        this.color = color;
        abscisse = new LinkedList<Double>();
        ordonnee = new LinkedList<Double>();

        titleTag = new JLabel(title);
        titleTag.setForeground(Color.BLACK);
        titleTag.setFont(boldFont);
        add(titleTag);

    }

    @Override
    public String toString() {
        return title;
    }

    public Point conversion(double x, double y) {
        int xEcran = marginLeft + (int) ((getWidth() - marginLeft - marginRight) * x / valMax);
        int yEcran = getHeight() - marginBottom - (int) (getHeight() * (y - valMin) / (valMax - valMin));
        return new Point(xEcran, yEcran);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        abscisseMax = Collections.max(abscisse).intValue();
        valMax = Collections.max(ordonnee) * 1.2;
        valMin = Collections.min(ordonnee) * 0.9;

        // Axes
        Point A, B;
        A = new Point(marginLeft, getHeight() - marginBottom);
        B = new Point(marginLeft, marginTop);
        g2d.setColor(Color.black);
        g2d.drawLine(A.x, A.y, B.x, B.y);

        // Points
        g2d.setColor(color);
        double x = abscisse.get(0);
        double y = ordonnee.get(0);
        A = conversion(x, y);
        for (int i = 1; i < abscisse.size(); i++) {
            x = abscisse.get(i);
            y = ordonnee.get(i);
            B = conversion(x, y);
            g2d.drawLine(A.x, A.y, B.x, B.y);
            A = B;
        }
    }

    public void update(double nY) {
        if (abscisse.size() >= POINTS) {
            ordonnee.remove(0);
        } else {
            abscisse.add(abscisse.size() + 1d);
        }
        ordonnee.add(nY);
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
