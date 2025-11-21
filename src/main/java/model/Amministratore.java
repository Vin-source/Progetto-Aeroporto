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
                String codiceVolo = volo.getCodiceVolo();
                if (volo.getCompagniaAerea().toLowerCase().contains(valore) || volo.getCodiceVolo().toLowerCase().contains(valore)) {
                    voliTrovati.add(volo);
                }
            });
            return voliTrovati;
        }
        return null;
    }


    /**
     * Ritorna tutti i voli associati all'amministratore corrente
     *
     * @return voli
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
