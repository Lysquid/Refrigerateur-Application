package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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

    private Graph graphTemp;
    private Graph graphHumi;
    private Graph graphGaz;

    private ConnexionBD connexion;
    private Timer timerInfo;
    private Timer timerLent;

    private BoxPanel gridStock;
    private BoxPanel gridAlerts;
    private JComboBox<String> comboBoxGraphs;
    private HashMap<String, Integer> capteursGaz;

    private ArrayList<Produit> listeProduits;
    private ArrayList<ProduitCompo> listeProduitsCompo;

    private JButton boutonAjout;
    private Boolean Ajout = true;

    public static final int gap = 30;
    public static final int compoInset = 12;
    public static final Color backgroundColor = Color.decode("#f2f2f2");
    public static final Color blockColor = Color.decode("#ffffff");
    public static final Color borderColor = Color.decode("#d6d6d6");
    public static final Color graphColor = Color.decode("#222222");

    public Application() {

        connexion = new ConnexionBD();
        listeProduits = new ArrayList<>();
        listeProduitsCompo = new ArrayList<>();

        setLayout(new GridLayout(0, 3));
        setBackground(backgroundColor);

        Column column1 = new Column();
        add(column1);
        capteursGaz = new HashMap<>();
        capteursGaz.put("NH3", 3);
        capteursGaz.put("CO", 4);
        capteursGaz.put("NO2", 5);
        String[] listeCapteursGaz = capteursGaz.keySet().toArray(new String[capteursGaz.size()]);

        GridPanel gridGraphs = new GridPanel(1, 3);
        graphTemp = new Graph("Température", "°C", Color.blue);
        graphHumi = new Graph("Humidité", "%", Color.red);
        graphGaz = new Graph("Gaz", "ppm", Color.green);
        gridGraphs.add(graphTemp);
        gridGraphs.add(graphHumi);
        gridGraphs.add(graphGaz);
        graphTemp.init(connexion.getDonnees(1));
        graphHumi.init(connexion.getDonnees(2));
        graphGaz.init(connexion.getDonnees(3));

        comboBoxGraphs = new JComboBox<String>(listeCapteursGaz) {
            @Override
            public Dimension getMaximumSize() {
                return super.getPreferredSize();
            }
        };
        comboBoxGraphs.addActionListener(this);
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
        boutonAjout = new JButton("Mode ajout");
        boutonAjout.addActionListener(this);
        Block blockStock = new Block("Contenu", scrollPaneStock, boutonAjout);
        column3.add(blockStock);

        timerInfo = new Timer(1000, this);
        timerInfo.setInitialDelay(1);
        timerInfo.start();
        timerLent = new Timer(60000, this);
        timerLent.setInitialDelay(1);
        timerLent.start();

    }

    @Override
    public Insets getInsets() {
        int insets = Application.gap / 2;
        return new Insets(insets, insets, insets, insets);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonAjout) {
            if (Ajout) {
                Ajout = false;
                boutonAjout.setText("Mode Retrait");
            } else {
                Ajout = true;
                boutonAjout.setText("Mode Ajout");
            }
            // System.out.println(boutonAjout.getText());
        }

        if (e.getSource() == timerInfo) {
            temperature.maj(connexion.getDonnee(1));
            humidite.maj(connexion.getDonnee(2));
            ouvert.maj(connexion.getOuverture() ? "oui" : "non");
            gaz1.maj((int) connexion.getDonnee(3));
            gaz2.maj((int) connexion.getDonnee(8));
            gaz3.maj((int) connexion.getDonnee(9));

            graphTemp.update(connexion.getDonnee(1));
            graphHumi.update(connexion.getDonnee(2));
            int idCapteur = capteursGaz.get(comboBoxGraphs.getSelectedItem());
            graphGaz.update(connexion.getDonnee(idCapteur));

        } else if (e.getSource() == timerLent) {
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
        if (e.getSource() == comboBoxGraphs) {
            int idCapteur = capteursGaz.get(comboBoxGraphs.getSelectedItem());
            System.out.println(idCapteur);
            graphGaz.init(connexion.getDonnees(idCapteur));

        }
    }

}
