package controller;

import dao.*;
import implementazionePostgresDAO.UtenteImplementazionePostgresDAO;
import implementazionePostgresDAO.GateImplementazionePostgresDAO;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

import implementazionePostgresDAO.*;


/**
 * Classe Controller dell'applicazione.
 * Fa da ponte tra la GUI e il Model/DAO.
 * Gestisce l'autenticazione degli utenti.
 * Gestisce le operazioni sui voli.
 * Gestisce le prenotazioni degli utenti.
 */
public class Controller {
    private Amministratore amministratore;
    private Utente utente;

    private LoginDAO loginDAO;
    private VoloDAO voloDAO;
    private GateDAO gateDAO;
    private UtenteDAO utenteDAO;
    private PrenotazioneDAO prenotazioneDAO;

    /**
     * Costruisce il Controller e inizializza i DAO per la comunicazione con il database.
     */
    public Controller() {
        this.loginDAO = new LoginImplementazionePostgresDAO();
        this.voloDAO = new VoloImplementazionePostgresDAO();
        this.gateDAO  =  new GateImplementazionePostgresDAO();
        this.utenteDAO = new UtenteImplementazionePostgresDAO();
        this.prenotazioneDAO = new PrenotazioneImplementazionePostgresDAO();

    }

    /**
     * Restituisce l'email dell'ospite loggato
     *
     * @return L'email dell'utente/amministratore
     */
    public String getEmail() {
        if (this.utente != null) {
            return this.utente.getEmail();
        } else if (this.amministratore != null) {
            return this.amministratore.getEmail();
        }
        return null;
    }


    /**
     * Permette all'ospite di effettuare la login verificando i dati nel db
     *
     * @param username L'email inserita dall'ospite
     * @param password La password inserita dall'ospite
     * @return Il ruolo (Utente/Amministratore)
     */
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


    /**
     * Recupera dal database tutte le prenotazioni associate all'utente loggato.
     *
     * @return L'Arraylist contenente tutte le prenotazioni
     */
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

    /**
     * Effettua la ricerca delle prenotazioni dell'utente
     *
     * @param valore Il valore da cercare
     * @return L'Arraylist con le prenotazioni ricercate
     */
    public ArrayList<Prenotazione> ricercaPrenotazioni(String valore) {
        ArrayList<Prenotazione> p;

        p = utente.cercaPrenotazioni(valore);

        return p;
    }

    /**
     * Effettua una nuova prenotazione per uno specifico volo
     *
     * @param codiceVolo    Il codice volo del volo da prenotare
     * @param nome          Il nome del passeggero
     * @param cognome       Il cognome del passeggero
     * @param cid           Il documento d'identità
     * @param postoInAereo  Il posto selezionato in aereo
     * @param numeroBagagli Il numero dei bagagli
     * @param pesoTotaleDeiBagagli il peso totale dei bagagli inseriti
     * @return L'esito della prenotazione
     */
    public String effettuaPrenotazione(String codiceVolo, String nome, String cognome, String cid, String postoInAereo, int numeroBagagli, String pesoTotaleDeiBagagli) {

    try{
        if(prenotazioneDAO.effettuaPrenotazioneDB(codiceVolo, nome, cognome, cid, postoInAereo, utente.getEmail(), numeroBagagli, pesoTotaleDeiBagagli)){
            getTutteLePrenotazioni();
            return "Prenotazione effettuata!";
        }
    }catch (SQLException _){
        return "Errore nella connessione al server";
    }
        return "Errore: Prenotazione non effettuata correttamente";
    }

    /**
     * Modifica i dati di una prenotazione.
     *
     * @param codiceVolo       Il codice volo
     * @param nome             Il nome del passeggero
     * @param cognome          Il cognome del passeggero
     * @param cartaIdentita    La carta identita del passeggero
     * @param nuovoPostoScelto Il posto scelto
     * @param p                L'oggetto prenotazione
     * @return Il risultato dell'operazione
     */
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

    /**
     * Cancella una prenotazione dal db.
     *
     * @param idPrenotazione L'id della  prenotazione da cancellare
     * @return Il risultato dell'operazione
     */
    public String cancellaPrenotazione(String idPrenotazione){
        try{
            prenotazioneDAO.cancellaPrenotazioneDB(idPrenotazione);
            return "Prenotazione cancellata Correttamente!";
        }catch (SQLException e){
            return "Errore nel server durante la cancellazione della prenotazione";
        }
    }

    /**
     * Recupera la lista dei voli occupati di un volo
     *
     * @param codiceVolo Il codice volo
     * @return i posti occupati
     */
    public ArrayList<String> getPostiOccupati(String codiceVolo) {

        try{
            return prenotazioneDAO.getPostiOccupatiDB(codiceVolo);
        }catch (SQLException e){
            System.err.println("Errore SQL durante la ricerca dei posti occupati");
        }
        return null;
    }

    /**
     * Ricerca un volo nel db in base ad un valore.
     *
     * @param valore Il valore da cercare (codiceVolo o compagnia)
     * @return L'Arraylist contenente i voli trovati
     */
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


    /**
     * Recupera l'elenco di tutti i voli.
     *
     * @return L'Arraylist voli
     */
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


    /**
     * Ricerca i voli nella finestra Amministratore
     *
     * @param valore il valore del volo
     * @return L'Arraylist dei voli trovati
     */
    public ArrayList<Volo> cercaVoliAmministratore(String valore) {
        if (valore == null || valore.isEmpty()) return amministratore.getVoli();

        ArrayList<Volo> filtrati;
        String valoreLowerCase = valore.toLowerCase();

        filtrati = amministratore.ricercaRapida(valoreLowerCase);
        return filtrati;
    }


    /**
     * Crea un nuovo volo e lo inserisce nel db.
     *
     * @param compagniaAerea Il nome della compagnia aerea
     * @param origine        Aeroporto di partenza
     * @param destinazione   Aeroporto di arrivo
     * @param data           La data del volo
     * @param ora            L'orario del volo
     * @param ritardo        L'eventuale ritardo del volo
     * @param numeroGate     Il numero del gate
     * @return  Messaggio di esito
     */
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


    /**
     * Aggiorna i dati di un volo nel db
     *
     * @param codiceVolo       Il codice del volo da aggiornare
     * @param nuovaData        La nuova data del volo
     * @param nuovoOrario      Il nuovo orario del volo
     * @param nuovoRitardo     Il nuovo ritardo del volo
     * @param nuovoNumeroGateS Il nuovo numero del gate
     * @return Messaggio di esito
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


    /**
     * Recupera la lista dei gate disponibili.
     *
     * @return L'Arraylist dei gate disponibili
     */
    public ArrayList<String> getGateDisponibili() {
        ArrayList<String> gatesString = null;


        try {
            gatesString = gateDAO.getGateDisponibiliDAO();

        } catch (SQLException e) {
            return null;
        }

        return gatesString;
    }


    /**
     * Elimina un volo e cancella tutte le prenotazioni assocciate al volo eliminato.
     *
     * @param codiceVolo Il codice del volo da eliminare
     * @param gate       Il gate associato al volo da eliminare
     * @return Messaggio di esito.
     */
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

    /**
     * Recupera le prenotazioni associate ad uno specifico volo.
     *
     * @param codiceVolo il codice del volo da cui ottenere le prenotazioni.
     * @return Un ArrayList delle prenotazioni trovate.
     */
    public ArrayList<Prenotazione> getPrenotazioniByIdVolo(String codiceVolo){
        try{
            return prenotazioneDAO.getPrenotazioniByIdVoloPostgresDAO(codiceVolo);
        }catch(SQLException e){
            return null;
        }

    }
}
