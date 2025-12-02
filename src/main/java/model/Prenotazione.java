package model;

import java.util.ArrayList;

/**
 * La prenotazione effettuata\modificata dall'utente
 */
public class Prenotazione {
    private String idPrenotazione;//modificare class diagram
    private String nome;
    private String cognome;
    private String cartaIdentita;// va messo nel setNumero
    private String postoAssegnato;





    // oggetti esterni
    private Utente utente;
    private Volo volo;
    private ArrayList<Bagaglio> bagagli;
    private StatoPrenotazione statoPrenotazione = StatoPrenotazione.IN_ATTESA;


    /**
     * Costruttore della classe Prenotazione.java
     * Inizializza le principali variabili nel momento della creazione
     * di un nuovo oggetto Prenotazione
     *
     * @param nome           Nome del passeggero
     * @param cognome        Cognome del passeggero
     * @param cartaIdentita  Carta d'identità del passeggero
     * @param postoAssegnato posto scelto dal passeggero
     */
    public Prenotazione(String nome,
                        String cognome,
                        String cartaIdentita,
                        String postoAssegnato) {
        this.nome = nome;
        this.cognome = cognome;
        this.cartaIdentita = cartaIdentita;
        this.postoAssegnato = postoAssegnato;
    }


    /**
     * Ritorna il nome del passeggero
     *
     * @return nome
     */
    public String getNome() {
        if(this.nome != null){
            return this.nome;
        }
        return null;
    }

    /**
     * Salva un nuovo nome per il passeggero
     *
     * @param nome il nuovo nome del passeggero
     */
    public void setNome(String nome) {
        this.nome = nome;
    }


    /**
     * Ritorna il cognome del passeggero
     *
     * @return cognome
     */
    public String getCognome() {
        if(this.cognome != null){
            return this.cognome;
        }
        return null;
    }

    /**
     * Salva il cognome del passeggero
     *
     * @param cognome il nuovo cognome del passeggero
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


    /**
     * Ritorna la carta d'identità del passeggero
     *
     * @return cartaIdentita
     */
    public String getCartaIdentita() {
        if(this.cartaIdentita != null){
            return this.cartaIdentita;
        }
        return null;
    }

    /**
     * Salva una nuova carta d'identità della prenotazione
     *
     * @param cartaIdentita La nuova carta d'identità
     */
    public void setCartaIdentita(String cartaIdentita) { this.cartaIdentita = cartaIdentita; }


    /**
     * Ritorna l'id della prenotazione
     *
     * @return idPrenotazione
     */
    public String getIdPrenotazione() {
        return this.idPrenotazione;
    }

    /**
     * Salva l'id della prenotazione
     *
     * @param idPrenotazione il nuovo id della prenotazione
     */
    public void setIdPrenotazione(String idPrenotazione) { this.idPrenotazione = idPrenotazione;}


    /**
     * Ritorna il posto in aereo scelto dal passeggero
     *
     * @return postoScelto
     */
    public String getPostoAssegnato() {
        if(this.postoAssegnato != null){
            return this.postoAssegnato;
        }
        return null;
    }

    /**
     * Salva il posto assegnato al passeggero
     *
     * @param postoAssegnato il nuovo posto scelto dal passeggero
     */
    public void setPostoAssegnato(String postoAssegnato) { this.postoAssegnato = postoAssegnato;}





    // ---------------------- OGGETTI ESTERNI -------------------------


    /**
     * Ritorna l'oggetto utente che effettua la prenotazione
     *
     * @return utente
     */
    public Utente getUtente() {
        if(utente != null){
            return utente;
        }
        return null;
    }

    /**
     * Salva un nuovo oggetto utente
     *
     * @param utente nuovo oggetto Utente
     */
    public void setUtente(Utente utente) { this.utente = utente; }

    /**
     * Ritorna il volo associato alla prenotazione
     *
     * @return l'oggetto volo
     */
    public Volo getVolo() {
        if(volo != null){
            return volo;
        }
        return null;
    }

    /**
     * Salva il volo
     *
     * @param volo il nuovo oggetto volo
     */
    public void setVolo(Volo volo){ this.volo = volo; }

    /**
     * Ritorna l'oggetto bagagli associato alla prenotazione
     *
     * @return l'oggetto bagagli
     */
    public ArrayList<Bagaglio> getBagaglio() {
        if(bagagli != null){
            return bagagli;
        }
        return null;
    }

    /**
     * Salva un nuovo oggetto bagaglio
     *
     * @param bagagli il nuovo oggetto bagagli
     */
    public void setBagagli(ArrayList<Bagaglio> bagagli) { this.bagagli = bagagli; }

    /**
     * Ritorna lo stato attuale della prenotazione
     *
     * @return statoPrenotazione
     */
    public StatoPrenotazione getStatoPrenotazione() {
        if(statoPrenotazione != null){
            return this.statoPrenotazione;
        }
        return null;
    }

    /**
     * Salva lo stato della prenotazione
     *
     * @param statoPrenotazione Lo stato della prenotazione
     */
    public void setStatoPrenotazione(StatoPrenotazione statoPrenotazione) { this.statoPrenotazione = statoPrenotazione; }

    /**
     * Ritorna il codice del volo associato alla prenotazione
     *
     * @return codiceVolo
     */
    public String getCodiceVolo(){
        if(this.volo != null){
            return this.volo.getCodiceVolo();
        }

    return null;
    }

}
