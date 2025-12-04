package dao;

import model.Gate;
import model.Volo;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione dei voli
 */
public interface VoloDAO{

    /**
     * Inserisci un nuovo volo nel db
     *
     * @param volo       Il nuovo volo da inserire
     * @param numeroGate Il numero del gate da assegnare al volo
     * @return Il codice del volo inserito
     * @throws SQLException Se si verifica un errore nell'inserimento  nel db
     */
    String inserisciVolo(Volo volo, String numeroGate) throws SQLException;

    /**
     * Aggiorna i dati di un volo nel db
     *
     * @param volo Il volo da aggiornare
     * @return L'esito dell'aggiornamento
     * @throws SQLException Se si verifica un errore durante l'aggiornamento nel db
     */
    boolean aggiornaVolo(Volo volo) throws SQLException;

    /**
     * Elimina i dati di un volo nel db
     *
     * @param codiceVolo Il codice volo del volo da eliminare
     * @param gate       Il gate associato al volo eliminato
     * @return Le prenotazioni relative il volo che sono state cancellate
     * @throws SQLException Se si verifica un errore durante l'eliminazione delle chiavi esterne
     */
    ArrayList<Integer> eliminaVolo(String codiceVolo, Gate gate) throws SQLException;

    /**
     * Recupera l'elenco completo di tutti i voli presenti nel db
     *
     * @return Tutti i voli del db
     * @throws SQLException Se si verifica un errore durante la lettura dei dati
     */
    ArrayList<Volo> getVoliDB() throws SQLException;
}