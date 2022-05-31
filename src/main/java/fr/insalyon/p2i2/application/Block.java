package fr.insalyon.p2i2.application;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.*;
import javax.swing.*;

public class Block extends JPanel {

    private final static Color backgroundColor = Color.CYAN;
    private final static Font font = Application.font.deriveFont(Font.PLAIN, 24);

    public Block(String title, JComponent grid) {

        setBackground(backgroundColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        addSpace();
        // add(new JButton("Bouton"));
        add(grid);

    }

    public Block(String title, JComponent grid, JComponent button) {
        this(title, grid);
        addSpace();
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(button);
    }

    private void addSpace() {
        add(Box.createRigidArea(new Dimension(0, Application.gap / 2)));
    }

    @Override
    public Insets getInsets() {
        int inset = Application.gap / 2;
        return new Insets(inset, inset, inset, inset);
    }

}
