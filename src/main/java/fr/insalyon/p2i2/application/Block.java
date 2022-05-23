package fr.insalyon.p2i2.application;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.*;

public class Block extends JPanel {

    private final Color backgroundColor = Color.BLUE;

    public Block()

    {

        setBackground(Color.CYAN);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        Font font = new Font(getFont().getFontName(), Font.PLAIN, 30);

        JLabel label = new JLabel("Titre", SwingConstants.CENTER);
        label.setFont(font);
        add(label);

        // add(new JButton("Bouton"));
        add(new Grid(2, 2));

    }

    @Override
    public Insets getInsets() {
        int insets = 20;
        return new Insets(insets, insets, insets, insets);
    }

}
