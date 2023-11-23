package Model;

import java.util.ArrayList;

public class Client {

    private String name;
    private String surname;
    private String email;
    private String mdp;
    private Droit droit;

    public Client(String name, String surname, String email, String mdp) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.mdp = mdp;
        this.droit = Droit.CLASSIQUE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Droit getDroit() {
        return droit;
    }

    public void setDroit(Droit droit) {
        this.droit = droit;
    }

    @Override
    public String toString() {
        return String.format("Client name='%s', surname='%s', email='%s'", name, surname, email);
    }
}
