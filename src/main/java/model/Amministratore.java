package model;

import java.util.ArrayList;

/**
 * Amministratore registrato nel sistema
 */
public class Amministratore extends Ospite {
    private ArrayList<Volo> voli;

    /**
     * Costruttore della classe Amministratore.java
     *
     * @param id       id dell'amministratore
     * @param email    email dell'amministratore
     * @param password password dell'amministratore
     */
    public Amministratore(String id, String email, String password) {
        super(email, password);
        this.id = id;

        voli = new ArrayList<>();
    }


    /**
     * Ricerca i voli disponibili sulla base
     * del codice volo o della compagnia aerea
     *
     * @param valore     il valore di ricerca
     * @return arrayList dei voli cercati
     */
    public ArrayList<Volo> ricercaRapida(String valore) {
        ArrayList<Volo> voliTrovati = new ArrayList<>();
        if(voli != null){
            voli.forEach(volo -> {
                boolean prenotazioneBagaglioTrovato = verificaPerBagaglioPasseggero(volo, valore);

                if (volo.getCompagniaAerea().toLowerCase().contains(valore) ||
                        volo.getCodiceVolo().toLowerCase().contains(valore) ||
                            prenotazioneBagaglioTrovato) {
                    voliTrovati.add(volo);
                }
            });
            return voliTrovati;
        }
        return null;
    }


    /**
     * Verifica che il passeggero associato a una prenotazione abbia valori coincidenti con il valore ricercato.
     * @param volo l'oggetto volo.
     * @param valore il valore da ricercare.
     * @return  L'esito dell'operazione
     */
    private boolean verificaPerBagaglioPasseggero(Volo volo, String valore) {
        boolean prenotazioneBagaglioTrovato = false;


        if(!volo.getPrenotazioni().isEmpty()){
            ArrayList<Prenotazione> prenotazioni = volo.getPrenotazioni();
            if(!prenotazioni.isEmpty()){
                for(Prenotazione p : prenotazioni){
                    if(p.getNome().toLowerCase().contains(valore.toLowerCase())){
                        prenotazioneBagaglioTrovato = true;
                    }
                    prenotazioneBagaglioTrovato = verificaBagaglio(p, valore);
                }
            }
        }

        return prenotazioneBagaglioTrovato;
    }

    /**
     * verifica che il bagaglio associato a una prenotazione abbia valori coincidenti con il valore ricercato.
     *
     * @param p la prenotazione presa in considerazione
     * @param valore il valore ricercato
     * @return L'esito dell'operazione
     */
    private boolean verificaBagaglio(Prenotazione p, String valore) {
        boolean prenotazioneBagaglioTrovato = false;
        ArrayList<Bagaglio> bagagli = p.getBagaglio();
        if(bagagli != null && !bagagli.isEmpty()){
            for(Bagaglio b : bagagli){
                if(String.valueOf(b.getCodice()).equals(valore)){
                    prenotazioneBagaglioTrovato = true;
                }
            }
        }
        return prenotazioneBagaglioTrovato;
    }

    /**
     * Ritorna tutti i voli associati all'amministratore corrente
     *
     * @return L'Arraylist dei voli
     */
    public ArrayList<Volo> getVoli() {
        return voli;
    }


    /**
     * Salva i voli nella variabile voli dell'amministratore
     *
     * @param voli il nuovo oggetto contenente i voli da memorizzare
     */
    public void setVoli(ArrayList<Volo> voli){
        this.voli = voli;
    }

}
