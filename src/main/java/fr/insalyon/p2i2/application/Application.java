package fr.insalyon.p2i2.application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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

    private Graph graph1;
    private Graph graph2;
    private Graph graph3;

    private ConnexionBD connexion;
    private Timer timerInfo;
    private Timer timerRapide;

    private BoxPanel gridStock;
    private BoxPanel gridAlerts;

    private ArrayList<Produit> listeProduits;
    private ArrayList<ProduitCompo> listeProduitsCompo;

    public Application() {

        connexion = new ConnexionBD();
        listeProduits = new ArrayList<>();
        listeProduitsCompo = new ArrayList<>();

        setLayout(new GridLayout(0, 3, padding, padding));

        Column column1 = new Column();
        add(column1);
        BoxPanel gridGraphs = new BoxPanel(true);
        Block blockGraphs = new Block("Graphiques", gridGraphs);
        column1.add(blockGraphs);
        graph1 = new Graph("Température", Color.blue);
        graph2 = new Graph("Humidité", Color.red);
        graph3 = new Graph("Gaz", Color.green);
        gridGraphs.add(graph1);
        gridGraphs.add(graph2);
        gridGraphs.add(graph3);
        graph1.init(connexion.getDonnees(1));
        graph2.init(connexion.getDonnees(2));
        graph3.init(connexion.getDonnees(3));

        Column column2 = new Column();
        add(column2);
        JLabel logo = new JLabel(new ImageIcon("./fichiers/img/logo.png"));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setAlignmentY(Component.TOP_ALIGNMENT);
        column2.add(logo);
        column2.add(Box.createRigidArea(new Dimension(0, 10)));

        // Ajout du block monitoring
        BoxPanel gridMonitor = new BoxPanel(false);
        Block blockMonitor = new Block("Monitoring", gridMonitor);
        column2.add(blockMonitor);

        BoxPanel colonneMonitoring1 = new BoxPanel(true);
        temperature = new Information("Temperature", "°C");
        colonneMonitoring1.add(temperature);
        humidite = new Information("Humidité", "%");
        colonneMonitoring1.add(humidite);
        ouvert = new Information("Ouvert", "");
        colonneMonitoring1.add(ouvert);
        gridMonitor.add(colonneMonitoring1);

        BoxPanel colonneMonitoring2 = new BoxPanel(true);
        gaz1 = new Information("NH3", "ppm");
        colonneMonitoring2.add(gaz1);
        gaz2 = new Information("H2", "ppm");
        colonneMonitoring2.add(gaz2);
        gaz3 = new Information("CH4", "ppm");
        colonneMonitoring2.add(gaz3);
        gridMonitor.add(colonneMonitoring2);

        // Ajout du block alerte
        gridAlerts = new BoxPanel(true);
        JScrollPane scrollPaneAlerts = new JScrollPane(gridAlerts);
        scrollPaneAlerts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Block blockAlerts = new Block("Alertes", scrollPaneAlerts);
        column2.add(blockAlerts);

        JButton jButton = new JButton("Effacer");
        column2.add(jButton);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Column column3 = new Column();
        add(column3);
        gridStock = new BoxPanel(true);
        JScrollPane scrollPaneStock = new JScrollPane(gridStock);
        scrollPaneStock.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Block blockStock = new Block("Contenu", scrollPaneStock);
        column3.add(blockStock);

        column3.add(new JButton("Mode ajout"));

        timerInfo = new Timer(1000, this);
        timerInfo.setInitialDelay(1);
        timerInfo.start();
        timerRapide = new Timer(500, this);
        timerRapide.setInitialDelay(1);
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

            graph1.update(connexion.getDonnee(1));
            graph2.update(connexion.getDonnee(2));
            graph3.update(connexion.getDonnee(3));

        } else if (e.getSource() == timerRapide) {

            ArrayList<Produit> nouveauxProduits = connexion.getProduits();
            for (Produit produit : nouveauxProduits) {
                if (listeProduits.contains(produit)) {
                    for (ProduitCompo compo : listeProduitsCompo) {
                        if (produit.equals(compo.produit)) {
                            compo.majQuantite(produit);
                        }
                    }
                } else {
                    ProduitCompo produitCompo = new ProduitCompo(produit);
                    listeProduitsCompo.add(produitCompo);
                    gridStock.add(produitCompo);
                    listeProduits.add(produit);
                }
            }

            gridAlerts.removeAll();
            ArrayList<Seuil> seuils = connexion.getSeuils();
            for (Seuil seuil : seuils) {
                gridAlerts.add(new Alerte(seuil));
            }

        }

    }

}
