package fr.insalyon.p2i2.application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Compo extends JPanel {

    protected static final Font mainFont = UIManager.getFont("defaultFont");
    protected static final Font boldFont = UIManager.getFont("semibold.font");
    protected static final Font smallFont = UIManager.getFont("small.font");
    protected static final Font biggerFont = UIManager.getFont("large.font");
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
