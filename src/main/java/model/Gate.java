package model;

public class Gate {
    private int numeroGate;
    private Volo volo;

    public Gate(int numeroGate) {
        this.numeroGate = numeroGate;
    }

    public int getNumero() {
        if(numeroGate >= 0){
            return numeroGate;
        }
        return -1;
    }
    public void setNumero(int numeroGate) {
        this.numeroGate = numeroGate;
    }

    // ----------------------- OGGETTI ESTERNI --------------------

    public Volo getVolo() {
        if(volo != null){
            return volo;
        }
        return null;
    }

    public void setVolo(Volo volo) { this.volo = volo; }
}