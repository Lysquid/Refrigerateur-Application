package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Graph extends Compo {

    private JLabel titleTag;
    private ArrayList<Double> mesures;
    private Color color;
    private String titre;
    double valMax;
    double valMin;
    int xDispo;
    int yDispo;

    public static final int NB_POINTS = 180;
    public static final int NB_GRADUATIONS = 5;

    private static final int marginRight = 20;
    private static final int marginLeft = 70;
    private static final int marginTop = 50;
    private static final int marginBottom = 30;
    private static final int marginGraphTop = 20;
    private static final int marginGraphBottom = 20;

    public Graph(String titre, String unite, Color color) {
        this.titre = titre;
        this.color = color;
        mesures = new ArrayList<Double>();

        titleTag = new JLabel(titre + " (" + unite + ")");
        titleTag.setForeground(Color.BLACK);
        titleTag.setFont(boldFont);
        add(titleTag);

    }

    @Override
    public String toString() {
        return titre;
    }

    public Point conversion(int abscisse, double val) {
        int xEcran = marginLeft + (int) (xDispo * abscisse / Graph.NB_POINTS);
        int yEcran = getHeight() - marginBottom - marginGraphBottom
                - (int) (yDispo * (val - valMin) / (valMax - valMin));
        return new Point(xEcran, yEcran);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        valMax = Collections.max(mesures);
        valMin = Collections.min(mesures);
        xDispo = getWidth() - marginLeft - marginRight;
        yDispo = getHeight() - marginTop - marginBottom - marginGraphTop - marginGraphTop;

        // Antialiasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Points
        g2d.setColor(color);
        Point pointA, pointB;
        double mesure = mesures.get(0);
        pointA = conversion(0, mesure);
        for (int i = 1; i < Graph.NB_POINTS; i++) {
            mesure = mesures.get(i);
            if (mesure == Double.NaN) {
                continue;
            }
            pointB = conversion(i, mesure);
            g2d.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
            pointA = pointB;
        }

        // Axes
        g2d.setColor(Application.graphColor);
        g2d.setFont(Compo.smallFont);
        pointA = new Point(marginLeft, getHeight() - marginBottom);
        pointB = new Point(marginLeft, marginTop);
        g2d.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);

        // Graduations
        double etendue = valMax - valMin;
        double inc;
        int roundDigits;
        if (etendue < 0.05) {
            inc = 0.01;
            roundDigits = 2;
        } else if (etendue < 0.1) {
            inc = 0.02;
            roundDigits = 2;
        } else if (etendue < 0.5) {
            inc = 0.1;
            roundDigits = 1;
        } else if (etendue < 1) {
            inc = 0.2;
            roundDigits = 1;
        } else if (etendue < 5) {
            inc = 1;
            roundDigits = 0;
        } else if (etendue < 10) {
            inc = 2;
            roundDigits = 0;
        } else if (etendue < 50) {
            inc = 10;
            roundDigits = -1;
        } else if (etendue < 100) {
            inc = 20;
            roundDigits = -1;
        } else if (etendue < 500) {
            inc = 200;
            roundDigits = -2;
        } else if (etendue < 1000) {
            inc = 100;
            roundDigits = -2;
        } else if (etendue < 5000) {
            inc = 1000;
            roundDigits = -3;
        } else if (etendue < 10000) {
            inc = 2000;
            roundDigits = -3;
        } else if (etendue < 50000) {
            inc = 10000;
            roundDigits = -4;
        } else {
            inc = 20000;
            roundDigits = -4;
        }

        String format;
        if (roundDigits == 2) {
            format = "#.##";
        } else if (roundDigits == 1) {
            format = "#.#";
        } else {
            format = "#";
        }
        DecimalFormat df = new DecimalFormat(format);

        double val = round(valMin, roundDigits) - inc;
        while (val <= valMax + inc) {
            pointA = conversion(0, val);
            if (pointA.y > marginTop && pointA.y < getHeight() - marginBottom) {
                pointB = new Point(pointA.x - 5, pointA.y);
                g.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
                pointA.x -= 35 + (int) (5 * Math.abs(roundDigits));
                pointA.y += 6;
                g2d.drawString(df.format(val), pointA.x, pointA.y);
            }
            val += inc;
        }

    }

    public static double round(double val, int nbDigits) {
        double puissance = Math.pow(10, nbDigits);
        return Math.round(val * puissance) / puissance;
    }

    public void update(double mesure) {
        mesures.remove(0);
        mesures.add(mesure);
        repaint();
    }

    public void init(ArrayList<Double> mesures) {
        this.mesures = mesures;
        while (mesures.size() < NB_POINTS) {
            mesures.add(0, Double.NaN);
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
