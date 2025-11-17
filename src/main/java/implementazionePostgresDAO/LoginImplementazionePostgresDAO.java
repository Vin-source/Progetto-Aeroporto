package implementazionePostgresDAO;

import dao.LoginDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginImplementazionePostgresDAO implements LoginDAO {

    private Connection connection;

    public LoginImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUtentiDB(String email, String password) throws SQLException {

        String sql = "SELECT ruolo FROM utenti WHERE email = ? AND password = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, email);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("ruolo"); // "amministratore" o "utente"
                }

            }
        }

        return null;
    }

    @Override
    public String getIdOspite(String email, String password) throws SQLException {

        String sql = "SELECT id FROM utenti WHERE email = ? AND password = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, email);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("id");
                }

            }
        }

        return null;
    }

}

