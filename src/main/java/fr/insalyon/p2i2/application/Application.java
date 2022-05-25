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
import fr.insalyon.p2i2.connexionBD.Seuil;

public class Application extends JPanel implements ActionListener {

    private final int padding = 20;

    public static String fontName = null;

    private Information temperature;
    private Information humidite;
    private Information ouvert;
    private Information gaz1;
    private Information gaz2;
    private Information gaz3;

    private ConnexionBD connexion;
    private Timer timerInfo;
    private Timer timerRapide;

    private BoxPanel gridStock;
    private BoxPanel gridAlerts;

    private ArrayList<Produit> nouveauxProduits;

    public Application() {

        connexion = new ConnexionBD();

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
        humidite = new Information("Humidité", "%");
        colonne1.add(humidite);
        ouvert = new Information("Ouvert", "");
        colonne1.add(ouvert);
        gridMonitor.add(colonne1);

        BoxPanel colonne2 = new BoxPanel(true);
        gaz1 = new Information("NH3", "ppm");
        colonne2.add(gaz1);
        gaz2 = new Information("H2", "ppm");
        colonne2.add(gaz2);
        gaz3 = new Information("CH4", "ppm");
        colonne2.add(gaz3);
        gridMonitor.add(colonne2);

        Block blockMonitor = new Block("Monitoring", gridMonitor);
        column2.add(blockMonitor);

        // Ajout du block alerte
        gridAlerts = new BoxPanel(true);

        JScrollPane scrollPaneAlerts = new JScrollPane(gridAlerts);
        scrollPaneAlerts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Block blockAlerts = new Block("Alertes", scrollPaneAlerts);

        column2.add(blockAlerts);
        JButton jButton = new JButton("Effacer");
        column2.add(jButton);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(column2);

        Column column3 = new Column();
        gridStock = new BoxPanel(true);

        JScrollPane scrollPaneStock = new JScrollPane(gridStock);

        scrollPaneStock.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Block blockStock = new Block("Stock", scrollPaneStock);
        column3.add(blockStock);
        column3.add(new JButton("Mode ajout"));
        add(column3);

        timerInfo = new Timer(1000, this);
        timerInfo.start();
        timerRapide = new Timer(500, this);
        timerRapide.start();

    }

    @Override
    public Insets getInsets() {
        return new Insets(padding, padding, padding, padding);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timerInfo) {
            temperature.maj(connexion.getDonnee(1));
            humidite.maj(connexion.getDonnee(2));
            ouvert.maj(connexion.getOuverture() ? "oui" : "non");
            gaz1.maj((int) connexion.getDonnee(3));
            gaz2.maj((int) connexion.getDonnee(8));
            gaz3.maj((int) connexion.getDonnee(9));

        } else if (e.getSource() == timerRapide) {

            gridStock.removeAll();
            ArrayList<Produit> nouveauxProduits = connexion.getProduits();
            // TODO : Ajouter seulement les nouveaux produits
            for (Produit produit : nouveauxProduits) {
                gridStock.add(new ProduitCompo(produit));
            }

            // TODO : Fixez moi cette requete
            // ArrayList<Seuil> seuils = connexion.getSeuils();
            // for (Seuil seuil : seuils) {
            // gridAlerts.add(new Alert(seuil));
            // }

        }

    }

}
