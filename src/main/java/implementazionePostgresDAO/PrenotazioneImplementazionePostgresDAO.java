package implementazionePostgresDAO;

import dao.PrenotazioneDAO;
import database.ConnessioneDatabase;
import model.Bagaglio;
import model.Prenotazione;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementazione dell'interfaccia {@link PrenotazioneDAO}.
 */
public class PrenotazioneImplementazionePostgresDAO implements PrenotazioneDAO {


    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al db.
     */
    public PrenotazioneImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Effettua una nuova prenotazione per l'utente nel db.
     * Il metodo è anche responsabile di associare il posto in aereo scelto dall'utente
     * all'interno del database
     *
     * @param codiceVolo    Il codice univoco del volo da prenotare.
     * @param nome          Il nome del passeggero.
     * @param cognome       Il cognome del passeggero.
     * @param cid           La carta d'identità del passeggero.
     * @param posto         Il codice del posto selezionato.
     * @param email_utente  L'email dell'utente che sta effettuando la prenotazione.
     * @param numeroBagagli Il numero dei bagagli.
     * @return L'esito dell'operazione.
     * @throws SQLException Se si verifica un errore nella query SQL.
     */
    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli, String pesoTotaleDeiBagagli) throws  SQLException{
        String sql = "INSERT INTO prenotazione(nome, cognome, carta_identita, email_utente, stato_prenotazione) VALUES (?,?,?,?,?)";
        String sql2 = "INSERT INTO associa(codice_volo, posto, id_prenotazione) VALUES (?, ?, ?)";
        String sql3;


            try(PreparedStatement prenotazioneSQL = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                prenotazioneSQL.setString(1, nome);
                prenotazioneSQL.setString(2, cognome);
                prenotazioneSQL.setString(3, cid);
                prenotazioneSQL.setString(4, email_utente);
                prenotazioneSQL.setObject(5, "IN_ATTESA", Types.OTHER);

                prenotazioneSQL.executeUpdate();

                ResultSet rs = prenotazioneSQL.getGeneratedKeys();
                int nuovoId = 0;
                if(rs.next()){
                    nuovoId = rs.getInt(1);
                }

                try(PreparedStatement associaSQL = connection.prepareStatement(sql2)){
                    associaSQL.setInt(1, Integer.parseInt(codiceVolo));
                    associaSQL.setString(2, posto);
                    associaSQL.setInt(3, nuovoId);

                    associaSQL.executeUpdate();

                    for(int i = 0; i < numeroBagagli; i++) {
                        sql3 = "INSERT INTO bagaglio(id_prenotazione, peso) VALUES (?, ?)";
                        try(PreparedStatement bagaglioSQL = connection.prepareStatement(sql3)){
                            bagaglioSQL.setInt(1, nuovoId);
                            bagaglioSQL.setFloat(2,  Float.parseFloat(pesoTotaleDeiBagagli));
                            bagaglioSQL.executeUpdate();
                        }

                    }
                    rs.close();
                }
                return true;
            }
    }


    /**
     * Recupera i posti occupati nell'aereo dato il codice di uno specifico volo.
     *
     * @param codiceVolo Il codice del volo di cui si vogliono conoscere i posti occupati.
     * @return Un'Arraylist contenente i posti occupati.
     * @throws SQLException Se si verifica un errore nella query SQL.
     */
    public ArrayList<String> getPostiOccupatiDB(String codiceVolo) throws SQLException{
        String sql = "SELECT posto FROM associa WHERE codice_volo = ?";
        ArrayList<String> postiTrovati = new ArrayList<>();


            try(PreparedStatement st = connection.prepareStatement(sql)){
                st.setInt(1, Integer.parseInt(codiceVolo));
                ResultSet rs = st.executeQuery();

                while(rs.next()){
                    postiTrovati.add(rs.getString("posto"));
                }

                rs.close();

                return postiTrovati;
            }


    }


    /**
     * Aggiorna i dati su una prenotazione esistente.
     * Il metodo è anche responsabile di modificare il posto assegnato in aereo
     * dall'utente (nel caso in cui si sceglie un posto diverso).
     *
     * @param codiceVolo       Il codice volo.
     * @param nome             Il nuovo nome del passeggero.
     * @param cognome          Il nuovo cognome del passeggero.
     * @param cartaIdentita    Il nuovo numero di carta d'identità.
     * @param idPrenotazione   L'id della prenotazione.
     * @param nuovoPostoScelto Il nuovo posto scelto.
     * @return L'esito dell'operazione.
     * @throws SQLException Se si verifica un errore nella query SQL.
     */
    public boolean modificaPrenotazioneDB(String codiceVolo,String nome,String cognome,String cartaIdentita,String idPrenotazione, String nuovoPostoScelto) throws SQLException{
        String sql = "UPDATE prenotazione SET nome = ?, cognome = ?, carta_identita = ? WHERE id = ?";
        String sql2 = "UPDATE associa SET posto = ? WHERE id_prenotazione = ?";


            try(PreparedStatement st = connection.prepareStatement(sql)){
                st.setString(1, nome);
                st.setString(2, cognome);
                st.setString(3, cartaIdentita);
                st.setInt(4, Integer.parseInt(idPrenotazione));

                st.execute();
            }

            try(PreparedStatement st2 = connection.prepareStatement(sql2)){
                st2.setString(1, nuovoPostoScelto);
                st2.setInt(2, Integer.parseInt(idPrenotazione));

                st2.execute();
            }

        return true;
    }

    /**
     * Elimina una prenotazione esistente prenotata dall'utente.
     * Il metodo è responsabile anche dell'aggiornamento delle tabelle associa e bagaglio
     * eliminando i relativi elementi associati alla prenotazione eliminata.
     *
     * @param idPrenotazione L'id della prenotazione da eliminare.
     * @return L'esito dell'operazione.
     * @throws SQLException Se si verifica un errore nella query SQL.
     */
    public boolean cancellaPrenotazioneDB(String idPrenotazione) throws SQLException{
        String sql = "UPDATE prenotazione SET stato_prenotazione = 'CANCELLATA' WHERE id = ?";
        String sql2 = "DELETE FROM associa WHERE  id_prenotazione = ?";
        String sql3 = "DELETE FROM bagaglio WHERE id_prenotazione = ?";

            try(PreparedStatement st = connection.prepareStatement(sql)){
                st.setInt(1, Integer.parseInt(idPrenotazione));
                st.executeUpdate();
            }

            try(PreparedStatement st2 = connection.prepareStatement(sql2)){
                st2.setInt(1, Integer.parseInt(idPrenotazione));
                st2.execute();
            }

            try(PreparedStatement st3 = connection.prepareStatement(sql3)){
                st3.setInt(1, Integer.parseInt(idPrenotazione));
                st3.execute();
            }

            return true;
    }


    /**
     * Recupera le prenotazioni associate ad un volo nel DB.
     * Il metodo recupera le prenotazioni associate al volo, dopodichè
     * recupera i dati sui bagagli associati ad ogni prenotazione
     *
     * @param codiceVolo il codice del volo dal quale trarre le prenotazioni corrispondenti.
     * @return Un ArrayList di prenotazioni trovate.
     * @throws SQLException Se si verifica un errore nelle query SQL.
     */
    public ArrayList<Prenotazione> getPrenotazioniByIdVoloPostgresDAO(String codiceVolo) throws SQLException{
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM associa JOIN prenotazione ON id_prenotazione=id WHERE codice_volo=?")){
            ps.setInt(1, Integer.parseInt(codiceVolo));
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                try(PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM bagaglio where id_prenotazione=?")){
                    ps2.setInt(1, Integer.parseInt(rs.getString("id_prenotazione")));
                    ResultSet rs2 = ps2.executeQuery();

                    ArrayList<Bagaglio> bagagli = new ArrayList<>();
                    while(rs2.next()){
                        Bagaglio b = new Bagaglio(rs2.getInt("codice_bagaglio"));
                        b.setPeso(rs2.getFloat("peso"));
                        bagagli.add(b);
                    }

                    Prenotazione p = new Prenotazione(
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("carta_identita"),
                            "00"
                    );
                    p.setBagagli(bagagli);

                    prenotazioni.add(p);

                    rs2.close();
                }
            }
            rs.close();
        }

        return prenotazioni;
    }

}
