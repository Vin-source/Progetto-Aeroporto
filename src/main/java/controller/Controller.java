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


    // --------------------------------- PRENOTAZIONE ------------------------------------------- //



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
    }catch (SQLException _){
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







    // --------------------------------- VOLO ------------------------------------------- //




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



    public ArrayList<Volo> cercaVoliAmministratore(String valore) {
        if (valore == null || valore.isEmpty()) return amministratore.getVoli();

        ArrayList<Volo> filtrati;
        String valoreLowerCase = valore.toLowerCase();

        filtrati = amministratore.ricercaRapida(valoreLowerCase);
        return filtrati;
    }





    public String creaNuovoVolo(String compagniaAerea, String origine, String destinazione,
                                 String data, String ora, String ritardo, String numeroGate) {
        try {

            int ritardoParsed = Integer.parseInt(ritardo);

            Volo volo = new Volo("00", compagniaAerea, origine, destinazione, data, ora, ritardoParsed);
            volo.setAmministratore(new Amministratore("00", amministratore.getEmail(), amministratore.getPassword()));


            String nuovoCodiceVolo = voloDAO.inserisciVolo(volo, numeroGate);

            if(numeroGate != null){
                int numeroGateParsed = Integer.parseInt(numeroGate);
                gateDAO.assegnaGate(nuovoCodiceVolo, numeroGateParsed);
            }

            getTuttiVoli();
            return "Volo inserito con successo!";
        } catch (SQLException e) {
            return "Errore del server durante la creazione del volo";
        }catch(Exception e){
            return "Errore nel sistema";
        }
    }




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


            if(nuovoNumeroGateS == null){
                voloDaAggiornare.setGate(null);
                int codiceVoloInt = Integer.parseInt(codiceVolo);
                gateDAO.setCodiceVoloNull(codiceVoloInt);
            }else if (!(nuovoNumeroGateS.equals("Gate non assegnato"))){
                int numeroGate = Integer.parseInt(nuovoNumeroGateS);
                voloDaAggiornare.setGate(new Gate(numeroGate));
                int codiceVoloInt = Integer.parseInt(codiceVolo);
                gateDAO.setCodiceVoloNull(codiceVoloInt);
                gateDAO.assegnaGate(codiceVolo, numeroGate);
            }


            voloDAO.aggiornaVolo(voloDaAggiornare);
            return "Volo aggiornato con successo!";

        } catch (SQLException e) {
            return "Errore del server durante l'aggiornamento del volo";
        } catch(Exception e){
            return "Errore nel sistema";
        }
    }


    public ArrayList<String> getGateDisponibili() {
        ArrayList<String> gatesString = null;


        try {
            gatesString = gateDAO.getGateDisponibiliDAO();

        } catch (SQLException e) {
            return null;
        }

        return gatesString;
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

            if(gate != null){
                gateDAO.setCodiceVoloNull(Integer.parseInt(codiceVolo));
            }

            getTuttiVoli();
            return "Volo cancellato correttamente!";

        }catch (SQLException e){
            return "Errore nel server durante l'eliminazione del volo";
        }
    }
}
