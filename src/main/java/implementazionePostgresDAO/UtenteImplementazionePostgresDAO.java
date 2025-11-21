package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.*;

import javax.xml.transform.Result;
import java.sql.*;
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

        try{
            PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
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
        return voli;
    }

    public ArrayList<Prenotazione> getPrenotazioniDB(String email_utente) {
        ArrayList<Prenotazione> prenotazioniFinali = new ArrayList<>();

        String sql = "SELECT * FROM prenotazione WHERE email_utente = ? ";
        String sql2 = "SELECT * FROM associa JOIN volo ON volo.codice_volo = associa.codice_volo" + " WHERE id_prenotazione = ?";


        try{
            PreparedStatement prenotazioniSQL = connection.prepareStatement(sql);
            prenotazioniSQL.setString(1, email_utente);

            ResultSet prenotazioni = prenotazioniSQL.executeQuery();
                while(prenotazioni.next()){
                    Prenotazione p = new Prenotazione(prenotazioni.getString("nome"),
                            prenotazioni.getString("cognome"),
                            prenotazioni.getString("carta_identita"), "00");
                    p.setIdPrenotazione(String.valueOf(prenotazioni.getInt("id")));
                    p.setStatoPrenotazione(StatoPrenotazione.valueOf(prenotazioni.getString("stato_prenotazione")));


                    PreparedStatement postoDB = connection.prepareStatement(sql2);
                    postoDB.setInt(1, prenotazioni.getInt("id"));

                    ResultSet postoRisultante = postoDB.executeQuery();
                    if (postoRisultante.next()){
                        p.setPostoAssegnato(postoRisultante.getString("posto"));
                        Volo v = new Volo(String.valueOf(postoRisultante.getInt("codice_volo")),
                                postoRisultante.getString("compagnia_aerea"),
                                postoRisultante.getString("origine"),
                                postoRisultante.getString("destinazione"),
                                postoRisultante.getString("data_aereo"),
                                postoRisultante.getString("ora_aereo"),
                                postoRisultante.getInt("ritardo"));
                        StatoVolo s = StatoVolo.valueOf(postoRisultante.getString("stato_volo"));
                        v.setStatoVolo(s);
                        p.setVolo(v);
                    }

                    prenotazioniFinali.add(p);
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prenotazioniFinali;
    }


    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli){
        String sql = "INSERT INTO prenotazione(nome, cognome, carta_identita, email_utente, stato_prenotazione) VALUES (?,?,?,?,?)";
        String sql2 = "INSERT INTO associa(codice_volo, posto, id_prenotazione) VALUES (?, ?, ?)";
        String sql3 = "INSERT INTO bagaglio(id_prenotazione) VALUES (?)";

        try{
            PreparedStatement prenotazioneSQL = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement associaSQL = connection.prepareStatement(sql2);
            PreparedStatement bagaglioSQL = connection.prepareStatement(sql3);


            prenotazioneSQL.setString(1, nome);
            prenotazioneSQL.setString(2, cognome);
            prenotazioneSQL.setString(3, cid);
            prenotazioneSQL.setString(4, email_utente);
            prenotazioneSQL.setObject(5, "CONFERMATA", Types.OTHER);

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

            bagaglioSQL.setInt(1, nuovoId);
            bagaglioSQL.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }



}
