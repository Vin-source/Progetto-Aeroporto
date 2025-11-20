package dao;

import model.Prenotazione;
import model.Volo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAO {
    public ArrayList<Volo> getVoliDB();
    public ArrayList<Prenotazione> getPrenotazioniDB(String email_utente);
}
