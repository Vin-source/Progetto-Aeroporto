package dao;

import java.util.ArrayList;

public interface PrenotazioneDAO {
    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli);
    public ArrayList<String> getPostiOccupatiDB(String codiceVolo);
    public boolean modificaPrenotazioneDB(String codiceVolo,String nome,String cognome,String cartaIdentita,String idPrenotazione, String nuovoPostoScelto);
    public boolean cancellaPrenotazioneDB(String idPrenotazione);
}
