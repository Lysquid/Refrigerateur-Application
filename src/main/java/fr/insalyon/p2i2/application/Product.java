package fr.insalyon.p2i2.application;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import net.miginfocom.layout.*;

public class Product extends Compo {

    private final int size = 130;

    public Product() {
        // setPreferredSize(new Dimension(0, 0));
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setLayout(new MigLayout(
                new LC().wrapAfter(1),
                new AC(),
                new AC()));

        JLabel label1 = new JLabel("Nom du produit");
        label1.setFont(font);
        add(label1);

        JLabel label2 = new JLabel("Marque");
        label2.setFont(font);
        add(label2);

        JLabel label3 = new JLabel("Marque");
        label3.setFont(font);
        add(label3);

    }

}
