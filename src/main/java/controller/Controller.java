package controller;

import dao.*;
import implementazionePostgresDAO.UtenteImplementazionePostgresDAO;
import implementazionePostgresDAO.GateImplementazionePostgresDAO;
import model.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import implementazionePostgresDAO.*;

import javax.swing.*;

public class Controller {
    private Amministratore amministratore;
    private Utente utente;

    private LoginDAO loginDAO;
    private VoloDAO voloDAO;
    private GateDAO gateDAO;
    private UtenteDAO utenteDAO;
    private PrenotazioneDAO prenotazioneDAO;

    public Controller() {
        this.loginDAO = new LoginImplementazionePostgresDAO();
        this.voloDAO = new VoloImplementazionePostgresDAO();
        this.gateDAO  =  new GateImplementazionePostgresDAO();
        this.utenteDAO = new UtenteImplementazionePostgresDAO();
        this.prenotazioneDAO = new PrenotazioneImplementazionePostgresDAO();

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
        String ruolo;
        try{
            ruolo = loginDAO.getUtentiDB(username, password);

            if ("amministratore".equals(ruolo)) {
                this.amministratore = new Amministratore("ID_ADMIN", username, password);
                return "amministratore";
            } else if ("utente".equals(ruolo)) {
                this.utente = new Utente("ID_UTENTE", username, password);
                return "utente";
            }
        }catch(SQLException e){
            return "Errore durante l'accesso al sistema";
        }

        return ruolo;
    }

    public ArrayList<Volo> getTuttiVoli() {
        try{
            ArrayList<Volo> voli;
            voli = voloDAO.getVoliDB();
            if(amministratore != null){
                amministratore.setVoli(voli);
            }
            return voli;
        }catch(SQLException e){
            System.err.println("Errore SQL durante il caricamento dei voli: ");
        }

        return null;
    }

    public ArrayList<Prenotazione> getTutteLePrenotazioni() {
        try{
            ArrayList<Prenotazione> p;


            p = utenteDAO.getPrenotazioniDB(utente.getEmail());

            utente.setPrenotazioni(p);
            // Salvo le prenotazioni ottenute nell'oggetto utente
            return p;
        }catch (SQLException e){
            System.err.println("Errore SQL durante il caricamento delle prenotazioni utente");
        }
        return null;
    }

    public ArrayList<Prenotazione> ricercaPrenotazioni(String valore) {
        ArrayList<Prenotazione> p;

        p = utente.cercaPrenotazioni(valore);

        return p;
    }

    public String effettuaPrenotazione(String codiceVolo, String nome, String cognome, String cid, String postoInAereo, int numeroBagagli) {

    try{
        if(prenotazioneDAO.effettuaPrenotazioneDB(codiceVolo, nome, cognome, cid, postoInAereo, utente.getEmail(), numeroBagagli)){
            getTutteLePrenotazioni();
            return "Prenotazione effettuata!";
        }
    }catch (SQLException e){
        return "Errore nella connessione al server";
    }
        return "Errore: Prenotazione non effettuata correttamente";
    }

    public String modificaPrenotazione(String codiceVolo, String nome, String cognome, String cartaIdentita,String nuovoPostoScelto, Prenotazione p) {

        if(nome.isEmpty()) nome = p.getNome();
        if(cognome.isEmpty()) cognome = p.getCognome();
        if(cartaIdentita.isEmpty()) cartaIdentita = p.getCartaIdentita();
        if(nuovoPostoScelto.isEmpty()) nuovoPostoScelto = p.getPostoAssegnato();

        try{
            prenotazioneDAO.modificaPrenotazioneDB(codiceVolo, nome, cognome, cartaIdentita, p.getIdPrenotazione(), nuovoPostoScelto);
        }catch (SQLException e){
            return "Errore nel server durante la modifica della prenotazione";
        }

        return "Prenotazione modificata correttamente!";
    }

    public String cancellaPrenotazione(String idPrenotazione){
        try{
            prenotazioneDAO.cancellaPrenotazioneDB(idPrenotazione);
            return "Prenotazione cancellata Correttamente!";
        }catch (SQLException e){
            return "Errore nel server durante la cancellazione della prenotazione";
        }
    }

    public ArrayList<String> getPostiOccupati(String codiceVolo) {

        try{
            return prenotazioneDAO.getPostiOccupatiDB(codiceVolo);
        }catch (SQLException e){
            System.err.println("Errore SQL durante la ricerca dei posti occupati");
        }
        return null;
    }

    public ArrayList<Volo> cercaVoli(String valore) {

        try{
            ArrayList<Volo> voli = voloDAO.getVoliDB();

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
        } catch (SQLException e) {
            System.err.println("Errore SQL durante la ricerca volo");
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

    public String creaNuovoVolo(String compagniaAerea, String origine, String destinazione,
                                 String data, String ora, String ritardo, String numeroGate) {
        try {

            int ritardoParsed = Integer.parseInt(ritardo);
            int numeroGateParsed = !(numeroGate == null) ? Integer.parseInt(numeroGate) : 0;

            Volo volo = new Volo("00", compagniaAerea, origine, destinazione, data, ora, ritardoParsed);
            volo.setGate(new Gate(numeroGateParsed));
            volo.setAmministratore(new Amministratore("00", amministratore.getEmail(), amministratore.getPassword()));

            voloDAO.inserisciVolo(volo);

            return "Volo inserito con successo!";
        } catch (SQLException e) {
            return "Errore del server durante la creazione del volo";
        }catch(Exception e){
            return "Errore nel sistema";
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

    public String aggiornaVolo(String codiceVolo, String nuovaData, String nuovoOrario,
                                String nuovoRitardo, String nuovoNumeroGateS) {

        try {
            ArrayList<Volo> tuttiVoli = getTuttiVoli();
            Volo voloDaAggiornare = null;
            for (Volo v : tuttiVoli) {
                if (v.getCodiceVolo().equals(codiceVolo)) {
                    voloDaAggiornare = v;
                    break;
                }
            }


            voloDaAggiornare.setData(nuovaData);
            voloDaAggiornare.setOrarioPrevisto(nuovoOrario);
            voloDaAggiornare.setRitardo(Integer.parseInt(nuovoRitardo));


            if (!nuovoNumeroGateS.equals("Gate non assegnato")) {
                int nuovoNumeroGateInt = Integer.parseInt(nuovoNumeroGateS);
                if(voloDaAggiornare.getGate() == null){
                    voloDaAggiornare.setGate(new Gate(nuovoNumeroGateInt));
                }else{
                    voloDaAggiornare.getGate().setNumero(nuovoNumeroGateInt);
                }
            }

            return voloDAO.aggiornaVolo(voloDaAggiornare);
        } catch (SQLException e) {
            return "Errore del server durante l'aggiornamento del volo";
        } catch(Exception e){
            return "Errore nel sistema";
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


    //serve per gestire i due casi di assegnamento di un gate ad un volo che non lo ha o la modifica di un gate ad un volo esistente
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

            if (voloCorrente == null) {
                return false;
            }

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

    public Volo getVoloByCodice(String codiceVolo) {
        if (voloDAO == null) {
            return null;
        }

        try {
            return voloDAO.getVoloByCodice(codiceVolo);

        } catch (SQLException e) {
            System.err.println("Errore recupero volo " + codiceVolo + ": " + e.getMessage());
            return null;
        }
    }

    public String eliminaVolo(String codiceVolo, Gate gate) {
        if (voloDAO == null) {
            return "Errore di sistema";
        }

        try {
            ArrayList<Integer> prenotazioni = voloDAO.eliminaVolo(codiceVolo, gate);
            for(Integer i : prenotazioni) {
                prenotazioneDAO.cancellaPrenotazioneDB(String.valueOf(i));
            }

            ArrayList<Volo> listaVoli = this.amministratore.getVoli();

            for (int i = 0; i < listaVoli.size(); i++) {
                if (listaVoli.get(i).getCodiceVolo().equals(codiceVolo)) {
                    listaVoli.get(i).setStatoVolo(StatoVolo.CANCELLATO);
                    break;
                }
            }
            return "Volo cancellato correttamente!";

        }catch (SQLException e){
            return "Errore nel server durante l'eliminazione del volo";
        }
    }
}
