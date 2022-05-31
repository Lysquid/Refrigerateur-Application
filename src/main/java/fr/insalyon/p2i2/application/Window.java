package fr.insalyon.p2i2.application;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

public class Window extends JFrame {

    public Window() {
        lookAndFeel();
        setTitle("Albert le frigidaire");
        setSize(1600, 870);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new Application());
        setVisible(true);

    }

    private void lookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

}
