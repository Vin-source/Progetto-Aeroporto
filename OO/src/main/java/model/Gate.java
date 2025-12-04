package model;

/**
 * Il gate associato ad un Volo
 */
public class Gate {
    private int numeroGate;
    private Volo volo;

    /**
     * Costruttore di Gate.java
     *
     * @param numeroGate Numero del gate corrispondente
     */
    public Gate(int numeroGate) {
        this.numeroGate = numeroGate;
    }

    /**
     * Ritorna il numero del gate
     *
     * @return numeroGate
     */
    public int getNumero() {
        if(numeroGate >= 0){
            return numeroGate;
        }
        return -1;
    }

    /**
     * Salva un nuovo numero per il gate
     *
     * @param numeroGate il nuovo numero gate
     */
    public void setNumero(int numeroGate) {
        this.numeroGate = numeroGate;
    }

    // ----------------------- OGGETTI ESTERNI --------------------

    /**
     * Ritorna l'oggetto Volo associato al gate corrispondente
     *
     * @return oggetto volo
     */
    public Volo getVolo() {
        if(volo != null){
            return volo;
        }
        return null;
    }

    /**
     * Salva un nuovo riferimento all'oggetto volo
     *
     * @param volo Il nuovo oggetto volo
     */
    public void setVolo(Volo volo) { this.volo = volo; }
}