package controller;

import dao.GateDAO;
import dao.UtenteDAO;
import implementazionePostgresDAO.UtenteImplementazionePostgresDAO;
import implementazionePostgresDAO.GateImplementazionePostgresDAO;
import model.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.LoginDAO;
import dao.VoloDAO;
import implementazionePostgresDAO.*;

public class Controller {
    private Amministratore amministratore;
    private Utente utente;

    private LoginDAO loginDAO;
    private VoloDAO voloDAO;
    private GateDAO gateDAO;

    public Controller() {
        // 1. Inizializziamo i DAO (Connessione al DB)
        this.loginDAO = new LoginImplementazionePostgresDAO();
        this.voloDAO = new VoloImplementazionePostgresDAO();
   //     this.gateDAO  =  new GateImplementazionePostgresDAO();

        this.amministratore = new Amministratore("TempId", "admin@gmail.com", "password");
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
      //  LoginDAO loginDAO = new LoginImplementazionePostgresDAO();
        if(loginDAO == null){
            return "errore";
        }

        String ruolo = loginDAO.getUtentiDB(username, password);

        if ("amministratore".equals(ruolo)) {
            this.amministratore = new Amministratore("ID_ADMIN", username, password);
            return "amministratore";
        } else if ("utente".equals(ruolo)) {
            this.utente = new Utente("ID_UTENTE", username, password);
            return "utente";
        }
        return "errore"; // Login fallito

    }

    public ArrayList<Volo> getTuttiVoli() {
        VoloDAO v = new VoloImplementazionePostgresDAO();
        ArrayList<Volo> voli;
        voli = v.getVoliDB();
        amministratore.setVoli(voli);
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

    public boolean effettuaPrenotazione(String codiceVolo, String nome, String cognome, String cid, String postoInAereo, int numeroBagagli) {
        UtenteDAO u = new UtenteImplementazionePostgresDAO();

        if(u.effettuaPrenotazioneDB(codiceVolo, nome, cognome, cid, postoInAereo, utente.getEmail(), numeroBagagli)){
            getTutteLePrenotazioni();
            return true;
        }

        return false;
    }

    public boolean modificaPrenotazione(String codiceVolo, String nome, String cognome, String cartaIdentita, Prenotazione p) {
        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        if(nome.isEmpty()) nome = p.getNome();
        if(cognome.isEmpty()) cognome = p.getCognome();
        if(cartaIdentita.isEmpty()) cartaIdentita = p.getCartaIdentita();

        u.modificaPrenotazioneDB(codiceVolo, nome, cognome, cartaIdentita, p.getIdPrenotazione());
        // dopo la successiva modifica della prenotazione non si aggiorna la visualizzazione della prenotazione con nuovi dati!


        return true;
    }

    public boolean cancellaPrenotazione(String idPrenotazione){
        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        return u.cancellaPrenotazioneDB(idPrenotazione);
    }

    public ArrayList<String> getPostiOccupati(String codiceVolo) {

        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        return u.getPostiOccupatiDB(codiceVolo);

    }

    public ArrayList<Volo> cercaVoli(String valore) {
        VoloDAO u = new VoloImplementazionePostgresDAO();
        ArrayList<Volo> voli = u.getVoliDB();

        if(voli != null){
            ArrayList<Volo> voliTrovati = new ArrayList<>();

            for (Volo v : voli) {
                if (v.getCompagniaAerea().toLowerCase().contains(valore.toLowerCase()) ||
                        v.getCodiceVolo().toLowerCase().contains(valore.toLowerCase())) {
                    voliTrovati.add(v);
                }
            }
            return voliTrovati;
        }
        return null;

    }

    public ArrayList<Volo> cercaVoliAmministratore(String valore) {
        if (valore == null || valore.isEmpty()) return amministratore.getVoli();

        ArrayList<Volo> filtrati;
        String valoreLowerCase = valore.toLowerCase();

        filtrati = amministratore.ricercaRapida(valoreLowerCase);
        return filtrati;
    }





    //CREAZIONE DI UN VOLO TESTING

    public Boolean creaNuovoVolo(String codiceVolo, String compagniaAerea, String origine, String destinazione,
                                 String data, String ora, String ritardo, String numeroGate) {
        try {
            if (!origine.equalsIgnoreCase("Napoli") && !destinazione.equalsIgnoreCase("Napoli")) {
                throw new Exception("Volo non valido: deve essere un arrivo o una partenza da Napoli.");
            }

            int ritardoParsed = Integer.parseInt(ritardo);
            int numeroGateParsed = Integer.parseInt(numeroGate);

            Volo volo = new Volo(codiceVolo, compagniaAerea, origine, destinazione, data, ora, ritardoParsed);
            volo.setGate(new Gate(numeroGateParsed));

            return voloDAO.inserisciVolo(volo);

        } catch (Exception e) {
            System.err.println("Errore creazione volo: " + e.getMessage());
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

        /*
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
        }*/
        try {
            ArrayList<Volo> tuttiVoli = getTuttiVoli();
            Volo voloDaAggiornare = null;
            for (Volo v : tuttiVoli) {
                if (v.getCodiceVolo().equals(codiceVolo)) {
                    voloDaAggiornare = v;
                    break;
                }
            }

            if (voloDaAggiornare == null) {
                return false;
            };


            voloDaAggiornare.setData(nuovaData);
            voloDaAggiornare.setOrarioPrevisto(nuovoOrario);
            voloDaAggiornare.setRitardo(Integer.parseInt(nuovoRitardo));


            if (nuovoNumeroGateS.equals("Gate non assegnato")) {
                int nuovoNumeroGateInt = Integer.parseInt(nuovoNumeroGateS);
                voloDaAggiornare.setGate(new Gate(nuovoNumeroGateInt));
            }

            return voloDAO.aggiornaVolo(voloDaAggiornare);

        } catch (Exception e) {
            System.err.println("Errore aggiornamento: " + e.getMessage());
            return false;
        }
    }

/*
    public ArrayList<String> getGateDisponibili() {
      /*
        ArrayList<String> gates = new ArrayList<>();
        // Simuliamo 20 gate
        for (int i = 1; i <= 9; i++) {
            gates.add(String.valueOf(i));
        }
        return gates;


        GateDAO g = new GateImplementazionePostgresDAO();
        ArrayList<String> gateDisponibili = new ArrayList<>();
        gateDisponibili = g.getTuttiGate();
        return gateDisponibili;
    }*/

    public ArrayList<String> getGateDisponibili() {
        ArrayList<String> gatesString = new ArrayList<>();



        if (gateDAO == null) {
            return gatesString;
        }

        try {
            ArrayList<Gate> gatesDalDB = gateDAO.getTuttiGate();
            for (Gate g : gatesDalDB) {
                gatesString.add(String.valueOf(g.getNumero()));
            }

        } catch (SQLException e) {
            System.err.println("Errore recupero gate dal DB: " + e.getMessage());
        }

        return gatesString;
    }


    public boolean modificaGate(String codiceVolo, String nuovoGateStr) {
        try {
            if (nuovoGateStr == null || nuovoGateStr.isEmpty()) {
                return false;
            }
            int nuovoGateNum = Integer.parseInt(nuovoGateStr);

            if (gateDAO == null) return false;

            boolean risultatoDB = gateDAO.modificaGate(codiceVolo, nuovoGateNum);

            if (risultatoDB) {
                for (Volo v : this.amministratore.getVoli()) {
                    if (v.getCodiceVolo().equals(codiceVolo)) {
                        if (v.getGate() != null) {
                            v.getGate().setNumero(nuovoGateNum);
                        } else {
                            v.setGate(new Gate(nuovoGateNum));
                        }
                        break;
                    }
                }
            }

            return risultatoDB;

        } catch (NumberFormatException e) {
            System.err.println("Errore: Il gate deve essere un numero intero.");
            return false;
        } catch (SQLException e) {
            System.err.println("Errore SQL durante la modifica del gate: " + e.getMessage());
            return false;
        }
    }


    public boolean assegnaGate(String codiceVolo, String numeroGateStr) {
        try {
            if (numeroGateStr == null || numeroGateStr.isEmpty()) {
                return false;
            }
            int numeroGate = Integer.parseInt(numeroGateStr);

            Integer.parseInt(codiceVolo);

            if (gateDAO == null) {
                return false;
            }

            boolean risultatoDB = gateDAO.assegnaGate(codiceVolo, numeroGate);

            if (risultatoDB) {
                for (Volo v : this.amministratore.getVoli()) {
                    if (v.getCodiceVolo().equals(codiceVolo)) {
                        v.setGate(new Gate(numeroGate));
                        break;
                    }
                }
            }
            return risultatoDB;

        } catch (NumberFormatException e) {
            System.err.println("Errore: Sia il Gate che il Codice Volo devono essere numeri");
            return false;
        } catch (SQLException e) {
            System.err.println("Errore SQL: " + e.getMessage());
            return false;
        }
    }


    //serve per gestire i due casi di assegnamento di un gate ad un nuovo volo o la modifica di un gate ad un volo esistente
    public boolean salvaGate(String codiceVolo, String nuovoGateStr) {
        try {
            if (nuovoGateStr == null || nuovoGateStr.isEmpty()) return false;
            int nuovoGateNum = Integer.parseInt(nuovoGateStr);

            Volo voloCorrente = null;
            for (Volo v : getTuttiVoli()) {
                if (v.getCodiceVolo().equals(codiceVolo)) {
                    voloCorrente = v;
                    break;
                }
            }

            if (voloCorrente == null) return false;

            boolean esito = false;

            if (voloCorrente.getGate() != null) {
                esito = this.modificaGate(codiceVolo, nuovoGateStr);
            }
            else {
                esito = this.assegnaGate(codiceVolo, nuovoGateStr);
            }

            return esito;

        } catch (NumberFormatException e) {
            System.err.println("Il gate deve essere un numero");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
