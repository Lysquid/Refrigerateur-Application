package fr.insalyon.p2i2.application;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import net.miginfocom.layout.*;

public class Block extends JPanel {

    private final Color backgroundColor = Color.BLUE;

    public Block(String title, Component grid)

    {

        setBackground(Color.CYAN);
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setLayout(new MigLayout(
                new LC().wrapAfter(1),
                new AC().fill().grow(),
                new AC().fill()));

        Font font = new Font(null, Font.PLAIN, 30);

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label, new CC().shrinkY(100f));

        // add(Box.createRigidArea(new Dimension(0, 20)));

        // add(new JButton("Bouton"));
        add(grid, new CC().grow());

    }

    @Override
    public Insets getInsets() {
        int insets = 20;
        return new Insets(insets, insets, insets, insets);
    }

}
