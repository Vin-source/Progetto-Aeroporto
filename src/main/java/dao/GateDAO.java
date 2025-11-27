package dao;

import model.Gate;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GateDAO{

    boolean assegnaGate(String codiceVolo, int numeroGate) throws SQLException;
   // Gate getGateByVolo(String codiceVolo) throws SQLException;
    ArrayList<String> getGateDisponibiliDAO() throws SQLException;
    void setCodiceVoloNull(int codiceVolo) throws SQLException;
}