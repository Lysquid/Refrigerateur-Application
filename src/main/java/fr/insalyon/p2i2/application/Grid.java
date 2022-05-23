package fr.insalyon.p2i2.application;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;

public class Grid extends JPanel {

    private final int gap = 5;

    public Grid(int rows, int cols) {

        setLayout(new GridLayout(rows, cols, gap, gap));
        setBackground(Color.YELLOW);
    }

}
