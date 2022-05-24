package fr.insalyon.p2i2.application;

import javax.swing.JPanel;
import java.awt.*;

public class Compo extends JPanel {

    protected Font font = new Font(null, Font.PLAIN, 18);

    public Compo() {
        setBackground(Color.GRAY);

    }

    public void setMySize(int w, int h) {
        Dimension size = new Dimension(w, h);
        setPreferredSize(size);
        setMaximumSize(size);
    }

}
