package fr.insalyon.p2i2.application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;

public class Window extends JFrame {

    public Window() {
        setTitle("Albert le frigidaire");
        setSize(1600, 870);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new Application());
        setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
