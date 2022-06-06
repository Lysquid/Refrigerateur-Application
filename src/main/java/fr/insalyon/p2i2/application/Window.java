package fr.insalyon.p2i2.application;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class Window extends JFrame {

    public Window() {
        lookAndFeel();
        setTitle("Albert le frigidaire");
        setIconImage(Toolkit.getDefaultToolkit().getImage("./fichiers/img/flocon.png"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 450));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new Application());
        setVisible(true);

    }

    private void lookAndFeel() {
        try {
            FlatLaf.registerCustomDefaultsSource("fr.insalyon.p2i2.themes");
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

}
