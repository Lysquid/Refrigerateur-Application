package fr.insalyon.p2i2.application;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BoxPanel extends JPanel {

    public BoxPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBackground(Application.backgroundColor);
    }

    @Override
    public Component add(Component comp) {
        if (getComponentCount() > 0) {
            super.add(Box.createRigidArea(new Dimension(0, Application.gap)));
        }
        return super.add(comp);
    }

}
