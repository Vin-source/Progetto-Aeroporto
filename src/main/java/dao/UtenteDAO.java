package dao;

import model.Prenotazione;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione dell'utente.
 */
public interface UtenteDAO {
    /**
     * Recupera dal db l'elenco delle prenotazioni effettuate all'utente.
     *
     * @param email_utente L'email dell'utente.
     * @return Le prenotazioni effettuate dall'utente.
     * @throws SQLException Se si verifica un errore durante la connessione al database.
     */
    public ArrayList<Prenotazione> getPrenotazioniDB(String email_utente) throws SQLException;
}
