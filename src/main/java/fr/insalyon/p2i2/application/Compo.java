package fr.insalyon.p2i2.application;

import javax.swing.JPanel;
import java.awt.*;

public class Compo extends JPanel {

    protected static final Font mainFont = Application.font.deriveFont(Font.PLAIN, 18);
    protected static final Font boldFont = Application.font.deriveFont(Font.BOLD, 18);
    protected static final Font smallFont = Application.font.deriveFont(Font.ITALIC, 17);
    protected static final Font biggerFont = Application.font.deriveFont(Font.PLAIN, 24);
    protected static final int inset = 10;

    protected boolean fullWidth = false;
    protected static final Color color = Color.LIGHT_GRAY;

    public Compo(boolean fullWidth) {
        this.fullWidth = fullWidth;
        setBackground(color);
    }

    @Override
    public Dimension getMaximumSize() {
        if (fullWidth) {
            return new Dimension(getParent().getWidth(), super.getMaximumSize().height);
        } else {
            return super.getMaximumSize();
        }
    }

}
