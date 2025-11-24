package implementazionePostgresDAO;

import dao.GateDAO;
import database.ConnessioneDatabase;
import model.Gate;

import java.sql.*;
import java.util.ArrayList;

public class GateImplementazionePostgresDAO implements GateDAO {

    private Connection connection;

    public GateImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean assegnaGate(String codiceVolo, int numeroGate) throws SQLException {
        PreparedStatement ps = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "INSERT INTO gate (numero_gate, codice_volo) VALUES (?, ?)"
        );

        ps.setInt(1, numeroGate);
        ps.setInt(2, Integer.parseInt(codiceVolo));
        boolean res = ps.executeUpdate() > 0;
        ps.close();
        return res;
    }

    @Override
    public boolean modificaGate(String codiceVolo, int nuovoGate) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "UPDATE gate SET numero_gate=? WHERE codice_volo=?"
        );
        ps.setInt(1, nuovoGate);
        ps.setString(2, codiceVolo);
        boolean res = ps.executeUpdate() > 0;
        ps.close();
        return res;
    }

  /*  @Override
    public Gate getGateByVolo(String codiceVolo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "SELECT * FROM gates WHERE codice_volo=?"
        );
        ps.setString(1, codiceVolo);
        rs = ps.executeQuery();
        if (rs.next()) {
            return new Gate(
                    rs.getInt("numero_gate")
            );
        }
        rs.close();
        ps.close();
        return null;
    }
    */

    @Override
    public ArrayList<Gate> getTuttiGate() throws SQLException {
        ArrayList<Gate> listaGates = new ArrayList<>();

        String sql = "SELECT DISTINCT numero_gate FROM gate ORDER BY numero_gate ASC";

        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int numero = rs.getInt("numero_gate");
                listaGates.add(new Gate(numero));
            }

            rs.close();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }



        return listaGates;
    }
}