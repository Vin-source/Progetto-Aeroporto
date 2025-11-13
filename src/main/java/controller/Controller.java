package controller;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class Controller {
    private Utente utente;
    private Amministratore amministratore;

  //  private final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("d/MM/yyyy");
  //  private final DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

    public Controller() {
        amministratore = new Amministratore("IdProva", "admin@gmail.com", "password");
        utente = new Utente("IdProva", "utente@gmail.com", "password");

//        LocalDate data1 = LocalDate.parse("07/02/2067", formatterData);
//        LocalTime ora1 = LocalTime.parse("11:00", formatterOra);
//
//        LocalDate data2 = LocalDate.parse("12/05/2067", formatterData);
//        LocalTime ora2 = LocalTime.parse("14:00", formatterOra);
//
//        LocalDate data3 = LocalDate.parse("26/11/2067", formatterData);
//        LocalTime ora3 = LocalTime.parse("09:45", formatterOra);

        try {
            Volo v1 = new Volo("Volo1","Alitalia","Spagna","Napoli","07/02/2067","11:00",0);
            v1.setGate(new Gate(1));
            amministratore.getVoli().add(v1);

            Volo v2 = new Volo("Volo2", "EasyJet", "Lapponia", "Napoli","12/05/2067","14:00",30);
            v2.setGate(new Gate(2));
            amministratore.getVoli().add(v2);

            Volo v3 = new Volo("Volo3","ArabLines","Berlino","Napoli","26/11/2067","09:45",10);
            v3.setGate(new Gate(3));
            amministratore.getVoli().add(v3);

        } catch (DateTimeParseException e) {
            System.err.println("Errore fatale nel parsing dei dati di test: " + e.getMessage());
        }
    }


    //LOGIN TESTING
    public String login(String email, String password) {

        if(amministratore.getEmail().equals(email) && amministratore.getPassword().equals(password)) {
            return "amministratore";
        }

        if(utente.getEmail().equals(email) && utente.getPassword().equals(password)) {
            return "utente";
        }
        return "errore";
    }

    //CREAZIONE DI UN VOLO TESTING

    public Boolean creaNuovoVolo(String codiceVolo, String compagniaAerea, String origine, String destinazione,
                                 String data, String ora, String ritardo, String numeroGate){
        try {
            if (!origine.equalsIgnoreCase("Napoli") && !destinazione.equalsIgnoreCase("Napoli")) {
                throw new Exception("Volo non valido: deve essere un arrivo o una partenza da Napoli.");
            }

            int ritardoParsed = Integer.parseInt(ritardo);
            int numeroGateParsed = Integer.parseInt(numeroGate);

            Volo volo = new Volo(codiceVolo, compagniaAerea, origine, destinazione, data, ora, ritardoParsed);

            volo.setGate(new Gate(numeroGateParsed));

            this.amministratore.getVoli().add(volo);

            return true;

        } catch(Exception e) {
            System.err.println("Errore creazione volo (intercettato da Controller): "+e.getMessage());
            return false;
        }
    }

    //VISUALIZZAZIONE DEI VOLI TESTING
    public ArrayList<Volo> getTuttiVoli(){
        return this.amministratore.getVoli();
    }

    //RICERCA DEI VOLI TESTING
    public ArrayList<Volo> cercaVoli(String testoRicerca) {
        ArrayList<Volo> voliFiltrati = new ArrayList<>();
        String ricercaLower = testoRicerca.toLowerCase();


        if (ricercaLower.isEmpty()) {
            return getTuttiVoli();
        }

        for (Volo volo : this.amministratore.getVoli()) {
            boolean origineTrovata = volo.getOrigine() != null && volo.getOrigine().toLowerCase().contains(ricercaLower);
            boolean destinazioneTrovata = volo.getDestinazione() != null && volo.getDestinazione().toLowerCase().contains(ricercaLower);

            if (volo.getCodiceVolo().toLowerCase().contains(ricercaLower) ||
                    volo.getCompagniaAerea().toLowerCase().contains(ricercaLower) ||
                    origineTrovata || destinazioneTrovata)
            {
                voliFiltrati.add(volo);
            }
        }
        return voliFiltrati;
    }

    public Boolean aggiornaVolo(String codiceVolo, String nuovaData, String nuovoOrario,
                                String nuovoRitardo, String nuovoNumeroGateS) {
        try {
            Volo voloDaAggiornare = null;
            for (Volo v : this.amministratore.getVoli()) {
                if (v.getCodiceVolo().equals(codiceVolo)) {
                    voloDaAggiornare = v;
                    break;
                }
            }

            if (voloDaAggiornare == null) {
                return false;
            }

            voloDaAggiornare.setData(nuovaData);
            voloDaAggiornare.setOrarioPrevisto(nuovoOrario);
            voloDaAggiornare.setRitardo(Integer.parseInt(nuovoRitardo));

            // 3. Gestisci l'aggiornamento del Gate
            int nuovoNumeroGateInt = Integer.parseInt(nuovoNumeroGateS);
            if (voloDaAggiornare.getGate() != null) {
                voloDaAggiornare.getGate().setNumero(nuovoNumeroGateInt);
            } else {
                voloDaAggiornare.setGate(new Gate(nuovoNumeroGateInt));
            }

            return true;

        } catch (DateTimeParseException | NumberFormatException e) {
            System.err.println("Errore aggiornamento: " + e.getMessage());
            return false;
        }
    }


    public ArrayList<String> getGateDisponibili() {
        ArrayList<String> gates = new ArrayList<>();
        // Simuliamo 20 gate
        for (int i = 1; i <= 9; i++) {
            gates.add(String.valueOf(i));
        }
        return gates;
    }
}
