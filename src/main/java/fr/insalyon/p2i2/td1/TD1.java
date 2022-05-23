package fr.insalyon.p2i2.td1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TD1 {

    // À adapter à votre BD
    private final String serveurBD = "fimi-bd-srv1.insa-lyon.fr";
    private final String portBD = "3306";
    private final String nomBD = "G224_E_BD1";
    private final String loginBD = "G224_E";
    private final String motdepasseBD = "G224_E";

    private Connection connection = null;
    private PreparedStatement insertMesureStatement = null;
    private PreparedStatement selectMesuresStatement = null;

    public void main(String[] args) {

        System.out.println("Début du TD1");

        try {

            TD1 td1 = new TD1();

            // Partie 2
            System.out.println("Début de la Partie 2");
            //td1.sommeDepuisClavier();
            //td1.lireFichier("./fichiers/input/mesures-input.txt");
            //td1.ecrireFichier("./fichiers/output");
            System.out.println("Fin de la Partie 2");

            // Format de date pour Partie 3 et Partie 4
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            
            // Partie 3
            System.out.println("Début de la Partie 3");

            td1.connexionBD();
            td1.creerRequetesParametrees();
            //td1.lireMesuresDepuisFichier("./fichiers/input/mesures-input.txt");

            //td1.ecrireMesures(
            //        new PrintWriter(System.out, true),
            //        1,
            //        formatDate.parse("01/05/2020"),
            //        formatDate.parse("08/05/2020")
            //);

            //td1.ecrireMesuresDansFichier(
            //        "./fichiers/output",
            //        1,
            //        formatDate.parse("01/05/2020"),
            //        formatDate.parse("08/05/2020")
            //);

            System.out.println("Fin de la Partie 3");
            
            // Partie 4
            System.out.println("Début de la Partie 4");

            td1.ecrireMesuresJSON(
                    new PrintWriter(System.out, true),
                    50,
                    formatDate.parse("06/05/2022"),
                    formatDate.parse("07/05/2022")
            );
            
            td1.ecrireMesuresDansFichierDataJS(
                    "./fichiers/html",
                    50,
                    formatDate.parse("06/05/2022"),
                    formatDate.parse("07/05/2022")
            );

            System.out.println("Fin de la Partie 4");

        } catch (Exception ex) {
            //ex.printStackTrace(System.err);
            System.out.println();
            System.out.println("/!\\ Exception lors de l'exécution: " + ex.getMessage());
        }

        System.out.println("Fin du TD1");
    }

    public void sommeDepuisClavier() throws Exception {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            int somme = 0;

            String ligne;
            System.out.println("Entrer un nombre: ");
            while ((ligne = input.readLine()) != null) {

                System.out.println("Ligne lue: >>>" + ligne + "<<<");

                if (ligne.equals("fin")) {
                    break;
                }

                // À compléter
                int nombre = Integer.parseInt(ligne);
                somme = somme + nombre;

                System.out.println("Entrer un autre nombre (ou 'fin'): ");
            }

            System.out.println("Somme: " + somme);

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode sommeDepuisClavier()");
        }

    }

    public void lireFichier(String cheminVersFichier) throws Exception {
        try {
            // À compléter
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    new FileInputStream(cheminVersFichier)
            ));

            int nbLignes = 0;
            double somme = 0.0;

            String line;

            while ((line = input.readLine()) != null) {
                String[] valeurs = line.split(";");
                if (valeurs.length > 1) {

                    // À compléter
                    Integer numInventaire = Integer.parseInt(valeurs[0]);
                    Double valeur = Double.parseDouble(valeurs[1]);
                    System.out.println("Le Capteur n°" + numInventaire + " a mesuré: " + valeur);

                    nbLignes++;
                    // À compléter
                    somme = somme + valeur;
                }
            }

            input.close();

            System.out.println("Nombre de Lignes: " + nbLignes);
            System.out.println("Moyennes des Mesures: " + (somme / nbLignes));

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode lireFichier()");
        }

    }

    public void ecrireFichier(String cheminVersDossier) throws Exception {
        try {

            DecimalFormat formatNombreDecimal = new DecimalFormat("0.00");
            formatNombreDecimal.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ROOT));

            SimpleDateFormat formatDatePourNomFichier = new SimpleDateFormat("yyyyMMdd-HHmmss");

            String datePourNomFichier = formatDatePourNomFichier.format(new Date());
            String nomFichier = "nombres-output_" + datePourNomFichier + ".csv";

            // À compléter
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(cheminVersDossier + "/" + nomFichier) // aide: (cheminVersDossier + "/" + nomFichier)
            ));

            for (int i = 0; i < 250; i++) {

                double nombre = Math.random() * 100;
                // À compléter
                writer.println( formatNombreDecimal.format(nombre) );
            }

            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireFichier()");
        }

    }

    public void connexionBD() throws Exception {

        try {
            //Enregistrement de la classe du driver par le driverManager
            //Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Driver trouvé...");
            //Création d'une connexion sur la base de donnée
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

            //System.out.println("Liste des tables:");
            while (result.next()) {
                //System.out.println("- " + result.getString("table_schema") + "." + result.getString("table_name"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode connexionBD()");
        }

    }

    public void creerRequetesParametrees() throws Exception {
        try {
            // À compléter
            this.insertMesureStatement = this.connection.prepareStatement("INSERT INTO Mesure (numInventaire,valeur,dateMesure) VALUES (?,?,?)");
            this.selectMesuresStatement = this.connection.prepareStatement("SELECT * FROM Mesure WHERE numInventaire = ? AND dateMesure >= ? AND dateMesure < ?");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode creerRequetesParametrees()");
        }
    }

    public void lireMesuresDepuisFichier(String cheminVersFichier) throws Exception {
        try {
            // À compléter
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    new FileInputStream(cheminVersFichier)
            ));

            lireMesures(input);

            input.close();

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode lireMesuresDepuisFichier()");
        }

    }

    public void lireMesures(BufferedReader input) throws Exception {
        try {

            String line;

            while ((line = input.readLine()) != null) {
                String[] valeurs = line.split(";");
                if (valeurs.length > 1) {

                    // À compléter
                    Integer numInventaire = Integer.parseInt(valeurs[0]);
                    Double valeur = Double.parseDouble(valeurs[1]);
                    System.out.println("Le Capteur n°" + numInventaire + " a mesuré: " + valeur);

                    ajouterMesure(numInventaire, valeur, new Date());
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode lireMesures()");
        }

    }

    public int ajouterMesure(int numInventaire, double valeur, Date datetime) {
        try {
            // À compléter
            this.insertMesureStatement.setInt(1, numInventaire);
            this.insertMesureStatement.setDouble(2, valeur);
            this.insertMesureStatement.setTimestamp(3, new Timestamp(datetime.getTime())); // DATETIME
            return this.insertMesureStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return -1;
        }
    }

    public void ecrireMesures(PrintWriter output, int numInventaire, Date dateDebut, Date dateFin) throws Exception {

        try {

            // À compléter
            this.selectMesuresStatement.setInt(1, numInventaire);
            this.selectMesuresStatement.setTimestamp(2, new Timestamp(dateDebut.getTime())); // DATETIME
            this.selectMesuresStatement.setTimestamp(3, new Timestamp(dateFin.getTime())); // DATETIME
            ResultSet result = this.selectMesuresStatement.executeQuery();

            SimpleDateFormat formatDatePourCSV = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DecimalFormat formatNombreDecimal = new DecimalFormat("0.00");
            formatNombreDecimal.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ROOT));

            while (result.next()) {

                // À compléter
                output.println(
                        result.getInt("numInventaire") + ";"
                        + formatDatePourCSV.format( result.getTimestamp("dateMesure") ) + ";"
                        + formatNombreDecimal.format( result.getDouble("valeur") ) + ";"
                );

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesures()");
        }
    }

    public void ecrireMesuresDansFichier(String cheminVersDossier, int numInventaire, Date dateDebut, Date dateFin) throws Exception {

        try {
            SimpleDateFormat formatDatePourNomFichier = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String datePourNomFichier = formatDatePourNomFichier.format(new Date());
            String nomFichier = "mesures-output_" + datePourNomFichier + ".csv";

            // À compléter
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(cheminVersDossier + "/" + nomFichier) // aide: (cheminVersDossier + "/" + nomFichier)
            ));

            ecrireMesures(writer, numInventaire, dateDebut, dateFin);

            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesuresDansFichier()");
        }
    }
    
    public void ecrireMesuresJSON(PrintWriter output, int numInventaire, Date dateDebut, Date dateFin) throws Exception {

        try {

            // À compléter
            this.selectMesuresStatement.setInt(1, numInventaire);
            this.selectMesuresStatement.setTimestamp(2, new Timestamp(dateDebut.getTime())); // DATETIME
            this.selectMesuresStatement.setTimestamp(3, new Timestamp(dateFin.getTime())); // DATETIME
            ResultSet result = this.selectMesuresStatement.executeQuery();

            // SimpleDateFormat formatDatePourCSV = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DecimalFormat formatNombreDecimal = new DecimalFormat("0.00");
            formatNombreDecimal.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ROOT));

            output.println("var SAMPLE_TIME_SERIES = [{");
            output.println("  name: \"Mon Capteur n°" + Integer.toString(numInventaire) + "\",");
            output.println("  data : [");

            while (result.next()) {

                // À compléter
                output.println(
                        "    ["
                        + Long.toString(result.getTimestamp("dateMesure").getTime())
                        + ","
                        + formatNombreDecimal.format( result.getDouble("valeur") )
                        + "],"
                );

            }
            
            output.println("]}];");

        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesures()");
        }
    }

    public void ecrireMesuresDansFichierDataJS(String cheminVersDossier, int numInventaire, Date dateDebut, Date dateFin) throws Exception {

        try {
            String nomFichier = "data.js";

            // À compléter
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(cheminVersDossier + "/" + nomFichier) // aide: (cheminVersDossier + "/" + nomFichier)
            ));
            
            ecrireMesuresJSON(writer, numInventaire, dateDebut, dateFin);

            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesuresDansFichier()");
        }
    }

}
