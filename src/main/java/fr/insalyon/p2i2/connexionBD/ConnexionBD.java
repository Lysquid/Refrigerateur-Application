package fr.insalyon.p2i2.connexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.constraints.Null;

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

    public double getDonnee(int idCapteur ) {
        try {

            String query = "SELECT valeur FROM Mesure, Capteur WHERE Capteur.idCapteur = Mesure.idCapteur AND Capteur.idCapteur = ? ORDER BY Mesure.dateMesure DESC LIMIT 0,1";
            PreparedStatement selectMesureStatement = this.connection.prepareStatement(query);            
            selectMesureStatement.setInt(1, idCapteur);
            ResultSet temperature = selectMesureStatement.executeQuery();
            temperature.next();
            return temperature.getDouble("valeur");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return 0;
        }
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
            }
            return listeProduits;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return listeProduits;
        }
    }

    public boolean getOuverture(){
        try {
            String query = "SELECT porteOuverte FROM OuverturePorte ORDER BY dateOuverture DESC LIMIT 0,1;";
            PreparedStatement selectOuvertureStatement = this.connection.prepareStatement(query);
            ResultSet Ouverture = selectOuvertureStatement.executeQuery();
            Ouverture.next();
            return Ouverture.getBoolean("porteOuverte");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return false;
        }
    }

    public ArrayList<Seuil> getSeuils(){
        ArrayList<Seuil> listeSeuils = new ArrayList<>();
        try {
            String query = 
            "SELECT nomCategorieProduit, nomTypeMesure, seuilMin, seuilMax"
            + " FROM Seuil, TypeMesure, CategorieProduit"
            + " WHERE Seuil.idTypeMesure = TypeMesure.idTypeMesure"
            + " AND Seuil.idCategorieProduit = CategorieProduit.idCategorieProduit;";
            PreparedStatement selectSeuilsStatement = this.connection.prepareStatement(query);
            ResultSet Seuils = selectSeuilsStatement.executeQuery();
            while(Seuils.next()){
                Seuil seuil = new Seuil(Seuils.getString("nomCategorieProduit"),
                Seuils.getString("nomTypeMesure"), Seuils.getFloat("seuilMin"),
                Seuils.getFloat("seuilMax"));
                listeSeuils.add(seuil);
                System.out.println(seuil.toString());
            }
            return listeSeuils;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return listeSeuils;
        }
    }
}
