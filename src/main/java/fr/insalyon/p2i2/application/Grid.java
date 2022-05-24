package fr.insalyon.p2i2.application;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import net.miginfocom.layout.*;

public class Grid extends JPanel {

    private final int gap = 5;

    public Grid(int rows, int cols) {
        this(1);
        setLayout(new GridLayout(rows, cols, gap, gap));
    }

    public Grid(int wrapAfter) {
        setBackground(Color.YELLOW);
        // setLayout(new FlowLayout(FlowLayout.LEFT, gap, gap));
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        // setLayout(new GridBagLayout());
        setLayout(new MigLayout(
                new LC().wrapAfter(wrapAfter).insets("20"),
                new AC().fill().grow(),
                new AC()));
    }

}
