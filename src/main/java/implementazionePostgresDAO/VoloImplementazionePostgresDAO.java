package implementazionePostgresDAO;

import dao.VoloDAO;
import database.ConnessioneDatabase;
import model.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Implementazione dell'interfaccia {@link VoloDAO}.
 */
public class VoloImplementazionePostgresDAO implements VoloDAO {

    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al db.
     */
    public VoloImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserisce un nuovo volo nel DB.
     * Il metodo, dopo aver inserito i dati del nuovo volo, recupera il codiceVolo generato automaticamente dal database
     * per poter poi utilizzare il valore in altri metodi del controller
     *
     * @param volo       Il nuovo volo da inserire.
     * @param numeroGate Il numero del gate da assegnare al volo.
     * @return Il nuovo codiceVolo generato dal db.
     * @throws SQLException Se si verifica un errore nella query SQL.
     */
    @Override
    public String inserisciVolo(Volo volo, String numeroGate) throws SQLException {

            try(PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO volo (compagnia_aerea,stato_volo, origine, destinazione, data_aereo, ora_aereo, ritardo, gate,  email_utente) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            )){
                ps.setString(1, volo.getCompagniaAerea());
                if(volo.getRitardo() == 0){
                    ps.setObject(2, volo.getStatoVolo().name(), Types.OTHER);
                }else{
                    ps.setObject(2, StatoVolo.IN_RITARDO, Types.OTHER);
                }


                // ------------------ DATA e ORA ------------------

                DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

                LocalDate data = LocalDate.parse(volo.getData(), formatterData);
                LocalTime ora = LocalTime.parse(volo.getOrarioPrevisto(), formatterOra);

                LocalDateTime dataOraInput = LocalDateTime.of(data, ora);

                LocalDateTime adesso = LocalDateTime.now();

                if (dataOraInput.isBefore(adesso)) {
                    if(volo.getOrigine().toLowerCase().equalsIgnoreCase("Napoli"))
                        ps.setObject(2, StatoVolo.DECOLLATO, Types.OTHER);
                    else ps.setObject(2, StatoVolo.ATTERRATO, Types.OTHER);
                }



                ps.setString(3, volo.getOrigine());
                ps.setString(4, volo.getDestinazione());
                ps.setString(5, volo.getData());
                ps.setString(6, volo.getOrarioPrevisto());
                ps.setInt(7, volo.getRitardo());
                if(numeroGate != null){
                    ps.setInt(8, Integer.parseInt(numeroGate));
                }else{
                    ps.setObject(8, null);
                }
                ps.setString(9, volo.getAmministratore().getEmail());


                ps.executeUpdate();


                ResultSet codiceCreato = ps.getGeneratedKeys();
                String nuovoCodiceVolo = null;
                if(codiceCreato.next()){
                    nuovoCodiceVolo = String.valueOf(codiceCreato.getInt("codice_volo"));
                }


                codiceCreato.close();

                return nuovoCodiceVolo;
            }
    }

    /**
     * Aggiorna i dati su un volo esistente nel DB.
     *
     * @param volo Il volo da aggiornare.
     * @return L'esito dell'operazione.
     * @throws SQLException Se si verifica un errore nella query SQL.
     */
    @Override
    public boolean aggiornaVolo(Volo volo) throws SQLException {

        try(PreparedStatement ps = connection.prepareStatement(
                "UPDATE volo SET data_aereo=?, ora_aereo=?, ritardo=?, stato_volo=?, gate=? WHERE codice_volo=?"
        )){
            ps.setString(1, volo.getData());
            ps.setString(2, volo.getOrarioPrevisto());
            ps.setInt(3, volo.getRitardo());
            if(volo.getRitardo() == 0){
                ps.setObject(4, String.valueOf(StatoVolo.PROGRAMMATO), Types.OTHER);
            }else{
                ps.setObject(4, String.valueOf(StatoVolo.IN_RITARDO), Types.OTHER);
            }

            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate data = LocalDate.parse(volo.getData(), formatterData);
            LocalTime ora = LocalTime.parse(volo.getOrarioPrevisto(), formatterOra);

            LocalDateTime dataOraInput = LocalDateTime.of(data, ora);

            LocalDateTime adesso = LocalDateTime.now();

            if (dataOraInput.isBefore(adesso)) {
                if(volo.getOrigine().toLowerCase().equalsIgnoreCase("Napoli"))
                    ps.setObject(4, StatoVolo.DECOLLATO, Types.OTHER);
                else ps.setObject(4, StatoVolo.ATTERRATO, Types.OTHER);
            }

            if(volo.getGate() == null){
                ps.setObject(5, null);
            }else{
                ps.setInt(5, volo.getGate().getNumero());
            }
            ps.setInt(6, Integer.parseInt(volo.getCodiceVolo()));
            ps.executeUpdate();
            return true;
        }
    }


    /**
     * Modifica lo stato del volo a CANCELLATO.
     * Il metodo esegue la modifica dello stato del volo.
     * Dopodichè effettua una ricerca di tutte le prenotazioni associate al volo cancellato
     * e ritorna un ArrayList del risultato.
     *
     * @param codiceVolo Il codice volo del volo da eliminare.
     * @param gate       Il gate associato al volo eliminato.
     * @return           Un ArrayList contenente l'id delle prenotazioni associate al volo da cancellare.
     * @throws SQLException Se si verifica un errore nella query SQL
     */
    @Override
    public ArrayList<Integer> eliminaVolo(String codiceVolo, Gate gate) throws SQLException {
        ArrayList<Integer> prenotazioniDaCancellare = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement("UPDATE volo SET stato_volo = ?, gate = null WHERE codice_volo=?")){
            ps.setObject(1, StatoVolo.CANCELLATO,  Types.OTHER);
            ps.setInt(2, Integer.parseInt(codiceVolo));


            try(PreparedStatement ps3 = connection.prepareStatement("SELECT id_prenotazione FROM associa WHERE codice_volo = ?")){
                ps3.setInt(1, Integer.parseInt(codiceVolo));
                ResultSet rs = ps3.executeQuery();
                while(rs.next()){
                    prenotazioniDaCancellare.add(rs.getInt("id_prenotazione"));
                }

                rs.close();

                ps.executeUpdate();

                return prenotazioniDaCancellare;
            }
        }
    }


    /**
     * Recupera tutti i voli dal DB.
     *
     * @return Un ArrayList contenente tutti i voli nel DB
     * @throws SQLException Se si verifica un errore nella query SQL
     */
    @Override
    public ArrayList<Volo> getVoliDB() throws SQLException{
        ArrayList<Volo> voli = new ArrayList<>();
        String sql = "SELECT * FROM volo";


            try(PreparedStatement st = connection.prepareStatement(sql)){
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Volo v = new Volo(String.valueOf(rs.getInt("codice_volo")),
                            rs.getString("compagnia_aerea"),
                            rs.getString("origine"),
                            rs.getString("destinazione"),
                            rs.getString("data_aereo"),
                            rs.getString("ora_aereo"),
                            rs.getInt("ritardo"));
                    if(rs.getObject("gate") != null) v.setGate(new Gate(rs.getInt("gate")));
                    StatoVolo s = StatoVolo.valueOf(rs.getString("stato_volo"));
                    v.setStatoVolo(s);

                    voli.add(v);
                }

                rs.close();
                return voli;
            }
    }




}