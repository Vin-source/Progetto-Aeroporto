package dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione dei gate.
 */
public interface GateDAO{

    /**
     * Assegna un gate ad un volo.
     *
     * @param codiceVolo Il codice del volo a cui assegnare il gate.
     * @param numeroGate Il numero del gate da assegnare.
     * @return Esito dell'assegnazione.
     * @throws SQLException Se si verifica un errore durante l'aggiornamento nel database.
     */
    boolean assegnaGate(String codiceVolo, int numeroGate) throws SQLException;

    /**
     * Recupera l'elenco dei gate disponibili.
     *
     * @return L'elenco dei gate disponibili.
     * @throws SQLException Se si verifica un errore durante la lettura dal database.
     */

    ArrayList<String> getGateDisponibiliDAO() throws SQLException;

    /**
     * Libera un gate precedentemente assegnato ad un volo.
     *
     * @param codiceVolo Il codice del volo id cui liberare il gate.
     * @throws SQLException Se si verifica un errore durante l'aggiornamento nel database.
     */
    void setCodiceVoloNull(int codiceVolo) throws SQLException;
}