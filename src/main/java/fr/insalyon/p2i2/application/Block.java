package fr.insalyon.p2i2.application;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.*;
import javax.swing.*;

public class Block extends JPanel {

    private final static Color backgroundColor = Color.CYAN;
    private final static Font font = Application.font.deriveFont(Font.PLAIN, 30);

    public Block(String title, Component grid) {

        setBackground(backgroundColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);

        add(Box.createRigidArea(new Dimension(0, 20)));

        // add(new JButton("Bouton"));
        add(grid);

    }

    @Override
    public Insets getInsets() {
        int insets = 20;
        return new Insets(insets, insets, insets, insets);
    }

}
