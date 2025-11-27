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


        ps = connection.prepareStatement(
                "UPDATE gate SET codice_volo = ? WHERE numero_gate = ?"
        );

        ps.setInt(1, Integer.parseInt(codiceVolo));
        ps.setInt(2, numeroGate);
        boolean res = ps.executeUpdate() > 0;


        ps.close();

        return res;
    }



    public ArrayList<String> getGateDisponibiliDAO() throws SQLException {
        ArrayList<String> listaGates = new ArrayList<>();

        String sql = "SELECT numero_gate FROM gate WHERE codice_volo IS null ORDER BY numero_gate ASC";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int numero = rs.getInt("numero_gate");
            listaGates.add(String.valueOf(numero));
        }

        rs.close();
        stmt.close();


        return listaGates;
    }

    public void setCodiceVoloNull(int codiceVolo) throws SQLException{
        PreparedStatement ps = null;

        ps = connection.prepareStatement("UPDATE gate SET codice_volo = null WHERE codice_volo = ?");
        ps.setInt(1, codiceVolo);

        ps.executeUpdate();

        ps.close();
    }
}