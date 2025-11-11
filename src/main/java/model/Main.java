package model;

import controller.Controller;
import gui.Ospite;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        new gui.Ospite(controller);
    }
}
