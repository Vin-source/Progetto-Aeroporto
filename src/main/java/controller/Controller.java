package controller;

import dao.UtenteDAO;
import implementazionePostgresDAO.UtenteImplementazionePostgresDAO;
import model.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.LoginDAO;
import implementazionePostgresDAO.LoginImplementazionePostgresDAO;

public class Controller {
    private Amministratore amministratore;
    private Utente utente;



    public Controller() {
    }

    public String getEmail() {
        if (this.utente != null) {
            return this.utente.getEmail();
        } else if (this.amministratore != null) {
            return this.amministratore.getEmail();
        }
        return null;
    }


    public String login(String username, String password){
        LoginDAO loginDAO = new LoginImplementazionePostgresDAO();

        String ruolo = loginDAO.getUtentiDB(username, password);

        if(ruolo.equals("amministratore")){
            amministratore = new Amministratore("456", username, password);
            return "amministratore";
        }else if(ruolo.equals("utente")) {
            utente = new Utente("123", username, password);
            return "utente";
        }
        return null;
        //da continuare
    }

    public ArrayList<Volo> getTuttiVoli() {
        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        ArrayList<Volo> voli;

        voli = u.getVoliDB();

        return voli;
    }

    public ArrayList<Prenotazione> getTutteLePrenotazioni() {
        ArrayList<Prenotazione> p;

        UtenteDAO u = new UtenteImplementazionePostgresDAO();

        p = u.getPrenotazioniDB(utente.getEmail());

        utente.setPrenotazioni(p);
        // Salvo le prenotazioni ottenute nell'oggetto utente
        return p;
    }

    public ArrayList<Prenotazione> ricercaPrenotazioni(String valore) {
        ArrayList<Prenotazione> p;

        p = utente.cercaPrenotazioni(valore);

        return p;
    }

    public boolean effettuaPrenotazione(String codiceVolo, String nome, String cognome, String cid, String postoInAereo) {
        UtenteDAO u = new UtenteImplementazionePostgresDAO();

        if(u.effettuaPrenotazioneDB(codiceVolo, nome, cognome, cid, postoInAereo)){
            return true;
        }
        return false;
    }

    public boolean modificaPrenotazione(String codiceVolo, String nome, String cognome, String cartaIdentita, String idPrenotazione) {
        return true;
    }

    public ArrayList<String> getPostiOccupati(String codiceVolo) {
        ArrayList<String> postiOccupati = new ArrayList<>();
        postiOccupati.add("A2");
        postiOccupati.add("A3");
        postiOccupati.add("A4");
        postiOccupati.add("E5");
        return postiOccupati;
    }

    public ArrayList<Volo> cercaVoli(String valore) {
        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        ArrayList<Volo> voli = u.getVoliDB();
        ArrayList<Volo> voliTrovati = new ArrayList<>();

        for(Volo v : voli){
            if(v.getCompagniaAerea().toLowerCase().contains(valore.toLowerCase()) ||
                    v.getCodiceVolo().toLowerCase().contains(valore.toLowerCase())){
                voliTrovati.add(v);
            }
        }

        return voliTrovati;
    }


    //CREAZIONE DI UN VOLO TESTING

    public Boolean creaNuovoVolo(String codiceVolo, String compagniaAerea, String origine, String destinazione,
                                 String data, String ora, String ritardo, String numeroGate) {
        try {
            if (!origine.equalsIgnoreCase("Napoli") && !destinazione.equalsIgnoreCase("Napoli")) {
                throw new Exception("Volo non valido: deve essere un arrivo o una partenza da Napoli.");
            }

            ArrayList<Volo> voli = new ArrayList<>();
            voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
            voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
            this.amministratore.setVoli(voli);

            int ritardoParsed = Integer.parseInt(ritardo);
            int numeroGateParsed = Integer.parseInt(numeroGate);

            Volo volo = new Volo(codiceVolo, compagniaAerea, origine, destinazione, data, ora, ritardoParsed);

            volo.setGate(new Gate(numeroGateParsed));

            this.amministratore.getVoli().add(volo);

            return true;

        } catch (Exception e) {
            System.err.println("Errore creazione volo (intercettato da Controller): " + e.getMessage());
            return false;
        }
    }


    //RICERCA DEI VOLI TESTING
/*    public ArrayList<Volo> cercaVoli(String testoRicerca) {
        ArrayList<Volo> voliFiltrati = new ArrayList<>();
        String ricercaLower = testoRicerca.toLowerCase();


        if (ricercaLower.isEmpty()) {
            return getTuttiVoli();
        }

        for (Volo volo : this.amministratore.getVoli()) {
            boolean origineTrovata = volo.getOrigine() != null && volo.getOrigine().toLowerCase().contains(ricercaLower);
            boolean destinazioneTrovata = volo.getDestinazione() != null && volo.getDestinazione().toLowerCase().contains(ricercaLower);

            if (volo.getCodiceVolo().toLowerCase().contains(ricercaLower) ||
                    volo.getCompagniaAerea().toLowerCase().contains(ricercaLower) ||
                    origineTrovata || destinazioneTrovata)
            {
                voliFiltrati.add(volo);
            }
        }
        return voliFiltrati;
    }



 */

    public Boolean aggiornaVolo(String codiceVolo, String nuovaData, String nuovoOrario,
                                String nuovoRitardo, String nuovoNumeroGateS) {

        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
        this.amministratore.setVoli(voli);

        try {
            Volo voloDaAggiornare = null;
            for (Volo v : this.amministratore.getVoli()) {
                if (v.getCodiceVolo().equals(codiceVolo)) {
                    voloDaAggiornare = v;
                    break;
                }
            }

            if (voloDaAggiornare == null) {
                return false;
            }

            voloDaAggiornare.setData(nuovaData);
            voloDaAggiornare.setOrarioPrevisto(nuovoOrario);
            voloDaAggiornare.setRitardo(Integer.parseInt(nuovoRitardo));

            // 3. Gestisci l'aggiornamento del Gate
            if(!nuovoNumeroGateS.equals("Gate non assegnato")) {
                int nuovoNumeroGateInt = Integer.parseInt(nuovoNumeroGateS);
                if (voloDaAggiornare.getGate() != null) {
                    voloDaAggiornare.getGate().setNumero(nuovoNumeroGateInt);
                }
            }

            return true;

        } catch (NumberFormatException e) {
            System.err.println("Errore aggiornamento: " + e.getMessage());
            return false;
        }
    }


    public ArrayList<String> getGateDisponibili() {
        ArrayList<String> gates = new ArrayList<>();
        // Simuliamo 20 gate
        for (int i = 1; i <= 9; i++) {
            gates.add(String.valueOf(i));
        }
        return gates;
    }
}
