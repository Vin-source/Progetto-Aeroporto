package model;

import controller.Controller;
import gui.Ospite;

import javax.swing.*;

/**
 * La classe main.jqvq
 */
public class Main {
    /**
     * La classe è responsabile dell'avvio dell'applicazione
     * Crea la prima istanza della GUI {@link gui.Ospite Ospite}
     *
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            gui.Ospite ospite = new Ospite(controller);
        });
    }
}
