package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.*;

public class Application extends JPanel {

    private final int padding = 20;

    public Application() {

        setLayout(new GridLayout(0, 3, padding, padding));

        Column column1 = new Column();
        Grid gridGraphs = new Grid(3, 3);
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        Block blockGraphs = new Block("Graphiques", gridGraphs);
        column1.add(blockGraphs);
        add(column1);

        Column column2 = new Column();
        column2.add(Box.createRigidArea(new Dimension(0, 50)));
        //Ajout du block monitoring
        Grid gridMonitor = new Grid(3,3);
        Information temp = new Information("Temperature"); 
        temp.maj(10);
        gridMonitor.add(temp);

        gridMonitor.add(new Information("Humidité"));
        gridMonitor.add(new Information("Gaz 1"));
        gridMonitor.add(new Information("Gaz 2"));
        gridMonitor.add(new Information("Gaz 3"));
        gridMonitor.add(new Information("Je sais pas quoi mettre"));
        Block blockMonitor = new Block("Monitoring", gridMonitor);
        column2.add(blockMonitor);
        //Ajout du block alerte
        Grid gridAlerts = new Grid(4, 0);
        gridAlerts.add(new Alert());
        Block blockAlerts = new Block("Alertes", gridAlerts);
        column2.add(blockAlerts);
        add(column2);

        Column column3 = new Column();
        Grid gridStock = new Grid(6, 2);
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
