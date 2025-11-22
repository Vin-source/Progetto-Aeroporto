package dao;

import model.Prenotazione;
import model.Volo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAO {
    public ArrayList<Prenotazione> getPrenotazioniDB(String email_utente);
    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli);
    public ArrayList<String> getPostiOccupatiDB(String codiceVolo);
    public boolean modificaPrenotazioneDB(String codiceVolo,String nome,String cognome,String cartaIdentita,String idPrenotazione);
}
