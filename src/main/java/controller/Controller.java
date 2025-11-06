package controller;

import model.Amministratore;
import model.Utente;
import model.Volo;

import java.util.ArrayList;

public class Controller {
    private Amministratore amministratore;
    private Utente utente;


    public Controller() {

    }


    public String login(String username, String password) {
        // controlla utente e password nel db
        //  non ricordo se era l'id condiviso o diverse table
        return "utente";
    }

    public ArrayList<Volo> getTuttiVoli() {
        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
        return voli;
    }
}
