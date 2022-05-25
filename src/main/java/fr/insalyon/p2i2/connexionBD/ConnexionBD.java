package fr.insalyon.p2i2.connexionBD;

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

public class ConnexionBD {

    private final String serveurBD = "fimi-bd-srv1.insa-lyon.fr";
    private final String portBD = "3306";
    private final String nomBD = "G221_A_BD1";
    private final String loginBD = "G221_A";
    private final String motdepasseBD = "G221_A";

    private Connection connection = null;
    private PreparedStatement insertMesureStatement = null;
    private PreparedStatement selectMesuresStatement = null;

    public ConnexionBD() {

        try {
            // Création d'une connexion sur la base de donnée
            String urlJDBC = "jdbc:mysql://" + serveurBD + ":" + portBD + "/" + nomBD;
            urlJDBC += "?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Europe/Paris";

            System.out.println("Connexion à " + urlJDBC);
            connection = DriverManager.getConnection(urlJDBC, loginBD, motdepasseBD);

            System.out.println("Connexion établie...");

            // Requête de test pour lister les tables existantes dans les BDs MySQL
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT table_schema, table_name"
                            + " FROM information_schema.tables"
                            + " WHERE table_schema NOT LIKE '%_schema' AND table_schema != 'mysql'"
                            + " ORDER BY table_schema, table_name");
            ResultSet result = statement.executeQuery();

            System.out.println("Liste des tables:");
            while (result.next()) {
                if (result.getString("table_schema").contains("BD1")) {
                    System.out.println("- " + result.getString("table_name"));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }

    }

    public void creerRequetesParametrees() throws Exception {
        try {
            // À compléter
            this.insertMesureStatement = this.connection
                    .prepareStatement("INSERT INTO Mesure (numInventaire,valeur,dateMesure) VALUES (?,?,?)");
            this.selectMesuresStatement = this.connection.prepareStatement(
                    "SELECT * FROM Mesure WHERE numInventaire = ? AND dateMesure >= ? AND dateMesure < ?");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode creerRequetesParametrees()");
        }
    }

    public void lireMesuresDepuisFichier(String cheminVersFichier) throws Exception {
        try {
            // À compléter
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    new FileInputStream(cheminVersFichier)));

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
                                + formatDatePourCSV.format(result.getTimestamp("dateMesure")) + ";"
                                + formatNombreDecimal.format(result.getDouble("valeur")) + ";");

            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesures()");
        }
    }

    public void ecrireMesuresDansFichier(String cheminVersDossier, int numInventaire, Date dateDebut, Date dateFin)
            throws Exception {

        try {
            SimpleDateFormat formatDatePourNomFichier = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String datePourNomFichier = formatDatePourNomFichier.format(new Date());
            String nomFichier = "mesures-output_" + datePourNomFichier + ".csv";

            // À compléter
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(cheminVersDossier + "/" + nomFichier) // aide: (cheminVersDossier + "/" +
                                                                               // nomFichier)
            ));

            ecrireMesures(writer, numInventaire, dateDebut, dateFin);

            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesuresDansFichier()");
        }
    }

    public void ecrireMesuresJSON(PrintWriter output, int numInventaire, Date dateDebut, Date dateFin)
            throws Exception {

        try {

            // À compléter
            this.selectMesuresStatement.setInt(1, numInventaire);
            this.selectMesuresStatement.setTimestamp(2, new Timestamp(dateDebut.getTime())); // DATETIME
            this.selectMesuresStatement.setTimestamp(3, new Timestamp(dateFin.getTime())); // DATETIME
            ResultSet result = this.selectMesuresStatement.executeQuery();

            // SimpleDateFormat formatDatePourCSV = new SimpleDateFormat("yyyy-MM-dd
            // HH:mm:ss");
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
                                + formatNombreDecimal.format(result.getDouble("valeur"))
                                + "],");

            }

            output.println("]}];");

        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesures()");
        }
    }

    public void ecrireMesuresDansFichierDataJS(String cheminVersDossier, int numInventaire, Date dateDebut,
            Date dateFin) throws Exception {

        try {
            String nomFichier = "data.js";

            // À compléter
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(cheminVersDossier + "/" + nomFichier) // aide: (cheminVersDossier + "/" +
                                                                               // nomFichier)
            ));

            ecrireMesuresJSON(writer, numInventaire, dateDebut, dateFin);

            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new Exception("Erreur dans la méthode ecrireMesuresDansFichier()");
        }
    }

    public double getTemperature() {
        try {
            // À compléter
            // this.insertMesureStatement = this.connection.prepareStatement("INSERT INTO
            // Mesure (numInventaire,valeur,dateMesure) VALUES (?,?,?)");
            this.selectMesuresStatement = this.connection.prepareStatement(
                    "SELECT valeur FROM Mesure m, Capteur c, TypeMesure t WHERE t.idTypeMesure = c.idTypeMesure AND c.idCapteur = m.idCapteur AND t.nomTypeMesure = 'température' AND c.idCapteur = 1 ORDER BY m.dateMesure DESC LIMIT 0,1 ;");
            ResultSet temperature = this.selectMesuresStatement.executeQuery();
            return temperature.getDouble(1);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return 0;
        }

        // return temperature;
    }

}
