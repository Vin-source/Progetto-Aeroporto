package model;

/**
 * Un bagaglio dello specifico passeggero
 */
public class Bagaglio{
    private int codiceBagaglio;

    private Passeggero passeggero;

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
     * Ritorna l'oggetto passeggero associato al bagaglio
     *
     * @return Oggetto passeggero
     */
    public Passeggero getPasseggero() {
        if(passeggero != null){
            return passeggero;
        }
        return null;
    }

    /**
     * Salva un nuovo riferimento a passeggero
     *
     * @param passeggero Il nuovo passeggero
     */
    public void setPasseggero(Passeggero passeggero) { this.passeggero = passeggero;}
}