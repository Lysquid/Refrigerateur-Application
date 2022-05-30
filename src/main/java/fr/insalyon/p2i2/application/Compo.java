package fr.insalyon.p2i2.application;

import javax.swing.JPanel;
import java.awt.*;

public class Compo extends JPanel {

    protected static final Font mainFont = new Font(Application.fontName, Font.PLAIN, 18);
    protected static final Font boldFont = new Font(Application.fontName, Font.BOLD, 18);
    protected static final Font smallFont = new Font(Application.fontName, Font.ITALIC, 17);
    protected static final Font biggerFont = new Font(Application.fontName, Font.PLAIN, 24);
    protected static final int inset = 10;

    public Compo() {
        setBackground(Color.LIGHT_GRAY);

    }

    public void setMySize(int w, int h) {
        Dimension size = new Dimension(w, h);
        setPreferredSize(size);
        setMaximumSize(size);
    }

}
