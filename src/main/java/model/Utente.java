package model;

import java.util.ArrayList;


/**
 *  Utente generico registrato nel sistema
 */
public class Utente extends Ospite {
    private ArrayList<Prenotazione> prenotazioni;

    /**
     * Costruttore della classe Utente.java
     *
     * @param email    email dell'utente
     * @param password password dell'utente
     */
    public Utente(String email, String password) {
        super(email, password);
        this.prenotazioni = new ArrayList<>();
    }


    /**
     * Cerca delle specifiche prenotazioni di un utente
     * sulla base del nome del passaggero registrato o del
     * numero volo associato che coincidono con un parametro passato dal controller.
     * Inoltre, controlla tramite
     * {@link #verificaValorePerBagaglio(Prenotazione, String) verificaValorePerBagaglio}
     * se il valore ricercato è presente nel peso o nel codice dei bagagli associati alla prenotazione
     *
     * @param valore il nome del passaggero o il numero del volo
     * @return l'array list prenotazioniTrovate
     */
    public ArrayList<Prenotazione> cercaPrenotazioni(String valore) {
        ArrayList<Prenotazione> prenotazioniTrovate = new ArrayList<>();

        if (prenotazioni != null) {

            if (valore.isEmpty()) {
                return prenotazioni;
            }

            prenotazioni.forEach(prenotazione -> {
                boolean bagaglioTrovato = verificaValorePerBagaglio(prenotazione, valore);

                String nome = prenotazione.getNome();
                String codVolo = (prenotazione.getVolo() != null) ? prenotazione.getVolo().getCodiceVolo() : "";

                if ((nome != null && nome.toLowerCase().contains(valore.toLowerCase())) ||
                        (codVolo != null && codVolo.toLowerCase().contains(valore.toLowerCase())) ||
                            bagaglioTrovato) {

                    prenotazioniTrovate.add(prenotazione);
                }
            });



        }
        return prenotazioniTrovate;
    }

    /**
     * Verifica se i dati dei bagagli associati ad una specifica prenotazione coincidono con un valore ricercato.
     * @param prenotazione la prenotazione nel quale cercare il valore.
     * @param valore il valore da cercare.
     * @return L'esito dell'operazione.
     */
    private boolean verificaValorePerBagaglio(Prenotazione prenotazione, String valore){
        boolean bagaglioTrovato = false;
        ArrayList<Bagaglio> bagagli = prenotazione.getBagaglio();
        for(Bagaglio b : bagagli){
            if(String.valueOf(b.getPeso()).equals(valore) || String.valueOf(b.getCodice()).equals(valore)){
                bagaglioTrovato = true;
            }
        }

        return bagaglioTrovato;
    }



    // -------------- OGGETTI ESTERNI ------------------

    /**
     * Ritorna tutte le prenotazioni associate allo
     * specifico utente
     * @return l'ArrayList delle prenotazioni salvate
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
     * @param prenotazioni Nuovo ArrayList delle prenotazioni
     */
    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) { this.prenotazioni = prenotazioni; }

}
