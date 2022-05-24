package fr.insalyon.p2i2.application;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Grid extends JPanel {

    private final int gap = 15;
    private boolean vertical;

    public Grid(boolean vertical) {

        setBackground(Color.YELLOW);

        this.vertical = vertical;
        setLayout(new BoxLayout(this, vertical ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS));
    }

    @Override
    public Component add(Component comp) {
        if (getComponentCount() > 0) {
            super.add(Box.createRigidArea(new Dimension(vertical ? 0 : gap, vertical ? gap : 0)));
        }
        return super.add(comp);
    }

}
