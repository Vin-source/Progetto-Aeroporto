package model;

import java.util.ArrayList;


/**
 *  Utente generico registrato nel sistema
 */
public class Utente extends Ospite {
    private ArrayList<Prenotazione> prenotazioni;
    private String id;
    private String email;
    private String password;

    /**
     * Costruttore della classe Utente.java
     *
     * @param id       id dell'utente
     * @param email    email dell'utente
     * @param password password dell'utente
     */
    public Utente(String id, String email, String password) {
        super(email, password);
        this.id = id;
        this.prenotazioni = new ArrayList<Prenotazione>();
    }


    /**
     * Cerca la prenotazione dello specifico utente
     * sulla base del nome del passaggero o del
     * numero volo
     *
     * @param valore il nome del passaggero o il numero del volo ( o il numero del bagaglio)
     * @return l'array list prenotazioniTrovate
     */
    public ArrayList<Prenotazione> cercaPrenotazioni(String valore, boolean checkBagaglio) {
        ArrayList<Prenotazione> prenotazioniTrovate = new ArrayList<>();

        if (prenotazioni != null) {

            if (valore.isEmpty()) {
                return prenotazioni;
            }

            prenotazioni.forEach(prenotazione -> {

                String nome = prenotazione.getNome();
                String codVolo = (prenotazione.getVolo() != null) ? prenotazione.getVolo().getCodiceVolo() : "";
                ArrayList<Bagaglio> bagagli = prenotazione.getBagaglio();
                boolean isBagaglioIn = false;

                if(bagagli != null){
                    for(int i = 0; i < bagagli.toArray().length; i++){
                        Bagaglio b = bagagli.get(i);
                        if(String.valueOf(b.getCodice()).equals(valore)){
                            isBagaglioIn = true;
                        }
                    }
                }

                if(!checkBagaglio) isBagaglioIn = false;


                if ((nome != null && nome.toLowerCase().contains(valore.toLowerCase())) ||
                        (codVolo != null && codVolo.toLowerCase().contains(valore.toLowerCase())) ||
                            isBagaglioIn == true) {

                    prenotazioniTrovate.add(prenotazione);
                }

            });

            return prenotazioniTrovate;

        } else {
            return new ArrayList<>();
        }
    }



    // -------------- OGGETTI ESTERNI ------------------

    /**
     * Ritorna tutte le prenotazioni associate allo
     * specifico utente
     * @return le prenotazioni
     */
    public ArrayList<Prenotazione> getPrenotazioni() {
        if(prenotazioni != null){
            return prenotazioni;
        }
        return null;
    }

    /**
     * Salva tutte le prenotazioni associate all'utente
     * nell'arraylist corrispondente
     *
     * @param prenotazioni L'arraylist delle prenotazioni
     */
    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) { this.prenotazioni = prenotazioni; }

}
