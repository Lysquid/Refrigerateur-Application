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

    public Grid(int wrapAfter, boolean fillY) {
        setBackground(Color.YELLOW);

        LC layoutConstraints = new LC().wrapAfter(wrapAfter).insets("20");
        AC colConstraints = new AC().fill().grow().sizeGroup("main");
        AC rowConstraints = new AC();

        if (fillY) {
            rowConstraints.fill().grow().sizeGroup("main");
        }

        setLayout(new MigLayout(
                layoutConstraints,
                colConstraints,
                rowConstraints));
    }

}
