package fr.insalyon.p2i2.application;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Column extends JPanel {

    public Column() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
    }

    @Override
    public Insets getInsets() {
        int insets = 0;
        return new Insets(insets, insets, insets, insets);
    }

    public void addSpace() {
        add(Box.createRigidArea(new Dimension(0, Application.gap / 2)));
    }

}
