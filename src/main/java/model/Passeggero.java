package model;
import java.util.ArrayList;


/**
 * La persona che prenderà il volo
 */
public class Passeggero {
    private String cartaIdentita;
    private String nome;
    private String cognome;


    private Prenotazione prenotazione;
    private ArrayList<Bagaglio> bagagli;

    /**
     * Costruttore della classe Passeggero
     *
     * @param nome          Il nome del passeggero
     * @param cognome       Il cognome del passegero
     * @param cartaIdentita la carta d'identità del passeggero
     */
    public Passeggero(String nome, String cognome, String cartaIdentita) {
        this.nome = nome;
        this.cognome = cognome;
        this.cartaIdentita = cartaIdentita;

        bagagli = new ArrayList<>();
    }


    /**
     * Ritorna la carta d'identità del passeggero
     *
     * @return cartaIdentita
     */
    public String getCartaIdentita() {
        if(cartaIdentita != null){
            return this.cartaIdentita;
        }
        return null;
    }

    /**
     * Salva una nuova carta d'identità per il passeggero
     *
     * @param cartaIdentita La nuova carta d'identità
     */
    public void setCartaIdentita(String cartaIdentita) {
        this.cartaIdentita = cartaIdentita;
    }

    /**
     * Ritorna il nome del passeggero
     *
     * @return nome
     */
    public String getNome() {
        if(nome != null){
            return this.nome;
        }
        return null;
    }

    /**
     * Salva il nome del passeggero
     *
     * @param nome il nuovo nome del passeggero
     */
    public void setNome(String nome) { this.nome = nome;}

    /**
     * Ritorna il cognome del passeggero
     *
     * @return cognome
     */
    public String getCognome() {
        if(cognome != null){
            return this.cognome;
        }
        return null;
    }

    /**
     * Salva un nuovo cognome per il passeggero
     *
     * @param cognome il nuovo cognome del passeggero
     */
    public void setCognome(String cognome) { this.cognome = cognome;}


    // --------------------- OGGETTI ESTERNI --------------------

    /**
     * Ritorna la prenotazione associata al passeggero
     *
     * @return prenotazione
     */
    public Prenotazione getPrenotazione() {
        if(prenotazione != null){
            return this.prenotazione;
        }
        return null;
    }

    /**
     * Salva un nuovo riferimento alla prenotazione per il passeggero
     *
     * @param prenotazione Il nuovo oggetto prenotazione
     */
    public void setPrenotazione(Prenotazione prenotazione) { this.prenotazione = prenotazione;}

    /**
     * Ritorna l'oggetto bagaglio associato all'utente
     *
     * @return ArrayList di bagaglio
     */
    public ArrayList<Bagaglio> getBagagli() {
        if(bagagli != null){
            return this.bagagli;
        }
        return null;
    }

    /**
     * Salva un nuovo oggetto bagaglio associato al passeggero
     *
     * @param bagagli ArrayList di Bagaglio
     */
    public void setBagagli(ArrayList<Bagaglio> bagagli) { this.bagagli = bagagli; }
}
