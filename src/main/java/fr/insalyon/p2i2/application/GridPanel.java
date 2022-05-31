package fr.insalyon.p2i2.application;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GridPanel extends JPanel {

    public GridPanel(int col, int rows) {
        setLayout(new GridLayout(3, 2, Application.gap, Application.gap));
        setBackground(Color.YELLOW);
    }

}
