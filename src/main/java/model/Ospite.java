package model;

public class Ospite {
    protected String id;
    protected String email;
    protected String password;


    public Ospite(String email, String password) {
        this.email = email;
        this.password = password;
    }



    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}


