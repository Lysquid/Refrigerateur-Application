package fr.insalyon.p2i2.application;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Image;


import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class Window extends JFrame {

    public Window() {
        lookAndFeel();
        setTitle("Albert le frigidaire");
        Image icon = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\fr\\insalyon\\p2i2\\flocon.png"); 
        setIconImage(icon);
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
