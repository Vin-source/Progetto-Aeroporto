package model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Volo {
    private String codiceVolo; // auto increment
    private String compagniaAerea;
    private String origine;
    private String destinazione;
    private LocalDate data;
    private LocalTime orarioPrevisto;
    private int ritardo;
    private String[] postiOccupati = new String[30];
    private Amministratore amministratore;
    private ArrayList<Prenotazione> prenotazioni;
    private Gate gate;
    private StatoVolo statoVolo = StatoVolo.PROGRAMMATO;



    // Uniformati con Controller (prima era HH:mm:ss)
    private final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");



    public Volo(String codiceVolo,
                String compagniaAerea,
                String origine,
                String destinazione,
                String data,
                String orarioPrevisto,
                int ritardo) throws DateTimeParseException {

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




    public String getCodiceVolo() {
        if(codiceVolo != null){
            return this.codiceVolo;
        }
        return null;
    }

    public void setCodiceVolo(String codiceVolo) {
        this.codiceVolo = codiceVolo;
    }

    public String getCompagniaAerea(){
        if(compagniaAerea != null){
            return this.compagniaAerea;
        }
        return null;
    }
    public void setCompagniaAerea(String compagniaAerea){
        this.compagniaAerea = compagniaAerea;
    }


    public String getOrigine() {
        if(origine != null){
            return this.origine;
        }
        return null;
    }

    public void setOrigine(String origine){
        this.origine = origine;
    }


    public String getDestinazione(){
        if(destinazione != null){
            return this.destinazione;
        }
        return null;
    }

    public void setDestinazione(String destinazione){
        this.destinazione = destinazione;
    }


    public String getData(){
        if(data != null){
            return this.data.format(formatterData);
        }
        return null;
    }

    public void setData(String data){
        this.data = LocalDate.parse(data, formatterData);
    }


    public String getOrarioPrevisto(){
        if(orarioPrevisto != null){
            return this.orarioPrevisto.format(formatterOra);
        }
         return null;
    }

    public void setOrarioPrevisto(String orarioPrevisto){
        this.orarioPrevisto = LocalTime.parse(orarioPrevisto, formatterOra);
    }


    public int getRitardo(){
        if(ritardo >= 0){
            return this.ritardo;
        }
        return -1;
    }

    public void setRitardo(int ritardo){
        this.ritardo = ritardo;
    }


    public String[] getPostiOccupati(){
        if(postiOccupati != null && (postiOccupati.length > 0)){
            return this.postiOccupati;
        }
        return null;
    }

    public void setPostiOccupati(String[] posti){
        this.postiOccupati = posti;
    }





    // ---------------------- METODI OGGETTI ESTERNI ------------------------
    public Amministratore getAmministratore(){
        if(amministratore != null){
            return this.amministratore;
        }
        return null;
    }

    public void setAmministratore(Amministratore amministratore){
        this.amministratore = amministratore;
    }

    public ArrayList<Prenotazione> getPrenotazioni(){
        if(prenotazioni != null){
            return this.prenotazioni;
        }
        return null;
    }

    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni){
        this.prenotazioni = prenotazioni;
    }

    public Gate getGate(){
        if(gate != null){
            return this.gate;
        }
        return null;
    }

    public void setGate(Gate gate){
        this.gate = gate;
    }

    public StatoVolo getStatoVolo(){
        if(statoVolo != null){
            return this.statoVolo;
        }
        return null;
    }

    public void setStatoVolo(StatoVolo v) {
        this.statoVolo = v;
    }
}