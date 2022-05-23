package fr.insalyon.p2i2.application;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Application extends JPanel {

    public Application() {

        setLayout(new GridLayout(0, 3, 50, 50));

        Column colonne = new Column();
        add(colonne);

        Column colonne2 = new Column();
        add(colonne2);

        Column colonne3 = new Column();
        add(colonne3);

    }

}
