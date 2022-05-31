package fr.insalyon.p2i2.application;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GridPanel extends JPanel {

    public GridPanel(int col, int rows) {
        setLayout(new GridLayout(3, 2, Application.gap, Application.gap));
        setOpaque(false);
    }

}
