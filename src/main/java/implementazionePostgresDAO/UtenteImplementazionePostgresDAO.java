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


    public ArrayList<Prenotazione> getPrenotazioniDB(String email_utente) {
        ArrayList<Prenotazione> prenotazioniFinali = new ArrayList<>();

        String sql = "SELECT * FROM prenotazione WHERE email_utente = ? ";
        String sql2 = "SELECT * FROM associa JOIN volo ON volo.codice_volo = associa.codice_volo" + " WHERE id_prenotazione = ?";
        String sql3 = "SELECT * FROM bagaglio WHERE id_prenotazione = ? ";

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prenotazioniFinali;
    }


    public boolean effettuaPrenotazioneDB(String codiceVolo, String nome, String cognome, String cid, String posto, String email_utente, int numeroBagagli){
        String sql = "INSERT INTO prenotazione(nome, cognome, carta_identita, email_utente, stato_prenotazione) VALUES (?,?,?,?,?)";
        String sql2 = "INSERT INTO associa(codice_volo, posto, id_prenotazione) VALUES (?, ?, ?)";
        String sql3;

        try{
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



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }


    public ArrayList<String> getPostiOccupatiDB(String codiceVolo){
        String sql = "SELECT posto FROM associa WHERE codice_volo = ?";
        ArrayList<String> postiTrovati = new ArrayList<>();

        try{
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, Integer.parseInt(codiceVolo));
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                postiTrovati.add(rs.getString("posto"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return postiTrovati;
    }

    public boolean modificaPrenotazioneDB(String codiceVolo,String nome,String cognome,String cartaIdentita,String idPrenotazione, String nuovoPostoScelto){
        String sql = "UPDATE prenotazione SET nome = ?, cognome = ?, carta_identita = ? WHERE id = ?";
        String sql2 = "UPDATE associa SET posto = ? WHERE id_prenotazione = ?";

        try{

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean cancellaPrenotazioneDB(String idPrenotazione){
        String sql = "DELETE FROM prenotazione WHERE id = ?";

        try{
            PreparedStatement st = connection.prepareStatement(sql);


            st.setInt(1, Integer.parseInt(idPrenotazione));

            st.executeUpdate();
            st.close();
            return true;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }



}
