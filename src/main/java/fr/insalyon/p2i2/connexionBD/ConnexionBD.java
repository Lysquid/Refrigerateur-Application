package fr.insalyon.p2i2.connexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnexionBD {

    // À adapter à votre BD
    private final String serveurBD = "fimi-bd-srv1.insa-lyon.fr";
    private final String portBD = "3306";
    private final String nomBD = "G221_A_BD1";
    private final String loginBD = "G221_A";
    private final String motdepasseBD = "G221_A";

    private Connection connection;

    public ConnexionBD() {

        try {
            // Enregistrement de la classe du driver par le driverManager
            // Class.forName("com.mysql.jdbc.Driver");
            // System.out.println("Driver trouvé...");
            // Création d'une connexion sur la base de donnée
            String urlJDBC = "jdbc:mysql://" + this.serveurBD + ":" + this.portBD + "/" + this.nomBD;
            urlJDBC += "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Europe/Paris";

            System.out.println("Connexion à " + urlJDBC);
            this.connection = DriverManager.getConnection(urlJDBC, this.loginBD, this.motdepasseBD);

            System.out.println("Connexion établie...");

            // Requête de test pour lister les tables existantes dans les BDs MySQL
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT table_schema, table_name"
                            + " FROM information_schema.tables"
                            + " WHERE table_schema NOT LIKE '%_schema' AND table_schema != 'mysql'"
                            + " ORDER BY table_schema, table_name");
            ResultSet result = statement.executeQuery();

            // System.out.println("Liste des tables:");
            while (result.next()) {
                // System.out.println("- " + result.getString("table_schema") + "." +
                // result.getString("table_name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

    }

    public double getTemperature() {
        try {
            // À compléter
            // this.insertMesureStatement = this.connection.prepareStatement("INSERT INTO
            // Mesure (numInventaire,valeur,dateMesure) VALUES (?,?,?)");
            PreparedStatement selectMesureStatement = this.connection.prepareStatement(
                    "SELECT valeur FROM Mesure, Capteur, TypeMesure WHERE TypeMesure.idTypeMesure = Capteur.idTypeMesure AND Capteur.idCapteur = Mesure.idCapteur AND TypeMesure.nomTypeMesure = 'température' AND Capteur.idCapteur = 1 ORDER BY Mesure.dateMesure DESC LIMIT 0,1 ;");
            ResultSet temperature = selectMesureStatement.executeQuery();
            return temperature.getDouble(1);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return 0;
        }

        // return temperature;
    }

    public ArrayList<Produit> getProduits() {
        ArrayList<Produit> listeProduits = new ArrayList<Produit>();
        try {
            String query = "SELECT nomProduit, quantite, codeBarre FROM Produit WHERE quantite != 0";
            PreparedStatement selectProduitsStatement = this.connection.prepareStatement(query);
            ResultSet Produits = selectProduitsStatement.executeQuery();
            while(Produits.next()){
                Produit aliment = new Produit(Produits.getString("nomProduit"),
                Produits.getInt("quantite"), Produits.getLong("codeBarre"));
                listeProduits.add(aliment);
                System.out.println("Nom du produit : " + Produits.getString("nomProduit"));
                System.out.println("Quantité du produit : " + Produits.getInt("quantite"));
                System.out.println("Code-Barre du Produit : " + Produits.getLong("codeBarre"));
            }
            return listeProduits;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return listeProduits;
        }
    }

}
