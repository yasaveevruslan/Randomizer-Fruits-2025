package roboSimJava;

import javax.swing.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        Window.initializeImage();
        SwingUtilities.invokeLater(Window::new);

    }
}