
    package com.thyrohealth;

import javax.swing.SwingUtilities;

import com.thyrohealth.controller.AppController;
import com.thyrohealth.view.MainFrame;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1) build the controller (loads & trains the Weka model)
            AppController controller = new AppController();
            // 2) create & show the Swing window
            MainFrame frame = new MainFrame(controller);
            frame.setVisible(true);
        });
    }
}

