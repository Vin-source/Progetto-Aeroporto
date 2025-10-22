package model;

public class Prenotazione {
    private String idPrenotazione;//modificare class diagram
    private String nome;
    private String cognome;
    private String cartaIdentita;// va messo nel setNumero
    private String postoAssegnato;





    // oggetti esterni
    private Utente utente;
    private Volo volo;
    private Passeggero passeggero;
    private StatoPrenotazione statoPrenotazione = StatoPrenotazione.IN_ATTESA;




    public Prenotazione(String nome,
                        String cognome,
                        String cartaIdentita,
                        String postoAssegnato) {
        this.nome = nome;
        this.cognome = cognome;
        this.cartaIdentita = cartaIdentita;
        this.postoAssegnato = postoAssegnato;
    }






    public String getNome() {
        if(this.nome != null){
            return this.nome;
        }
        return null;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCognome() {
        if(this.cognome != null){
            return this.cognome;
        }
        return null;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


    public String getCartaIdentita() {
        if(this.cartaIdentita != null){
            return this.cartaIdentita;
        }
        return null;
    }
    public void setCartaIdentita(String cartaIdentita) { this.cartaIdentita = cartaIdentita; }


    public String getIdPrenotazione() {
        return this.idPrenotazione;
    }
    public void setIdPrenotazione(String idPrenotazione) { this.idPrenotazione = idPrenotazione;}


    public String getPostoAssegnato() {
        if(this.postoAssegnato != null){
            return this.postoAssegnato;
        }
        return null;
    }
    public void setPostoAssegnato(String postoAssegnato) { this.postoAssegnato = postoAssegnato;}





    // ---------------------- OGGETTI ESTERNI -------------------------



    public Utente getUtente() {
        if(utente != null){
            return utente;
        }
        return null;
    }
    public void setUtente(Utente utente) { this.utente = utente; }

    public Volo getVolo() {
        if(volo != null){
            return volo;
        }
        return null;
    }
    public void setVolo(Volo volo){ this.volo = volo; }

    public Passeggero getPasseggero() {
        if(passeggero != null){
            return passeggero;
        }
        return null;
    }
    public void setPasseggero(Passeggero passeggero) { this.passeggero = passeggero; }

    public StatoPrenotazione getStatoPrenotazione() {
        if(statoPrenotazione != null){
            return this.statoPrenotazione;
        }
        return null;
    }
    public void setStatoPrenotazione(StatoPrenotazione statoPrenotazione) { this.statoPrenotazione = statoPrenotazione; }

    public String getCodiceVolo(){
        if(this.volo != null){
            return this.volo.getCodiceVolo();
        }


    }

}
