package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import fr.insalyon.p2i2.connexionBD.ConnexionBD;
import fr.insalyon.p2i2.connexionBD.Produit;
import fr.insalyon.p2i2.connexionBD.Seuil;

public class Application extends JPanel implements ActionListener {

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

    public static final int gap = 30;
    public static final int compoInset = 12;
    public static final Color backgroundColor = Color.decode("#f2f2f2");
    public static final Color blockColor = Color.decode("#ffffff");
    public static final Color borderColor = Color.decode("#d6d6d6");
    public static final Color accentColor = Color.decode("#737df5");

    public Application() {

        connexion = new ConnexionBD();
        listeProduits = new ArrayList<>();
        listeProduitsCompo = new ArrayList<>();

        setLayout(new GridLayout(0, 3));
        setBackground(backgroundColor);

        Column column1 = new Column();
        add(column1);
        // BoxPanel gridGraphs = new BoxPanel(true);
        GridPanel gridGraphs = new GridPanel(1, 3);
        graph1 = new Graph("Température", Color.blue);
        graph2 = new Graph("Humidité", Color.red);
        graph3 = new Graph("Gaz", Color.green);
        gridGraphs.add(graph1);
        gridGraphs.add(graph2);
        gridGraphs.add(graph3);
        graph1.init(connexion.getDonnees(1));
        graph2.init(connexion.getDonnees(2));
        graph3.init(connexion.getDonnees(3));
        Graph[] listeGraphs = { graph1, graph2, graph3 };
        JComboBox<Graph> comboBoxGraphs = new JComboBox<Graph>(listeGraphs) {
            @Override
            public Dimension getMaximumSize() {
                return super.getPreferredSize();
            }
        };
        Block blockGraphs = new Block("Graphiques", gridGraphs, comboBoxGraphs);
        column1.add(blockGraphs);

        Column column2 = new Column();
        add(column2);
        JLabel logo = new JLabel(new ImageIcon("./fichiers/img/logo.png"));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setAlignmentY(Component.TOP_ALIGNMENT);
        column2.addSpace();
        column2.add(logo);
        column2.addSpace();

        // Ajout du block monitoring
        GridPanel gridMonitor = new GridPanel(2, 3);
        Block blockMonitor = new Block("Monitoring", gridMonitor);
        column2.add(blockMonitor);

        temperature = new Information("Temperature", "°C");
        gridMonitor.add(temperature);
        gaz1 = new Information("NH3", "ppm");
        gridMonitor.add(gaz1);
        humidite = new Information("Humidité", "%");
        gridMonitor.add(humidite);
        gaz2 = new Information("H2", "ppm");
        gridMonitor.add(gaz2);
        ouvert = new Information("Ouvert", "");
        gridMonitor.add(ouvert);
        gaz3 = new Information("CH4", "ppm");
        gridMonitor.add(gaz3);

        // Ajout du block alerte
        gridAlerts = new BoxPanel();
        JScrollPane scrollPaneAlerts = new ScrollPane(gridAlerts);
        Block blockAlerts = new Block("Alertes", scrollPaneAlerts);
        column2.add(blockAlerts);

        Column column3 = new Column();
        add(column3);
        gridStock = new BoxPanel();
        JScrollPane scrollPaneStock = new ScrollPane(gridStock);
        JButton boutonAjout = new JButton("Mode ajout");
        Block blockStock = new Block("Contenu", scrollPaneStock, boutonAjout);
        column3.add(blockStock);

        timerInfo = new Timer(1000, this);
        timerInfo.setInitialDelay(1);
        timerInfo.start();
        timerRapide = new Timer(500, this);
        timerRapide.setInitialDelay(1);
        timerRapide.start();

    }

    @Override
    public Insets getInsets() {
        int insets = Application.gap / 2;
        return new Insets(insets, insets, insets, insets);
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
