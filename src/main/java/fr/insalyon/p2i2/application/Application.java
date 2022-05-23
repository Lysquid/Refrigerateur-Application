package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.*;

public class Application extends JPanel {

    private final int padding = 30;

    public Application() {

        setLayout(new GridLayout(0, 3, padding, padding));

        Column column1 = new Column();
        add(column1);

        column1.add(new Block());
        column1.add(Box.createRigidArea(new Dimension(0, 50)));
        column1.add(new Block());

        Column column2 = new Column();
        add(column2);

        Column column3 = new Column();
        add(column3);

    }

    @Override
    public Insets getInsets() {
        return new Insets(padding, padding, padding, padding);
    }

}
