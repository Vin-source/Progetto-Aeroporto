package model;

import gui.Ospite;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gui.Ospite ospite = new Ospite();
        });
    }
}
