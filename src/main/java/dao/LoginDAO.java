package dao;


//import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.ArrayList;

public interface LoginDAO {


    // Verifica le credenziali di accesso e ritorna il ruolo dell'utente.
//    String loginDB(String username, String password) throws SQLException;

    public String getUtentiDB(String email, String password) throws SQLException;
    // public String getIdOspite(String email, String password) throws SQLException;
}