package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PrenotazioneDAO {
    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli) throws SQLException;
    public ArrayList<String> getPostiOccupatiDB(String codiceVolo) throws SQLException;
    public boolean modificaPrenotazioneDB(String codiceVolo,String nome,String cognome,String cartaIdentita,String idPrenotazione, String nuovoPostoScelto) throws SQLException;
    public boolean cancellaPrenotazioneDB(String idPrenotazione) throws SQLException;
}
