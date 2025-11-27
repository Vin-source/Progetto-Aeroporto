package dao;

import model.Gate;
import model.Volo;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface VoloDAO{

    String inserisciVolo(Volo volo, String numeroGate) throws SQLException;
    boolean aggiornaVolo(Volo volo) throws SQLException;
    ArrayList<Integer> eliminaVolo(String codiceVolo, Gate gate) throws SQLException;
    ArrayList<Volo> getVoliDB() throws SQLException;
}