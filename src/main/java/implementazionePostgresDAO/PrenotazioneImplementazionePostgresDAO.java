package implementazionePostgresDAO;

import dao.PrenotazioneDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.ArrayList;

public class PrenotazioneImplementazionePostgresDAO implements PrenotazioneDAO {


    private Connection connection;

    public PrenotazioneImplementazionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli) throws  SQLException{
        String sql = "INSERT INTO prenotazione(nome, cognome, carta_identita, email_utente, stato_prenotazione) VALUES (?,?,?,?,?)";
        String sql2 = "INSERT INTO associa(codice_volo, posto, id_prenotazione) VALUES (?, ?, ?)";
        String sql3;


            PreparedStatement prenotazioneSQL = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement associaSQL = connection.prepareStatement(sql2);


            prenotazioneSQL.setString(1, nome);
            prenotazioneSQL.setString(2, cognome);
            prenotazioneSQL.setString(3, cid);
            prenotazioneSQL.setString(4, email_utente);
            prenotazioneSQL.setObject(5, "IN_ATTESA", Types.OTHER);

            prenotazioneSQL.executeUpdate();

            ResultSet rs = prenotazioneSQL.getGeneratedKeys();
            int nuovoId = 0;
            if(rs.next()){
                nuovoId = rs.getInt(1);
            }


            associaSQL.setInt(1, Integer.parseInt(codiceVolo));
            associaSQL.setString(2, posto);
            associaSQL.setInt(3, nuovoId);

            associaSQL.executeUpdate();

            for(int i = 0; i < numeroBagagli; i++) {
                sql3 = "INSERT INTO bagaglio(id_prenotazione) VALUES (?)";
                PreparedStatement bagaglioSQL = connection.prepareStatement(sql3);
                bagaglioSQL.setInt(1, nuovoId);
                bagaglioSQL.executeUpdate();
                bagaglioSQL.close();
            }

            rs.close();
            prenotazioneSQL.close();
            associaSQL.close();





        return true;
    }


    public ArrayList<String> getPostiOccupatiDB(String codiceVolo) throws SQLException{
        String sql = "SELECT posto FROM associa WHERE codice_volo = ?";
        ArrayList<String> postiTrovati = new ArrayList<>();


            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Integer.parseInt(codiceVolo));
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                postiTrovati.add(rs.getString("posto"));
            }

            rs.close();
            st.close();

        return postiTrovati;
    }

    public boolean modificaPrenotazioneDB(String codiceVolo,String nome,String cognome,String cartaIdentita,String idPrenotazione, String nuovoPostoScelto) throws SQLException{
        String sql = "UPDATE prenotazione SET nome = ?, cognome = ?, carta_identita = ? WHERE id = ?";
        String sql2 = "UPDATE associa SET posto = ? WHERE id_prenotazione = ?";


            PreparedStatement st = connection.prepareStatement(sql);
            PreparedStatement st2 = connection.prepareStatement(sql2);

            st.setString(1, nome);
            st.setString(2, cognome);
            st.setString(3, cartaIdentita);
            st.setInt(4, Integer.parseInt(idPrenotazione));

            st.execute();

            st2.setString(1, nuovoPostoScelto);
            st2.setInt(2, Integer.parseInt(idPrenotazione));

            st2.execute();

            st.close();
            st2.close();



        return true;
    }

    public boolean cancellaPrenotazioneDB(String idPrenotazione) throws SQLException{
        String sql = "UPDATE prenotazione SET stato_prenotazione = 'CANCELLATA' WHERE id = ?";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(idPrenotazione));

            st.executeUpdate();
            st.close();
            return true;
    }

}
