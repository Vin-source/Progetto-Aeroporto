package dao;

import model.Volo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAO {
    public ArrayList<Volo> getVoliDB() throws SQLException;
}
