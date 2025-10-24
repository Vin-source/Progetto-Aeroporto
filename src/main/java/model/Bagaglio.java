package model;

public class Bagaglio{
    private int codiceBagaglio;

    private Passeggero passeggero;

    public Bagaglio(int codice) {
        this.codiceBagaglio = codice;
    }


    public int getCodice() {
        if(codiceBagaglio >= 0){
            return codiceBagaglio;
        }
        return -1;
    }
    public void setCodice(int codice) { this.codiceBagaglio = codice; }



    // ----------------------- OGGETTI ESTERNI --------------------



    public Passeggero getPasseggero() {
        if(passeggero != null){
            return passeggero;
        }
        return null;
    }
    public void setPasseggero(Passeggero passeggero) { this.passeggero = passeggero;}
}