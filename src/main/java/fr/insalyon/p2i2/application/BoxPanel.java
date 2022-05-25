package fr.insalyon.p2i2.application;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoxPanel extends JPanel {

    private static final int defaultGap = 15;

    private boolean vertical;
    private int gap;

    public BoxPanel(boolean vertical, int gap) {
        this.gap = gap;
        this.vertical = vertical;
        setLayout(new BoxLayout(this, vertical ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS));
    }

    public BoxPanel(boolean vertical) {
        this(vertical, defaultGap);
        setBackground(Color.YELLOW);
    }

    @Override
    public Component add(Component comp) {
        if (getComponentCount() > 0) {
            super.add(Box.createRigidArea(new Dimension(vertical ? 0 : gap, vertical ? gap : 0)));
        }
        return super.add(comp);
    }

}
