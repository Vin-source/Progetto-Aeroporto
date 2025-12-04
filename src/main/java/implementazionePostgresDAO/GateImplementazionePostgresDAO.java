package implementazionePostgresDAO;

import dao.GateDAO;
import database.ConnessioneDatabase;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementazione dell'interfaccia {@link GateDAO}.
 */
public class GateImplementazionePostgresDAO implements GateDAO {

    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al db.
     */
    public GateImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Assegna un volo ad un gate.
     *
     * @param codiceVolo Il codice del volo da assegnare al gate.
     * @param numeroGate Il numero del gate alla quale è assegnato il volo.
     * @return L'esito dell'operazione.
     * @throws SQLException Se si verifica un problema durante l'operazione sul db.
     */
    @Override
    public boolean assegnaGate(String codiceVolo, int numeroGate) throws SQLException {

        try(PreparedStatement ps = connection.prepareStatement(
                "UPDATE gate SET codice_volo = ? WHERE numero_gate = ?"
        )){
            ps.setInt(1, Integer.parseInt(codiceVolo));
            ps.setInt(2, numeroGate);
            boolean res = ps.executeUpdate() > 0;

            return res;
        }


    }


    /**
     * Recupera tutti i gate che non sono assegnati ad un volo.
     *
     * @return Un'Arraylist dei gate disponibili.
     * @throws SQLException Se si verifica un errore nel recupero dei gate dal db.
     */
    public ArrayList<String> getGateDisponibiliDAO() throws SQLException {
        ArrayList<String> listaGates = new ArrayList<>();

        String sql = "SELECT numero_gate FROM gate WHERE codice_volo IS null ORDER BY numero_gate ASC";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int numero = rs.getInt("numero_gate");
                listaGates.add(String.valueOf(numero));
            }

            rs.close();
            return listaGates;
        }

    }

    /**
     * Elimina il riferimento al volo in un determinato gate
     *
     * @param codiceVolo Il codice del volo di cui liberare il gate.
     * @throws SQLException Se si verifica un errore durante l'assegnazione nel db.
     */
    public void setCodiceVoloNull(int codiceVolo) throws SQLException{

        try(PreparedStatement ps = connection.prepareStatement("UPDATE gate SET codice_volo = null WHERE codice_volo = ?")){
            ps.setInt(1, codiceVolo);
            ps.executeUpdate();
        }

    }
}