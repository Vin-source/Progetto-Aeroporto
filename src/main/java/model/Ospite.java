package model;


/**
 *   Il tipo ospite che entra nel sistema
 */
public class Ospite {
    protected String email;
    protected String password;


    /**
     * Costruttore della classe Ospite.java
     * @param email email dell'utente\amministratore registrato
     * @param password password dell'utente\amministratore registrato
     */
    public Ospite(String email, String password) {
        this.email = email;
        this.password = password;
    }



    /**
     * @return email dell'utente\amministratore
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return password dell'utente\amministratore
     */
    public String getPassword() {
        return this.password;
    }
}


