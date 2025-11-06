package controller;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Controller {
    private Ospite ospite;
    private Amministratore amministratore;

    private final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

    public Controller() {
        amministratore = new Amministratore("IdProva", "admin@gmail.com", "password");


        LocalDate data1 = LocalDate.parse("07/02/2067", formatterData);
        LocalTime ora1 = LocalTime.parse("11:00", formatterOra);

        LocalDate data2 = LocalDate.parse("12/05/2067", formatterData);
        LocalTime ora2 = LocalTime.parse("14:00", formatterOra);

        LocalDate data3 = LocalDate.parse("26/11/2067", formatterData);
        LocalTime ora3 = LocalTime.parse("09:45", formatterOra);

        amministratore.getVoli().add(new Volo("Volo1","Alitalia","Spagna","Napoli",data1,ora1,0));
        amministratore.getVoli().add(new Volo("Volo2", "EasyJet", "Lapponia", "Napoli",data2,ora2,30));
        amministratore.getVoli().add(new Volo("Volo3","ArabLines","Berlino","Napoli",data3,ora3,10));
    }


    //LOGIN TESTING
    public String login(String email, String password) {

        if(amministratore.getEmail().equals(email) && amministratore.getPassword().equals(password)) {
            return "amministratore";
        }

        if(ospite.getEmail().equals(email) && ospite.getPassword().equals(password)) {
            return "utente";
        }
        return "errore";
    }
}
