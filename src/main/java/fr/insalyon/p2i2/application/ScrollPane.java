package fr.insalyon.p2i2.application;

import java.awt.Component;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {

    public ScrollPane(Component view) {
        super(view);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(20);
        setOpaque(false);
        setBorder(null);
    }
}
