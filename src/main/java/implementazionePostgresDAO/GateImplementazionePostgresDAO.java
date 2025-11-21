package implementazionePostgresDAO;

import dao.GateDAO;
import database.ConnessioneDatabase;
import model.Gate;

import java.sql.*;

public class GateImplementazionePostgresDAO implements GateDAO {

    @Override
    public boolean assegnaGate(String codiceVolo, int numeroGate) throws SQLException {
        PreparedStatement ps = null;

        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "INSERT INTO gates (codice_volo, numero) VALUES (?, ?)"
        );
        ps.setString(1, codiceVolo);
        ps.setInt(2, numeroGate);
        return ps.executeUpdate() > 0;
    }

    @Override
    public boolean modificaGate(String codiceVolo, int nuovoGate) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = ConnessioneDatabase.getInstance().connection;
        ps = connection.prepareStatement(
                "UPDATE gates SET numero=? WHERE codice_volo=?"
        );
        ps.setInt(1, nuovoGate);
        ps.setString(2, codiceVolo);
        return ps.executeUpdate() > 0;
    }

    @Override
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
                    rs.getInt("numero")
            );
        }
        return null;
    }
}