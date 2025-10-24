package model;

import java.util.ArrayList;

public class Utente extends Ospite {
    private ArrayList<Prenotazione> prenotazioni;
    private String id;
    private String email;
    private String password;

    public Utente(String id, String email, String password) {
        super(email, password);
        this.id = id;
        this.prenotazioni = new ArrayList<Prenotazione>();
    }


    public ArrayList<Prenotazione> cercaPrenotazioni(String valore) {
        ArrayList<Prenotazione> prenotazioniTrovate = new ArrayList<>();
        if(prenotazioni != null){
            prenotazioni.forEach(prenotazione -> {
                if (prenotazione.getPasseggero().getNome().equals(valore) || prenotazione.getVolo().getCodiceVolo().equals(valore)) {
                    prenotazioniTrovate.add(prenotazione);
                }
            });
            return prenotazioniTrovate;
        }
    return null;
    }



    // -------------- OGGETTI ESTERNI ------------------

    public ArrayList<Prenotazione> getPrenotazioni() {
        if(prenotazioni != null){
            return prenotazioni;
        }
        return null;
    }
    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) { this.prenotazioni = prenotazioni; }

}
