package model;

import controller.Controller;
import gui.Ospite;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            gui.Ospite ospite = new Ospite(controller);
        });
    }
}
