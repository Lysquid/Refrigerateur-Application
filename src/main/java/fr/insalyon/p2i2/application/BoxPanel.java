package fr.insalyon.p2i2.application;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoxPanel extends JPanel {

    public BoxPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.YELLOW);
    }

    @Override
    public Component add(Component comp) {
        if (getComponentCount() > 0) {
            super.add(
                    Box.createRigidArea(new Dimension(0, Application.gap)));
        }
        return super.add(comp);
    }

}
