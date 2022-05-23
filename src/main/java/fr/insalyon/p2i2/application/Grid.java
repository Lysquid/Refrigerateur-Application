package fr.insalyon.p2i2.application;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.*;

public class Grid extends JPanel {

    private final int gap = 5;

    public Grid(int rows, int cols) {
        this();
        setLayout(new GridLayout(rows, cols, gap, gap));
    }

    public Grid() {
        setBackground(Color.YELLOW);
        setLayout(new FlowLayout());
    }

}
