package fr.insalyon.p2i2.application;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {

    private final int MIN_WIDTH = 960;
    private final int MAX_WIDTH = 540;

    public Window() {
        setTitle("Albert le frigidaire");
        setMinimumSize(new Dimension(MIN_WIDTH, MAX_WIDTH));
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new Application());
        setVisible(true);
    }

}
