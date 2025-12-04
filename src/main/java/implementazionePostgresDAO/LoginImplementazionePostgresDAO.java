package implementazionePostgresDAO;

import dao.LoginDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link LoginDAO}.
 */
public class LoginImplementazionePostgresDAO implements LoginDAO {

    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al db.
     */
    public LoginImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Verifica le credenziali di accesso al db.
     *  Prende come parametri l'email e la password inseriti dall'utente
     *  Cerca se l'email e password coincidono nel database
     *  In caso di esito positivo viene ritornato il ruolo dello specifico utente
     *  (se amministratore o utente generico)
     *
     * @param email    L'indirizzo email dell'utente.
     * @param password La password dell'utente.
     * @return il ruolo dell'utente se trovato.
     * @throws SQLException Se si verifica un errore di query SQL.
     */
    @Override
    public String getUtentiDB(String email, String password) throws SQLException{

        String sql = "SELECT ruolo FROM utente WHERE email = ? AND password = ?";

            try(PreparedStatement st = connection.prepareStatement(sql)){
                st.setString(1, email);
                st.setString(2, password);

                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    return rs.getString("ruolo"); // "amministratore" o "utente"
                }

                rs.close();

                return "utente o amministratore non trovato";
            }
    }
}

