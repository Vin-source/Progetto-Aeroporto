package controller;

import model.Amministratore;
import model.Prenotazione;
import model.Utente;
import model.Volo;

import java.util.ArrayList;

public class Controller {
    private Amministratore amministratore;
    private Utente utente;


    public Controller() {
    }

    public String getEmail() {
        if(this.utente != null) {
            return this.utente.getEmail();
        }else if(this.amministratore != null) {
            return this.amministratore.getEmail();
        }
        return null;
    }


    public String login(String username, String password) {
        // controlla utente e password nel db
        //  non ricordo se era l'id condiviso o diverse table
        utente = new Utente("sfsd", "asdsrew", "der00");
        return "utente";
    }

    public ArrayList<Volo> getTuttiVoli() {
        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
        return voli;
    }

    public ArrayList<Prenotazione> getTutteLePrenotazioni() {
        ArrayList<Prenotazione> p = new ArrayList<>();
        Prenotazione pr = new Prenotazione("sdf", "asd", "ert", "srwe");
        pr.setIdPrenotazione("sdfwer00");
        p.add(pr);

        utente.setPrenotazioni(p);
        // devo anche settare prenotazioni nel model utente
        return p;
    }

    public ArrayList<Prenotazione> ricercaPrenotazioni(String valore) {
        return utente.cercaPrenotazioni(valore);
    }

    public void effettuaPrenotazione(String codiceVolo, String nome, String cognome, String cid, String postoInAereo){
        // le chiavi esterne sono il codiceVolo e l'id(o email) utente preso dal controller
    }

    public boolean modificaPrenotazione(String codiceVolo, String nome, String cognome, String cartaIdentita, String idPrenotazione){
        return true;
    }

    public ArrayList<String> getPostiOccupati(String codiceVolo){
        ArrayList<String> postiOccupati =  new ArrayList<>();
        postiOccupati.add("A2");
        postiOccupati.add("A3");
        postiOccupati.add("A4");
        postiOccupati.add("E5");
        return postiOccupati;
    }

    public ArrayList<Volo> cercaVoli(String valore){
        // chiamata al db
        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
        return voli;
    }
}
