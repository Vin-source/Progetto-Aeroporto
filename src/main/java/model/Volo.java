package model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Il volo preso dal passaggero
 */
public class Volo {
    private String codiceVolo; // auto increment
    private String compagniaAerea;
    private String origine;
    private String destinazione;
    private LocalDate data;
    private LocalTime orarioPrevisto;
    private int ritardo;
    private String[] postiOccupati = new String[30];




    // Uniformati con Controller (prima era HH:mm:ss)
    private final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");








    private Amministratore amministratore;
    private ArrayList<Prenotazione> prenotazioni;
    private Gate gate;
    private StatoVolo statoVolo = StatoVolo.PROGRAMMATO;


    /**
     * Il costruttore della classe Volo.java
     *
     * @param codiceVolo     Il codice del volo
     * @param compagniaAerea La compagnia aerea del volo
     * @param origine        L'origine del volo
     * @param destinazione   La destinazione del volo
     * @param data           La data di partenza del volo
     * @param orarioPrevisto L'orario di partenza del volo
     * @param ritardo        Il possibile ritardo del volo
     */
    public Volo(String codiceVolo,
                String compagniaAerea,
                String origine,
                String destinazione,
                String data,
                String orarioPrevisto,
                int ritardo) {

        this.codiceVolo = codiceVolo;
        this.compagniaAerea = compagniaAerea;
        this.data = LocalDate.parse(data, formatterData);
        this.orarioPrevisto = LocalTime.parse(orarioPrevisto, formatterOra);
        this.ritardo = ritardo;

        if(origine.equals("Napoli")){
            this.destinazione = destinazione;
            this.origine = null;
        }else{
            this.origine = origine;
            this.destinazione = null;
        }

        prenotazioni = new ArrayList<>();
    }


    /**
     * Ritorna il codice del volo
     *
     * @return il codice del volo
     */
    public String getCodiceVolo() {
        if(codiceVolo != null){
            return this.codiceVolo;
        }
        return null;
    }

    /**
     * Salva il codice del volo
     *
     * @param codiceVolo Il codice del volo
     */
    public void setCodiceVolo(String codiceVolo) { this.codiceVolo = codiceVolo;}

    public String getCompagniaAerea() {
        if(compagniaAerea != null){
            return this.compagniaAerea;
        }
        return null;
    }

    /**
     * Salva la compagnia aerea associata al volo
     *
     * @param compagniaAerea La compagnia aerea associata al volo
     */
    public void setCompagniaAerea(String compagniaAerea) { this.compagniaAerea = compagniaAerea; }


    /**
     * Ritorna l'origine di partenza del volo
     *
     * @return L'origine di partenza del volo
     */
    public String getOrigine() {
        if(origine != null){
            return this.origine;
        }
        return null;
    }

    /**
     * Salva l'origine di partenza del volo
     *
     * @param origine L'origine di partenza del volo
     */
    public void setOrigine(String origine) { this.origine = origine;}


    /**
     * Ritorna la destinazione del volo
     *
     * @return La destinazione del volo
     */
    public String getDestinazione() {
        if(destinazione != null){
            return this.destinazione;
        }
        return null;
    }

    /**
     * Salva la destinazione del volo
     *
     * @param destinazione La destinazione del volo
     */
    public void setDestinazione(String destinazione) { this.destinazione = destinazione; }


    /**
     * Ritorna la data di partenza del volo
     *
     * @return La data di partenza del volo
     */
    public String getData() {
        if(data != null){
            return this.data.format(formatterData);
        }
        return null;
    }

    /**
     * Salva la data di partenza del volo
     *
     * @param data La data di partenza del volo
     */
    public void setData(String data) { this.data = LocalDate.parse(data, formatterData); }


    /**
     * Ritorna l'orario di partenza del volo
     *
     * @return L'orario previsto di partenza del volo
     */
    public String getOrarioPrevisto() {
        if(orarioPrevisto != null){
            return this.orarioPrevisto.format(formatterOra);
        }
         return null;
    }

    /**
     * Salva l'orario di partenza del volo
     *
     * @param orarioPrevisto  L'orario previsto di partenza del volo
     */
    public void setOrarioPrevisto(String orarioPrevisto) { this.orarioPrevisto = LocalTime.parse(orarioPrevisto, formatterOra); }


    /**
     * Ritorna il possibile ritardo del volo
     *
     * @return Il possibile ritardo del volo
     */
    public int getRitardo() {
        if(ritardo >= 0){
            return this.ritardo;
        }
        return -1;
    }

    /**
     * Salva il possibile ritardo del volo
     *
     * @param ritardo Il possibile ritardo
     */
    public void setRitardo(int ritardo) { this.ritardo = ritardo; }


    /**
     * Ritorna i posti occupati nell'aereo
     *
     * @return the string [ ]
     */
    public String[] getPostiOccupati() {
        if(postiOccupati != null && (postiOccupati.length > 0)){
            return this.postiOccupati;
        }
        return null;
    }
    public void setPostiOccupati(String[] posti) { this.postiOccupati = posti; }


    /**
     * Gets Ritorna l'amministratore del sistemq
     *
     * @return L'amministratore del sistema
     */
// ---------------------- METODI OGGETTI ESTERNI ------------------------
    public Amministratore getAmministratore() {
        if(amministratore != null){
            return this.amministratore;
        }
        return null;
    }

    /**
     * Salva l'amministratore del sistema
     *
     * @param amministratore L'amministratore del sistema
     */
    public void setAmministratore(Amministratore amministratore) { this.amministratore = amministratore; }

    public ArrayList<Prenotazione> getPrenotazioni() {
        if(prenotazioni != null){
            return this.prenotazioni;
        }
        return null;
    }

    /**
     * Salva le prenotazioni effettuate
     *
     * @param prenotazioni Le prenotazioni effettuate
     */
    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) { this.prenotazioni = prenotazioni; }

    /**
     * Ritorna i gate del volo
     *
     * @return Il gate associato al volo
     */
    public Gate getGate() {
        if(gate != null){
            return this.gate;
        }
        return null;
    }

    /**
     * Salva il gate associato al volo
     *
     * @param gate the gate
     */
    public void setGate(Gate gate) { this.gate = gate; }

    public StatoVolo getStatoVolo() {
        if(statoVolo != null){
            return this.statoVolo;
        }
        return null;
    }

    /**
     * Salva lo stato del volo
     *
     * @param v Il valore dello stato del volo
     */
    public void setStatoVolo(StatoVolo v) { this.statoVolo = v; }


}