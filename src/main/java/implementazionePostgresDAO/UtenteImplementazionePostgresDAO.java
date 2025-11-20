package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.*;

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
        String sql2 = "SELECT posto FROM associa WHERE id_prenotazione = ?";


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
                    }

                    prenotazioniFinali.add(p);
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prenotazioniFinali;
    }



}
