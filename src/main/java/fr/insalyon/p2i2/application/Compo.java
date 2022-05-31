package fr.insalyon.p2i2.application;

import javax.swing.JPanel;
import java.awt.*;

public class Compo extends JPanel {

    protected static final Font mainFont = Application.font.deriveFont(Font.PLAIN, 18);
    protected static final Font boldFont = Application.font.deriveFont(Font.BOLD, 18);
    protected static final Font smallFont = Application.font.deriveFont(Font.ITALIC, 17);
    protected static final Font biggerFont = Application.font.deriveFont(Font.PLAIN, 24);
    protected static final int inset = 10;

    protected int width;
    protected int height = 0;
    protected static final Color color = Color.LIGHT_GRAY;

    public Compo() {
        setBackground(color);
    }

    public Compo(int width) {
        this();
        this.width = width;
    }

    public Compo(int width, int height) {
        this(width);
        this.height = height;
    }

    @Override
    public Dimension getMaximumSize() {
        if (height == 0) {
            return new Dimension(width, super.getMaximumSize().height);
        } else {
            return new Dimension(width, height);
        }
    }

}
