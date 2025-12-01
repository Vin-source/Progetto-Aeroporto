package dao;

import java.sql.SQLException;

/**
 * * Interfaccia DAO per la gestione delle operazioni di autenticazione (login).
 */
public interface LoginDAO {

    /**
     * Verifica le credenziali di accesso e ritorna il ruolo dell'utente.
     *
     * @param email    L'indirizzo email dell'utente
     * @param password La password dell'utente
     * @return Il ruolo dell'utente
     * @throws SQLException Se si verifica un errore durante la connessione al database.
     */
    public String getUtentiDB(String email, String password) throws SQLException;
}