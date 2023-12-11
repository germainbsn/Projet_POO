package Model;

public class Client {

    private String name;
    private String firstName;
    private String email;
    private String mdp;
    private Droit droit;

    public Client(String name, String firstName, String email, String mdp, Droit droit) {
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.mdp = mdp;
        this.droit = droit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
        return String.format("Client name='%s', surname='%s', email='%s'", name, firstName, email);
    }
}
