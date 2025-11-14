package model;

import java.util.ArrayList;

public class Amministratore extends Ospite {
    private ArrayList<Volo> voli;

    public Amministratore(String id, String email, String password) {
        super(email, password);
        this.id = id;

        voli = new ArrayList<>();
    }


    public ArrayList<Volo> ricercaRapida(String codiceVoloCercato, String compagniaAereaCercata) {
        ArrayList<Volo> voliTrovati = new ArrayList<>();
        if(voli != null){
            voli.forEach(volo -> {
                String codiceVolo = volo.getCodiceVolo();
                if (volo.getCompagniaAerea().equals(compagniaAereaCercata) || volo.getCodiceVolo().equals(codiceVoloCercato)) {
                    voliTrovati.add(volo);
                }
            });
            return voliTrovati;
        }
        return null;
    }

    public ArrayList<Volo> getVoli() {
        return voli;
    }

    public void setVoli(ArrayList<Volo> voli){
        this.voli = voli;
    }

}
