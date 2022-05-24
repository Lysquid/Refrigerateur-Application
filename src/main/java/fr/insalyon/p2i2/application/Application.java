package fr.insalyon.p2i2.application;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Application extends JPanel implements ActionListener {

    private final int padding = 20;

    public Application() {

        setLayout(new GridLayout(0, 3, padding, padding));

        Column column1 = new Column();
        Grid gridGraphs = new Grid(true);
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        Block blockGraphs = new Block("Graphiques", gridGraphs);
        column1.add(blockGraphs);
        add(column1);

        Column column2 = new Column();
        column2.add(Box.createRigidArea(new Dimension(0, 50)));

        // Ajout du block monitoring
        Grid gridMonitor = new Grid(false);

        Grid colonne1 = new Grid(true);
        colonne1.add(new Information("Temperature", "°C"));
        colonne1.add(new Information("Humidité", "%"));
        colonne1.add(new Information("Ouvert", ""));
        gridMonitor.add(colonne1);

        Grid colonne2 = new Grid(true);
        colonne2.add(new Information("Gaz 1", "ppm"));
        colonne2.add(new Information("Gaz 2", "ppm"));
        colonne2.add(new Information("Gaz 3", "ppm"));
        gridMonitor.add(colonne2);

        Block blockMonitor = new Block("Monitoring", gridMonitor);
        column2.add(blockMonitor);

        // Ajout du block alerte
        Grid gridAlerts = new Grid(true);
        for (int i = 0; i < 5; i++) {
            gridAlerts.add(new Alert());
        }
        JScrollPane scrollPaneAlerts = new JScrollPane(gridAlerts);
        scrollPaneAlerts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Block blockAlerts = new Block("Alertes", scrollPaneAlerts);

        column2.add(blockAlerts);
        JButton jButton = new JButton("Effacer");
        column2.add(jButton);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(column2);

        Column column3 = new Column();
        Grid gridStock = new Grid(true);
        for (int i = 0; i < 15; i++) {
            gridStock.add(new Product());
        }

        JScrollPane scrollPaneStock = new JScrollPane(gridStock);

        scrollPaneStock.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Block blockStock = new Block("Stock", scrollPaneStock);
        column3.add(blockStock);
        column3.add(new JButton("Mode ajout"));
        add(column3);

        Timer timer = new Timer(1000, this);

    }

    @Override
    public Insets getInsets() {
        return new Insets(padding, padding, padding, padding);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

}
