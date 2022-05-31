package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Collections;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Graph extends Compo {

    private JLabel titleTag;
    private ArrayList<Double> mesures;
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
        mesures = new ArrayList<Double>();

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

        valMax = Collections.max(mesures) * 1.2;
        valMin = Collections.min(mesures) * 0.9;

        // Axes
        Point A, B;
        A = new Point(marginLeft, getHeight() - marginBottom);
        B = new Point(marginLeft, marginTop);
        g2d.setColor(Color.black);
        g2d.drawLine(A.x, A.y, B.x, B.y);

        // Points
        g2d.setColor(color);
        double mesure = mesures.get(0);
        A = conversion(0, mesure);
        for (int i = 1; i < Graph.POINTS; i++) {
            mesure = mesures.get(i);
            if (mesure == -1) {
                continue;
            }
            B = conversion(i, mesure);
            g2d.drawLine(A.x, A.y, B.x, B.y);
            A = B;
        }
    }

    public void update(double mesure) {
        mesures.remove(0);
        if (mesure == -1d) {
            mesures.remove(0);
        } else {
            mesures.add(mesure);
        }
        repaint();
    }

    public void init(ArrayList<Double> mesures) {
        this.mesures = mesures;
        while (mesures.size() < Graph.POINTS) {
            mesures.add(0, -1d);
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
