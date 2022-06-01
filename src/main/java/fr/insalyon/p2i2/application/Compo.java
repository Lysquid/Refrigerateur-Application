package fr.insalyon.p2i2.application;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;

public class Compo extends JPanel {

    protected static final Font mainFont = UIManager.getFont("defaultFont");
    protected static final Font boldFont = UIManager.getFont("semibold.font");
    protected static final Font italicFont = UIManager.getFont("defaultFont").deriveFont(Font.ITALIC);
    protected static final Font biggerFont = UIManager.getFont("large.font");
    protected static final Font smallFont = UIManager.getFont("mini.font");
    protected static final int inset = Application.compoInset;

    public Compo() {
        setBackground(Application.blockColor);
        putClientProperty(FlatClientProperties.STYLE_CLASS, "myRoundPanel");
    }
}