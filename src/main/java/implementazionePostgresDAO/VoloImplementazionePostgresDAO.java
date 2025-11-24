package implementazionePostgresDAO;

import dao.VoloDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.*;
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
    public boolean inserisciVolo(Volo volo) throws SQLException {
        PreparedStatement ps = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "INSERT INTO voli (compagnia_aerea,stato_volo, origine, destinazione, data_aereo, ora_aereo, ritardo, gate) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        );

        ps.setString(1, volo.getCompagniaAerea());
        ps.setString(2, volo.getStatoVolo().name());
        ps.setString(3, volo.getOrigine());
        ps.setString(4, volo.getDestinazione());
        ps.setDate(5, Date.valueOf(volo.getData()));
        ps.setTime(6, Time.valueOf(volo.getOrarioPrevisto()));
        ps.setInt(7, volo.getRitardo());
        ps.setInt(8, volo.getGate().getNumero());
       // ps.setString(9, volo.getEmail());
        boolean res = ps.executeUpdate() > 0;
        ps.close();
        return res;

    }

    @Override
    public boolean aggiornaVolo(Volo volo) throws SQLException {
        PreparedStatement ps = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "UPDATE voli SET data_aereo=?, ora_aereo=?, ritardo=?, stato_volo=?, gate=? WHERE codice_volo=?"
        );

        ps.setDate(1, Date.valueOf(volo.getData()));
        ps.setTime(2, Time.valueOf(volo.getOrarioPrevisto()));
        ps.setInt(3, volo.getRitardo());
        ps.setString(4, volo.getStatoVolo().name());
        ps.setInt(5, volo.getGate().getNumero());
        ps.setString(6, volo.getCodiceVolo());
        //ps.setInt(6, Integer.parseInt(volo.getCodiceVolo()));
        boolean res = ps.executeUpdate()>0;
        ps.close();
        return res;


       // return true;
    }

    @Override
    public boolean eliminaVolo(String codiceVolo) throws SQLException {
        PreparedStatement ps = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement("DELETE FROM voli WHERE codice_volo=?");
        ps.setString(1, codiceVolo);
        boolean res = ps.executeUpdate() > 0;
        ps.close();
        return res;
    }

    @Override
    public Volo getVoloByCodice(String codiceVolo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;

        java.time.format.DateTimeFormatter dataFormattata = java.time.format.DateTimeFormatter.ofPattern("d/MM/yyyy");
        java.time.format.DateTimeFormatter oraFormattata = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

        ps = connection.prepareStatement("SELECT * FROM voli WHERE codice_volo=?");
        ps.setString(1, codiceVolo);
        rs = ps.executeQuery();
        if (rs.next()) {

            String dataStringa = rs.getDate("data_aereo").toLocalDate().format(dataFormattata);
            String oraStringa = rs.getTime("ora_aereo").toLocalTime().format(oraFormattata);

            return new Volo(
                    rs.getString("codice_volo"),
                    rs.getString("compagnia_aerea"),
                    rs.getString("origine"),
                    rs.getString("destinazione"),
                    //rs.getDate("data").toLocalDate(),
                    //rs.getTime("orario").toLocalTime(),
                    dataStringa,
                    oraStringa,
                    rs.getInt("ritardo"));
        }

        rs.close();
        ps.close();

        return null;
    }

    @Override
    public ArrayList<Volo> getVoliDB() {
        ArrayList<Volo> voli = new ArrayList<>();
        String sql = "SELECT * FROM VOLO";

        try{
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
                StatoVolo s = StatoVolo.valueOf(rs.getString("stato_volo"));
                v.setStatoVolo(s);

                voli.add(v);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return voli;
    }


    public ArrayList<Volo> getVoloByValore(String valore) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Volo> res = new ArrayList<>();

        java.time.format.DateTimeFormatter dataFormattata = java.time.format.DateTimeFormatter.ofPattern("d/MM/yyyy");
        java.time.format.DateTimeFormatter oraFormattata = java.time.format.DateTimeFormatter.ofPattern("HH:mm");

        Connection connection = ConnessioneDatabase.getInstance().connection;

        ps = connection.prepareStatement("SELECT * FROM voli WHERE codice_volo=? OR compagnia_aerea=? OR origine=? OR destinazione=?");
        ps.setString(1, valore);
        ps.setString(2, valore);
        ps.setString(3, valore);
        ps.setString(4, valore);
        rs = ps.executeQuery();
        while (rs.next()) {
            String dataStringa = rs.getDate("data_aereo").toLocalDate().format(dataFormattata);
            String oraStringa = rs.getTime("ora_aereo").toLocalTime().format(oraFormattata);
            Volo v = new Volo(
                    rs.getString("codice_volo"),
                    rs.getString("compagnia_aerea"),
                    rs.getString("origine"),
                    rs.getString("destinazione"),
                    //rs.getDate("data").toLocalDate(),
                    //rs.getTime("orario").toLocalTime(),
                    dataStringa,
                    oraStringa,
                    rs.getInt("ritardo"));
            res.add(v);
        }

        rs.close();
        ps.close();

        return res;
    }


}