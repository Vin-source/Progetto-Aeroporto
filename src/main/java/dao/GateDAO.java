package dao;

import model.Gate;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GateDAO{

    boolean assegnaGate(String codiceVolo, int numeroGate) throws SQLException;
    boolean modificaGate(String codiceVolo, int nuovoGate) throws SQLException;
   // Gate getGateByVolo(String codiceVolo) throws SQLException;
    ArrayList<Gate> getTuttiGate() throws SQLException;
}