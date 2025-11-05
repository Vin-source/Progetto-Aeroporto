package controller;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Controller {
    private Ospite ospite;
    private Amministratore amministratore;

    private final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

    public Controller() {
        amministratore = new Amministratore("IdProva", "admin@gmail.com", "password");

        amministratore.getVoli().add(new Volo("Volo1","Alitalia","Spagna","Napoli","7/09/2067","11:00",0));
        amministratore.getVoli().add(new Volo("Volo2", "EasyJet", "Lapponia", "Napoli","12/05/2067","14:00",30));
        amministratore.getVoli().add(new Volo("Volo3","ArabLines","Berlino","Napoli","26/11/2067","9:45",10));
    }


    //LOGIN TESTING
    public String login(String email, String password) {
        if(ospite.getEmail().equals(email) && ospite.getPassword().equals(password)) {
            return "amministratore";
        }
        return "utente";
    }
}
