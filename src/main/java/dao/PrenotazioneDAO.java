package dao;

import model.Prenotazione;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Interfaccia DAO per la gestione delle prenotazioni
 */
public interface PrenotazioneDAO {
    /**
     * Registra una nuova prenotazione nel database per un utente specifico.
     *
     * @param codiceVolo    Il codice univoco del volo da prenotare.
     * @param nome          Il nome del passeggero.
     * @param cognome       Il cognome del passeggero.
     * @param cid           La carta d'identità del passeggero.
     * @param posto         Il codice del posto selezionato.
     * @param email_utente  L'email dell'utente che sta effettuando la prenotazione.
     * @param numeroBagagli Il numero dei bagagli.
     * @param pesoTotaleDeiBagagli il peso totale dei bagagli inseriti
     * @return il valore booleano true o false.
     * @throws SQLException Se si verifica un errore durante la query di inserimento.
     */
    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli, String pesoTotaleDeiBagagli) throws SQLException;

    /**
     * Recupera l'elenco dei posti prenotati per un determinato volo.
     *
     * @param codiceVolo Il codice del volo di cui si vogliono conoscere i posti occupati.
     * @return L'Arraylist contenente i posti occupati.
     * @throws SQLException Se si verifica un errore di connessione al db.
     */
    public ArrayList<String> getPostiOccupatiDB(String codiceVolo) throws SQLException;

    /**
     * Modifica i dati o il posto scelto di una prenotazione esistente.
     *
     * @param codiceVolo       Il codice volo.
     * @param nome             Il nuovo nome del passeggero.
     * @param cognome          Il nuovo cognome del passeggero.
     * @param cartaIdentita    Il nuovo numero di carta d'identità.
     * @param idPrenotazione   L'id della prenotazione.
     * @param nuovoPostoScelto Il nuovo posto scelto.
     * @return Il valore booleano.
     * @throws SQLException Se si verifica un errore durante l'aggiornamento nel db.
     */
    public boolean modificaPrenotazioneDB(String codiceVolo,String nome,String cognome,String cartaIdentita,String idPrenotazione, String nuovoPostoScelto) throws SQLException;

    /**
     * Cancella una prenotazione dal database.
     *
     * @param idPrenotazione L'id della prenotazione da eliminare.
     * @return Il valore booleano.
     * @throws SQLException Se si verifica un errore durante l'eliminazione nel db.
     */
    public boolean cancellaPrenotazioneDB(String idPrenotazione) throws SQLException;


    /**
     * Recupera tutte le prenotazioni legate al volo tramite il suo codice
     *
     * @param codiceVolo il codice del volo dal quale trarre le prenotazioni corrispondenti
     * @return Un ArrayList delle prenotazioni trovate
     * @throws SQLException Se si verifica un errore durante l'eliminazione nel db
     */
    public ArrayList<Prenotazione> getPrenotazioniByIdVoloPostgresDAO(String codiceVolo) throws SQLException;
}
