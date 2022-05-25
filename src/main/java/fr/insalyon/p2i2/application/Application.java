package fr.insalyon.p2i2.application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import fr.insalyon.p2i2.connexionBD.ConnexionBD;
import fr.insalyon.p2i2.connexionBD.Produit;

public class Application extends JPanel implements ActionListener {

    private final int padding = 20;

    private Information temperature;
    private ConnexionBD connexion;

    public Application() {

        setLayout(new GridLayout(0, 3, padding, padding));

        Column column1 = new Column();
        BoxPanel gridGraphs = new BoxPanel(true);
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        gridGraphs.add(new Graph());
        Block blockGraphs = new Block("Graphiques", gridGraphs);
        column1.add(blockGraphs);
        add(column1);

        Column column2 = new Column();
        column2.add(Box.createRigidArea(new Dimension(0, 50)));

        // Ajout du block monitoring
        BoxPanel gridMonitor = new BoxPanel(false);

        BoxPanel colonne1 = new BoxPanel(true);
        temperature = new Information("Temperature", "°C");
        colonne1.add(temperature);
        colonne1.add(new Information("Humidité", "%"));
        colonne1.add(new Information("Ouvert", ""));
        gridMonitor.add(colonne1);

        BoxPanel colonne2 = new BoxPanel(true);
        colonne2.add(new Information("Gaz 1", "ppm"));
        colonne2.add(new Information("Gaz 2", "ppm"));
        colonne2.add(new Information("Gaz 3", "ppm"));
        gridMonitor.add(colonne2);

        Block blockMonitor = new Block("Monitoring", gridMonitor);
        column2.add(blockMonitor);

        // Ajout du block alerte
        BoxPanel gridAlerts = new BoxPanel(true);
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
        BoxPanel gridStock = new BoxPanel(true);
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
        timer.start();

        connexion = new ConnexionBD();

        try {
            ArrayList<Produit> listeAliments = connexion.getProduits();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }

    @Override
    public Insets getInsets() {
        return new Insets(padding, padding, padding, padding);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        temperature.maj(connexion.getTemperature());

    }

}
