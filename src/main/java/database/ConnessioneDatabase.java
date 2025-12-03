package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {
    private static ConnessioneDatabase instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "admin";
    private String url = "jdbc:postgresql://localhost:5432/aeroporto_db";
    private String driver = "org.postgresql.Driver";
    // per semplicità, username e password sono stati inseriti nel codice
    // NON È UNA SOLUZIONE GENERALE E POSSIBILE, ANDRÀ CAMBIATA
    // CONTROLLARE ANCHE LE PORTE (sono di default in postgres 5432 o 5433)


    private ConnessioneDatabase() throws SQLException {
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);
        } catch(ClassNotFoundException ex){
            System.out.println("Database connection Creation failed!: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if(instance == null){
            instance = new ConnessioneDatabase();
        }else if(instance.connection.isClosed()){
            return instance; // ho cambiato con return instance invece di
            // instance = new ConnessioneDatabase();
        }
        return instance;
    }

    //ritorna connessione
    // public Connection getConnection() {
    //    return connection;
    //}
    // questo metodo è inutile.
    // il ritorno alla connessione (come scritto nelle slide) è eseguito chiamando
    // nel file qualcosa.implementazionePostgresDAO
    // connection = ConnessioneDatabase.getInstance().connection
    // senza dover ritornare nessun valore


    //aggiunto questo metodo per chiudere connessione
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    // anche questo metodo potrebbe essere eliminato
    // essendo il metodo close direttamente implementato in connection
    // e non avendo bisogno di controllare valori nulli o chiusi visto che
    // la variabile connessione verrà comunque unicamente utilizzata per
    // accedere al database, quindi in una connessione essenzialmente sempre sicura
    // però non escludo si possa mantenere comunque per semplicità, ma richiederebbe di
    // passare il valore dal file implementazionePostgresDAO in ConnessioneDatabase
    // Che potrebbe comportare problemi nello staccare il contatto tra oggetto connessione
    // e il resto dei package

}