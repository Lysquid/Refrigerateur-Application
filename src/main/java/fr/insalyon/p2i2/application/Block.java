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
import javax.swing.UIManager;

public class Block extends JPanel {

    private static final Font font = UIManager.getFont("h2.font");

    public Block(String title, JComponent grid) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        JLabel label = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        addSpace();
        // add(new JButton("Bouton"));
        add(grid);

    }

    public Block(String title, JComponent grid, JComponent button) {
        this(title, grid);
        addSpace(Application.gap);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(button);
    }

    private void addSpace(int size) {
        add(Box.createRigidArea(new Dimension(0, size)));
    }

    private void addSpace() {
        addSpace(Application.gap / 2);
    }

    @Override
    public Insets getInsets() {
        int inset = Application.gap / 2;
        return new Insets(inset, inset, inset, inset);
    }

}
