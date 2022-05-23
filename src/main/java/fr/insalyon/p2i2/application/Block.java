package fr.insalyon.p2i2.application;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Block extends JPanel {

    private final Color backgroundColor = Color.BLUE;

    public Block(String title, Grid grid)

    {

        setBackground(Color.CYAN);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        Font font = new Font(getFont().getFontName(), Font.PLAIN, 30);

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
