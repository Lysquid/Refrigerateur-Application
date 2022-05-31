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
    protected static final Font italicFont = UIManager.getFont("defaultFont").deriveFont(Font.ITALIC);
    protected static final Font biggerFont = UIManager.getFont("large.font");
    protected static final int inset = Application.compoInset;
    protected static final int borderSize = Application.compoBorderSize;

    public Compo() {
        setBackground(Application.blockColor);
        setBorder(BorderFactory.createMatteBorder(borderSize, borderSize, borderSize, borderSize,
                Application.borderColor));
    }
}