package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;

public class Graph extends Compo {

    private JLabel titleTag;
    private ArrayList<Double> mesures;
    private Color color;
    private String titre;

    private double valMax;
    private double valMin;
    private int xDispo;
    private int yDispo;

    private static final int NB_POINTS = 60;
    private static final int MARGIN_RIGHT = 20;
    private static final int MARGIN_LEFT = 70;
    private static final int MARGIN_TOP = 50;
    private static final int MARGIN_BOTTOM = 30;
    private static final int MARGIN_GRAPH_TOP = 20;
    private static final int MARGIN_GRAPH_BOTTOM = 20;

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

    private Point conversion(int abscisse, double val) {
        int xEcran = MARGIN_LEFT + (int) (xDispo * abscisse / Graph.NB_POINTS);
        int yEcran = getHeight() - MARGIN_BOTTOM - MARGIN_GRAPH_BOTTOM
                - (int) (yDispo * (val - valMin) / (valMax - valMin));
        return new Point(xEcran, yEcran);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        valMax = minMax(mesures, true);
        valMin = minMax(mesures, false);
        xDispo = getWidth() - MARGIN_LEFT - MARGIN_RIGHT;
        yDispo = getHeight() - MARGIN_TOP - MARGIN_BOTTOM - MARGIN_GRAPH_TOP - MARGIN_GRAPH_TOP;

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
        pointA = new Point(MARGIN_LEFT, getHeight() - MARGIN_BOTTOM);
        pointB = new Point(MARGIN_LEFT, MARGIN_TOP);
        g2d.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);

        // Graduations
        double etendue = (valMax - valMin) * 100;

        double inc = 0.01;
        int roundDigits = 2;

        while (true) {
            if (etendue < 5) {
                break;
            }
            roundDigits -= 1;
            if (etendue < 10) {
                inc *= 2;
                break;
            } else if (etendue < 20) {
                inc *= 5;
                break;
            }
            inc *= 10;
            etendue /= 10;
        }

        DecimalFormat df = new DecimalFormat("#.###");

        double val = round(valMin, roundDigits) - inc * 5;
        while (val <= valMax + inc) {
            pointA = conversion(0, val);
            if (pointA.y > MARGIN_TOP && pointA.y < getHeight() - MARGIN_BOTTOM) {
                pointB = new Point(pointA.x - 5, pointA.y);
                g.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
                String stringVal = df.format(val);
                pointA.x -= 15 + (int) (7 * stringVal.length());
                pointA.y += 6;
                g2d.drawString(stringVal, pointA.x, pointA.y);
            }
            val += inc;
        }

    }

    private static double minMax(ArrayList<Double> list, boolean max) {
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

    private static double round(double val, int nbDigits) {
        double puissance = Math.pow(10, nbDigits);
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

}
