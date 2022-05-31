package fr.insalyon.p2i2.application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Compo extends JPanel {

    protected static final Font mainFont = Application.font.deriveFont(Font.PLAIN, 18);
    protected static final Font boldFont = Application.font.deriveFont(Font.BOLD, 18);
    protected static final Font smallFont = Application.font.deriveFont(Font.ITALIC, 17);
    protected static final Font biggerFont = Application.font.deriveFont(Font.PLAIN, 24);
    protected static final int inset = 10;
    protected static final int borderSize = 2;

    protected boolean fullWidth = false;

    public Compo(boolean fullWidth) {
        this.fullWidth = fullWidth;
        setBackground(Application.blockColor);
        setBorder(BorderFactory.createMatteBorder(borderSize, borderSize, borderSize, borderSize,
                Application.borderColor));
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
