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


    public ArrayList<Prenotazione> getPrenotazioniDB(String email_utente) throws SQLException {
        ArrayList<Prenotazione> prenotazioniFinali = new ArrayList<>();

        String sql = "SELECT * FROM prenotazione WHERE email_utente = ? ";
        String sql2 = "SELECT * FROM associa JOIN volo ON volo.codice_volo = associa.codice_volo" + " WHERE id_prenotazione = ?";
        String sql3 = "SELECT * FROM bagaglio WHERE id_prenotazione = ? ";

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

                    PreparedStatement bagaglioSQL =  connection.prepareStatement(sql3);
                    bagaglioSQL.setInt(1, Integer.parseInt(p.getIdPrenotazione()));

                    ResultSet bagagliRisultanti = bagaglioSQL.executeQuery();
                    ArrayList<Bagaglio> bagagliTrovati = new ArrayList<>();

                    while(bagagliRisultanti.next()){
                        Bagaglio b = new Bagaglio(bagagliRisultanti.getInt("codice_bagaglio"));
                        b.setPrenotazione(p);

                        bagagliTrovati.add(b);
                    }


                    p.setBagagli(bagagliTrovati);

                    prenotazioniFinali.add(p);

                    bagagliRisultanti.close();
                    postoRisultante.close();
                    bagaglioSQL.close();
                    postoDB.close();
                }


                prenotazioni.close();
                prenotazioniSQL.close();

        return prenotazioniFinali;
    }




}
