package dao;

import model.Gate;
import model.Volo;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface VoloDAO{

    boolean inserisciVolo(Volo volo) throws SQLException;
    boolean aggiornaVolo(Volo volo) throws SQLException;
    boolean eliminaVolo(String codiceVolo) throws SQLException;
    Volo getVoloByCodice(String codiceVolo) throws SQLException;
    ArrayList<Volo> getVoliDB() throws SQLException;
    ArrayList<Volo> getVoloByValore(String valore) throws SQLException;
}