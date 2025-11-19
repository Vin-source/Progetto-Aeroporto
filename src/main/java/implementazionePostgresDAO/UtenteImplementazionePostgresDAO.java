package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.StatoVolo;
import model.Volo;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    private Connection connection;

    public UtenteImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Volo> getVoliDB(){
        ArrayList<Volo> voli = new ArrayList<>();
        String sql = "SELECT * FROM VOLO";

        try(PreparedStatement st = connection.prepareStatement(sql)){
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    Volo v = new Volo(String.valueOf(rs.getInt("codice_volo")),
                            rs.getString("compagnia_aerea"),
                            rs.getString("origine"),
                            rs.getString("destinazione"),
                            rs.getString("data_aereo"),
                            rs.getString("ora_aereo"),
                            rs.getInt("ritardo"));
                    StatoVolo s = StatoVolo.valueOf(rs.getString("stato_volo"));
                    v.setStatoVolo(s);

                    voli.add(v);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return voli;
    }



}
