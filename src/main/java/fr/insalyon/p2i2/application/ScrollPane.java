package fr.insalyon.p2i2.application;

import javax.swing.JScrollPane;
import java.awt.*;

public class ScrollPane extends JScrollPane {

    public ScrollPane(Component view) {
        super(view);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setOpaque(false);
        setBorder(null);
        getVerticalScrollBar().setUnitIncrement(14);

    }
}
