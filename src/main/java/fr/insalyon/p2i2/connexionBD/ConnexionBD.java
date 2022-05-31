package fr.insalyon.p2i2.connexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import fr.insalyon.p2i2.application.Graph;

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

    public double getDonnee(int idCapteur) {
        try {

            String query = "SELECT valeur FROM Mesure, Capteur WHERE Capteur.idCapteur = Mesure.idCapteur AND Capteur.idCapteur = ? ORDER BY Mesure.dateMesure DESC LIMIT 0,1";
            PreparedStatement selectMesureStatement = this.connection.prepareStatement(query);
            selectMesureStatement.setInt(1, idCapteur);
            ResultSet donnee = selectMesureStatement.executeQuery();
            if (donnee.next()) {
                return donnee.getDouble("valeur");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return 0;
        }
    }

    public ArrayList<Double> getDonnees(int idCapteur) {
        try {
            ArrayList<Double> mesures = new ArrayList<Double>();
            String query = "SELECT valeur FROM Mesure, Capteur WHERE Capteur.idCapteur = Mesure.idCapteur AND Capteur.idCapteur = ? ORDER BY Mesure.dateMesure DESC LIMIT 0,?";
            PreparedStatement selectMesureStatement = this.connection.prepareStatement(query);
            selectMesureStatement.setInt(1, idCapteur);
            selectMesureStatement.setInt(2, Graph.POINTS);
            ResultSet donnee = selectMesureStatement.executeQuery();
            while (donnee.next()) {
                mesures.add(donnee.getDouble("valeur"));
            }
            return mesures;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }

    public ArrayList<Produit> getProduits() {
        ArrayList<Produit> listeProduits = new ArrayList<Produit>();
        try {
            String query = "SELECT nomProduit, quantite, Produit.codeBarre, marque, imageURL, masse "
                    + "FROM Produit, CodeBarre "
                    + "WHERE Produit.codeBarre = CodeBarre.codebarre "
                    + "AND CodeBarre.dateCodeBarre IN (SELECT MAX(dateCodeBarre) FROM CodeBarre, Produit WHERE Produit.codeBarre = CodeBarre.codeBarre GROUP BY nomProduit) "
                    + "AND quantite != 0 "
                    + "GROUP BY nomProduit "
                    + "ORDER BY dateCodeBarre DESC";
            PreparedStatement selectProduitsStatement = this.connection.prepareStatement(query);
            ResultSet result = selectProduitsStatement.executeQuery();
            while (result.next()) {
                Produit aliment = new Produit(result.getString("nomProduit"),
                        result.getInt("quantite"), result.getLong("codeBarre"),
                        result.getString("marque"), result.getString("imageURL"), result.getInt("masse"));
                listeProduits.add(aliment);
            }
            return listeProduits;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return listeProduits;
        }
    }

    public boolean getOuverture() {
        try {
            String query = "SELECT porteOuverte FROM OuverturePorte ORDER BY dateOuverture DESC LIMIT 0,1;";
            PreparedStatement selectOuvertureStatement = this.connection.prepareStatement(query);
            ResultSet ouverture = selectOuvertureStatement.executeQuery();
            ouverture.next();
            return ouverture.getBoolean("porteOuverte");
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<Seuil> getSeuils() {
        ArrayList<Seuil> listeSeuils = new ArrayList<>();
        try {

            String query1 = "SELECT DISTINCT nomCategorieProduit, nomTypeMesure, valeur, unite, seuilMin, seuilMax " +
                    "FROM Seuil, TypeMesure, Mesure, Capteur, Produit, CategorieProduit, AssociationCategorie " +
                    "WHERE Seuil.idTypeMesure = TypeMesure.idTypeMesure " +
                    "AND Capteur.idTypeMesure = TypeMesure.idTypeMesure " +
                    "AND Mesure.idCapteur = Capteur.idCapteur " +
                    "AND Seuil.idCategorieProduit = CategorieProduit.idCategorieProduit " +
                    "AND CategorieProduit.idCategorieProduit = AssociationCategorie.idCategorieProduit " +
                    "AND Mesure.dateMesure IN (SELECT MAX(dateMesure) FROM Mesure, Capteur WHERE Capteur.idCapteur = Mesure.idCapteur GROUP BY nomCapteur) "
                    +
                    "AND (Capteur.idCapteur = 1 OR Capteur.idCapteur = 2) " +
                    "AND (seuilMax < valeur OR seuilMin > valeur) " +
                    "ORDER BY nomCategorieProduit;";

            PreparedStatement selectCategorieProduitStatement = this.connection.prepareStatement(query1);

            // long time1 = System.currentTimeMillis();
            ResultSet CategoriesProduits = selectCategorieProduitStatement.executeQuery();
            // long time2 = System.currentTimeMillis();
            // System.out.print(time2 - time1);
            // System.out.println(" ms");
            System.out.println(CategoriesProduits.next());

            while (CategoriesProduits.next()) {

                ArrayList<String> listeProduits = new ArrayList<>();

                String query2 = "SELECT nomProduit " +
                        "FROM Produit, AssociationCategorie, CategorieProduit " +
                        "WHERE Produit.codeBarre = AssociationCategorie.codeBarre " +
                        "AND AssociationCategorie.idCategorieProduit = CategorieProduit.idCategorieProduit " +
                        "AND nomCategorieProduit = ?;";

                PreparedStatement selectProduitsStatement = this.connection.prepareStatement(query2);
                selectProduitsStatement.setString(1, CategoriesProduits.getString("nomCategorieProduit"));
                ResultSet Produits = selectProduitsStatement.executeQuery();

                while (Produits.next()) {
                    listeProduits.add(Produits.getString("nomProduit"));
                }

                Seuil seuil = new Seuil(
                        listeProduits,
                        CategoriesProduits.getString("nomCategorieProduit"),
                        CategoriesProduits.getString("nomTypeMesure"),
                        CategoriesProduits.getFloat("seuilMin"),
                        CategoriesProduits.getFloat("seuilMax"),
                        CategoriesProduits.getFloat("valeur"),
                        CategoriesProduits.getString("unite"));

                listeSeuils.add(seuil);
                // System.out.println(seuil.toString());
            }
            return listeSeuils;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return listeSeuils;
        }
    }
}
