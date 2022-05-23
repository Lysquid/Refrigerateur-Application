package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.*;

public class Application extends JPanel {

    private final int padding = 30;

    public Application() {

        setLayout(new GridLayout(0, 3, padding, padding));

        Column column1 = new Column();
        Grid gridGraphs = new Grid(3, 0);
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        Block blockGraphs = new Block("Graphiques", gridGraphs);
        column1.add(blockGraphs);
        add(column1);

        Column column2 = new Column();
        column2.add(Box.createRigidArea(new Dimension(0, 50)));
        // TODO : Ajouter le block monitoring
        Grid gridAlerts = new Grid(4, 0);
        gridAlerts.add(new Alert());
        Block blockAlerts = new Block("Alerts", gridAlerts);
        column2.add(blockAlerts);
        add(column2);

        Column column3 = new Column();
        Grid gridStock = new Grid(6, 3);
        for (int i = 0; i < 8; i++) {
            gridStock.add(new Product());
        }
        Block blockStock = new Block("Stock", gridStock);
        column3.add(blockStock);
        add(column3);

    }

    @Override
    public Insets getInsets() {
        return new Insets(padding, padding, padding, padding);
    }

}
