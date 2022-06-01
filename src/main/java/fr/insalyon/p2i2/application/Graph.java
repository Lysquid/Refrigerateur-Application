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

    public static final int NB_POINTS = 60;
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

        titleTag = new JLabel(titre + " (" + unite + ")");
        titleTag.setForeground(Color.BLACK);
        titleTag.setFont(boldFont);
        add(titleTag);
        init();
    }

    public void init() {
        mesures = new ArrayList<>();
        for (int i = 0; i < NB_POINTS; i++) {
            mesures.add(Double.NaN);
        }
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

        valMax = minMax(mesures, true);
        valMin = minMax(mesures, false);
        xDispo = getWidth() - marginLeft - marginRight;
        yDispo = getHeight() - marginTop - marginBottom - marginGraphTop - marginGraphTop;

        // Antialiasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Points
        g2d.setColor(color);
        Point pointA = null;
        Point pointB = null;
        for (int i = 0; i < Graph.NB_POINTS; i++) {
            double mesure = mesures.get(i);
            if (!Double.isNaN(mesure)) {
                pointB = conversion(i, mesure);
                if (pointA != null) {
                    g2d.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
                }
            }
            pointA = pointB;
        }

        // Axes
        g2d.setColor(Application.graphColor);
        g2d.setFont(Compo.smallFont);
        pointA = new Point(marginLeft, getHeight() - marginBottom);
        pointB = new Point(marginLeft, marginTop);
        g2d.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);

        // Graduations
        double etendue = (valMax - valMin) * 100;

        double inc = 0.01;
        int roundDigits = 2;

        while (true) {
            if (etendue < 5) {
                break;
            } else if (etendue < 10) {
                inc *= 2;
                break;
            } else if (etendue < 20) {
                inc *= 5;
                break;
            }
            roundDigits -= 1;
            inc *= 10;
            etendue /= 10;
        }

        DecimalFormat df = new DecimalFormat("#.###");

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

    public static double minMax(ArrayList<Double> list, boolean max) {
        ArrayList<Double> listeWithOutNan = new ArrayList<>();
        for (double val : list) {
            if (!Double.isNaN(val)) {
                listeWithOutNan.add(val);
            }
        }
        if (listeWithOutNan.isEmpty()) {
            return Double.NaN;
        }
        if (max) {
            return Collections.max(listeWithOutNan);
        } else {
            return Collections.min(listeWithOutNan);
        }
    }

    public static double round(double val, int nbDigits) {
        double puissance = Math.pow(10, nbDigits - 1);
        return Math.round(val * puissance) / puissance;
    }

    public void maj(double mesure, boolean aJour) {
        mesures.remove(0);
        if (aJour) {
            mesures.add(mesure);
        } else {
            mesures.add(Double.NaN);
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
