package model;

import gui.Ospite;
import controller.Controller;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        new gui.Ospite(controller);

        /*
        SwingUtilities.invokeLater(() -> {
            gui.Ospite ospite = new Ospite();
        });
        */

    }
}
