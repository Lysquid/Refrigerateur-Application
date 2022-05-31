package fr.insalyon.p2i2.application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Block extends JPanel {

    private final static Font font = Application.font.deriveFont(Font.PLAIN, 24);

    public Block(String title, JComponent grid) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

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
