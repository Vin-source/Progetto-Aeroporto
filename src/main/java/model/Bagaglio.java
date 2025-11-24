package model;

/**
 * Un bagaglio dello specifico passeggero
 */
public class Bagaglio{
    private int codiceBagaglio;

    private Prenotazione prenotazione;

    /**
     * Costruttore di Bagaglio.java
     *
     * @param codice il codice univoco associato al bagaglio
     */
    public Bagaglio(int codice) {
        this.codiceBagaglio = codice;
    }


    /**
     * Ritorna il codice del bagaglio
     *
     * @return codiceBagaglio
     */
    public int getCodice() {
        if(codiceBagaglio >= 0){
            return codiceBagaglio;
        }
        return -1;
    }

    /**
     * Salva un nuovo codice per il bagaglio
     *
     * @param codice il nuovo codiceBagaglio
     */
    public void setCodice(int codice) { this.codiceBagaglio = codice; }



    // ----------------------- OGGETTI ESTERNI --------------------


    /**
     * Ritorna l'oggetto prenotazione associato al bagaglio
     *
     * @return Oggetto prenotazione
     */
    public Prenotazione getPrenotazione() {
        if(prenotazione != null){
            return prenotazione;
        }
        return null;
    }

    /**
     * Salva un nuovo riferimento a una prenotazione
     *
     * @param prenotazione La nuova prenotazione
     */
    public void setPrenotazione(Prenotazione prenotazione) { this.prenotazione = prenotazione;}
}