package dao;

import model.Gate;

import java.sql.SQLException;

public interface GateDAO{

    boolean assegnaGate(String codiceVolo, int numeroGate) throws SQLException;
    boolean modificaGate(String codiceVolo, int nuovoGate) throws SQLException;
    Gate getGateByVolo(String codiceVolo) throws SQLException;

}