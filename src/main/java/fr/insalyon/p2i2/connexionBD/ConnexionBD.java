package fr.insalyon.p2i2.connexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ConnexionBD {

    // À adapter à votre BD
    private final String serveurBD = "fimi-bd-srv1.insa-lyon.fr";
    private final String portBD = "3306";
    private final String nomBD = "G221_A_BD2";
    private final String loginBD = "G221_A";
    private final String motdepasseBD = "G221_A";

    private Connection connection;

    private final int TEMPS_ALERTE_OUVERTURE = 1; // en minute

    public ConnexionBD() {

        try {
            String urlJDBC = "jdbc:mysql://" + this.serveurBD + ":" + this.portBD + "/" + this.nomBD;
            urlJDBC += "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Europe/Paris";

            System.out.println("Connexion à la BD ...");
            this.connection = DriverManager.getConnection(urlJDBC, this.loginBD, this.motdepasseBD);

            System.out.println("Connexion établie.");

            // Requête de test pour lister les tables existantes dans les BDs MySQL
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT table_schema, table_name"
                            + " FROM information_schema.tables"
                            + " WHERE table_schema NOT LIKE '%_schema' AND table_schema != 'mysql'"
                            + " ORDER BY table_schema, table_name");
            statement.executeQuery();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

    }

    public ResultSet getNouvelleMesures() {
        try {
            String query = "SELECT idCapteur, idMesure, valeur FROM Mesure "
                    + "WHERE idMesure IN (SELECT MAX(idMesure) FROM Mesure GROUP BY idCapteur)";
            PreparedStatement selectMesureStatement = this.connection.prepareStatement(query);
            return selectMesureStatement.executeQuery();

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
            String query = "SELECT porteOuverte FROM OuverturePorte ORDER BY idOuverture DESC LIMIT 0,1;";
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

            String query3 = "SELECT porteOuverte, dateOuverture FROM OuverturePorte ORDER BY dateOuverture DESC LIMIT 0,1;";
            PreparedStatement selectOuvertureStatement = this.connection.prepareStatement(query3);
            ResultSet dateOuverture = selectOuvertureStatement.executeQuery();

            if (dateOuverture.next()) {

                Boolean porteOuverte = dateOuverture.getBoolean("porteOuverte");
                LocalDateTime derniereDate = dateOuverture.getTimestamp("dateOuverture").toLocalDateTime();
                LocalDateTime dateActuelle = LocalDateTime.now();
                float diffDate = ChronoUnit.MINUTES.between(derniereDate, dateActuelle);

                if ((porteOuverte == true) && (diffDate >= TEMPS_ALERTE_OUVERTURE)) {
                    Seuil seuil = new Seuil("réfrigérateur", TEMPS_ALERTE_OUVERTURE, "durée", diffDate, "min");
                    listeSeuils.add(seuil);
                }

            }

            String query1 = "SELECT DISTINCT nomCategorieProduit, nomTypeMesure, valeur, unite, seuilMin, seuilMax "
                    +
                    "FROM Seuil, TypeMesure, Mesure, Capteur, Produit, CategorieProduit, AssociationCategorie " +
                    "WHERE Seuil.idTypeMesure = TypeMesure.idTypeMesure " +
                    "AND Capteur.idTypeMesure = TypeMesure.idTypeMesure " +
                    "AND Mesure.idCapteur = Capteur.idCapteur " +
                    "AND Seuil.idCategorieProduit = CategorieProduit.idCategorieProduit " +
                    "AND CategorieProduit.idCategorieProduit = AssociationCategorie.idCategorieProduit " +
                    "AND Produit.codeBarre = AssociationCategorie.codeBarre " +
                    "AND Mesure.dateMesure IN (SELECT MAX(dateMesure) FROM Mesure, Capteur WHERE Capteur.idCapteur = Mesure.idCapteur GROUP BY nomCapteur) " +
                    "AND (Capteur.idCapteur = 1 OR Capteur.idCapteur = 2) " +
                    "AND (seuilMax < valeur OR seuilMin > valeur) " +
                    "AND Produit.quantite != 0 " +
                    "ORDER BY nomCategorieProduit;";

            PreparedStatement selectCategorieProduitStatement = this.connection.prepareStatement(query1);

            ResultSet CategoriesProduits = selectCategorieProduitStatement.executeQuery();

            while (CategoriesProduits.next()) {

                ArrayList<String> listeProduits = new ArrayList<>();

                String query2 = "SELECT nomProduit " +
                        "FROM Produit, AssociationCategorie, CategorieProduit " +
                        "WHERE Produit.codeBarre = AssociationCategorie.codeBarre " +
                        "AND AssociationCategorie.idCategorieProduit = CategorieProduit.idCategorieProduit " +
                        "AND Produit.quantite != 0 " +
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
            }
            return listeSeuils;

        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return listeSeuils;
        }
    }

    public void majQuantite(boolean ajout) {
        PreparedStatement selectCodeBarreStatement;
        try {
            selectCodeBarreStatement = connection.prepareStatement("SELECT codeBarre"
                    + " FROM CodeBarre"
                    + " WHERE ajout IS NULL;");
            PreparedStatement updateProduitStatement = connection.prepareStatement("UPDATE Produit"
                    + " SET quantite = quantite + ?"
                    + " WHERE codeBarre = ?;");
            PreparedStatement updateCodeBarreStatement = connection.prepareStatement("UPDATE CodeBarre"
                    + " SET ajout = ?"
                    + " WHERE ajout IS NULL;");
            ResultSet result = selectCodeBarreStatement.executeQuery();

            while (result.next()) {
                long codeBarre = result.getLong(1);

                updateProduitStatement.setInt(1, ajout ? 1 : -1);
                updateProduitStatement.setLong(2, codeBarre);
                updateProduitStatement.executeUpdate();
            }

            updateCodeBarreStatement.setBoolean(1, ajout);
            updateCodeBarreStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
