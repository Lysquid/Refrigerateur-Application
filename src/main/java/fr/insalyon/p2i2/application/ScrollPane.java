package fr.insalyon.p2i2.application;

import java.awt.Component;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {

    public ScrollPane(Component view) {
        super(view);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setOpaque(false);
        setBorder(null);
        getVerticalScrollBar().setUnitIncrement(20);

    }
}
