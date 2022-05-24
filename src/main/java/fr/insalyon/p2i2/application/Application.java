package fr.insalyon.p2i2.application;

import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Application extends JPanel implements ActionListener {

    private final int padding = 20;

    public Application() {

        setLayout(new GridLayout(0, 3, padding, padding));

        Column column1 = new Column();
        Grid gridGraphs = new Grid(1, true);
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        Block blockGraphs = new Block("Graphiques", gridGraphs);
        column1.add(blockGraphs);
        add(column1);

        Column column2 = new Column();
        column2.add(Box.createRigidArea(new Dimension(0, 50)));
        // Ajout du block monitoring
        Grid gridMonitor = new Grid(2, false);
        Information temp = new Information("Temperature", "°C");
        temp.maj(10);
        gridMonitor.add(temp);

        gridMonitor.add(new Information("Humidité", "%"));
        gridMonitor.add(new Information("Gaz 1", "ppm"));
        gridMonitor.add(new Information("Gaz 2", "ppm"));
        gridMonitor.add(new Information("Gaz 3", "ppm"));
        gridMonitor.add(new Information("Ouvert", ""));
        Block blockMonitor = new Block("Monitoring", gridMonitor);
        column2.add(blockMonitor);
        // Ajout du block alerte
        Grid gridAlerts = new Grid(1, false);
        gridAlerts.add(new Alert());
        Block blockAlerts = new Block("Alertes", gridAlerts);
        column2.add(blockAlerts);
        add(column2);

        Column column3 = new Column();
        Grid gridStock = new Grid(1, false);

        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.weightx = 1;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.gridwidth = GridBagConstraints.REMAINDER;
        // gbc.anchor = GridBagConstraints.NORTH;
        // gbc.weighty = 1;
        // int insets = 1;
        // gbc.insets = new Insets(insets, insets, insets, insets);
        for (int i = 0; i < 15; i++) {
            gridStock.add(new Product());

            // gridStock.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // JScrollPane scrollPane = new JScrollPane(gridStock);

        // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // scrollPane.setHorizontalScrollBar(null);
        // Block blockStock = new Block("Stock", scrollPane);

        Block blockStock = new Block("Stock", gridStock);

        column3.add(blockStock);
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
