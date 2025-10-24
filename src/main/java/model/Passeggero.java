package model;
import java.util.ArrayList;


public class Passeggero {
    private String cartaIdentita;
    private String nome;
    private String cognome;


    private Prenotazione prenotazione;
    private ArrayList<Bagaglio> bagagli;

    public Passeggero(String nome, String cognome, String cartaIdentita) {
        this.nome = nome;
        this.cognome = cognome;
        this.cartaIdentita = cartaIdentita;

        bagagli = new ArrayList<>();
    }



    public String getCartaIdentita() {
        if(cartaIdentita != null){
            return this.cartaIdentita;
        }
        return null;
    }
    public void setCartaIdentita(String cartaIdentita) {
        this.cartaIdentita = cartaIdentita;
    }

    public String getNome() {
        if(nome != null){
            return this.nome;
        }
        return null;
    }
    public void setNome(String nome) { this.nome = nome;}

    public String getCognome() {
        if(cognome != null){
            return this.cognome;
        }
        return null;
    }
    public void setCognome(String cognome) { this.cognome = cognome;}


    // --------------------- OGGETTI ESTERNI --------------------

    public Prenotazione getPrenotazione() {
        if(prenotazione != null){
            return this.prenotazione;
        }
        return null;
    }
    public void setPrenotazione(Prenotazione prenotazione) { this.prenotazione = prenotazione;}

    public ArrayList<Bagaglio> getBagagli() {
        if(bagagli != null){
            return this.bagagli;
        }
        return null;
    }
    public void setBagagli(ArrayList<Bagaglio> bagagli) { this.bagagli = bagagli; }
}
