package implementazionePostgresDAO;

import dao.VoloDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class VoloImplementazionePostgresDAO implements VoloDAO {

    private Connection connection;

    public VoloImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String inserisciVolo(Volo volo, String numeroGate) throws SQLException {
        PreparedStatement ps = null;


            ps = connection.prepareStatement(
                    "INSERT INTO volo (compagnia_aerea,stato_volo, origine, destinazione, data_aereo, ora_aereo, ritardo, gate,  email_utente) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, volo.getCompagniaAerea());
            ps.setObject(2, volo.getStatoVolo().name(), Types.OTHER);
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
        ps.close();

        return nuovoCodiceVolo;

    }

    @Override
    public boolean aggiornaVolo(Volo volo) throws SQLException {
        PreparedStatement ps = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "UPDATE volo SET data_aereo=?, ora_aereo=?, ritardo=?, stato_volo=?, gate=? WHERE codice_volo=?"
        );



        ps.setString(1, volo.getData());
        ps.setString(2, volo.getOrarioPrevisto());
        ps.setInt(3, volo.getRitardo());
        ps.setObject(4, String.valueOf(StatoVolo.PROGRAMMATO), Types.OTHER);
        if(volo.getGate() == null){
            ps.setObject(5, null);
        }else{
            ps.setInt(5, volo.getGate().getNumero());
        }
        ps.setInt(6, Integer.parseInt(volo.getCodiceVolo()));
        ps.executeUpdate();
        ps.close();
        return true;

    }

    @Override
    public ArrayList<Integer> eliminaVolo(String codiceVolo, Gate gate) throws SQLException {
        ArrayList<Integer> prenotazioniDaCancellare = new ArrayList<>();

        PreparedStatement ps = null;
        PreparedStatement ps3 = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement("UPDATE volo SET stato_volo = ?, gate = null WHERE codice_volo=?");
        ps.setObject(1, StatoVolo.CANCELLATO,  Types.OTHER);
        ps.setInt(2, Integer.parseInt(codiceVolo));


        ps3 = connection.prepareStatement("SELECT id_prenotazione FROM associa WHERE codice_volo = ?");
        ps3.setInt(1, Integer.parseInt(codiceVolo));
        ResultSet rs = ps3.executeQuery();
        while(rs.next()){
            prenotazioniDaCancellare.add(rs.getInt("id_prenotazione"));
        }

        rs.close();
        ps3.close();

        ps.executeUpdate();
        ps.close();

        return prenotazioniDaCancellare;
    }


    @Override
    public ArrayList<Volo> getVoliDB() throws SQLException{
        ArrayList<Volo> voli = new ArrayList<>();
        String sql = "SELECT * FROM volo";


            PreparedStatement st = connection.prepareStatement(sql);
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
            st.close();
        return voli;
    }




}