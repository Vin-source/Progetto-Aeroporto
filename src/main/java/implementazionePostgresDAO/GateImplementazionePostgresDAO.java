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
        PreparedStatement ps;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "UPDATE gate SET codice_volo = ? WHERE numero_gate = ?"
        );

        ps.setInt(1, Integer.parseInt(codiceVolo));
        ps.setInt(2, numeroGate);
        boolean res = ps.executeUpdate() > 0;
        ps.close();
        return res;
    }



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