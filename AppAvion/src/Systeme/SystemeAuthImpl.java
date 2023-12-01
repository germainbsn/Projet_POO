package Systeme;

import Model.Client;
import Model.Droit;

import java.util.ArrayList;
import java.util.Optional;

public class SystemeAuthImpl implements SystemeAuth {

    ArrayList<Client> clients;

    public SystemeAuthImpl() {
        this.clients = new ArrayList<>();
    }

    @Override
    public Client connexion(String email, String mdp) throws IllegalArgumentException {
        return clients.stream().filter(client -> client.getEmail().equals(email) && client.getMdp().equals(mdp)).findFirst().orElse(null);
    }

    @Override
    public Client inscription(String email, String name, String surname, String mdp) {
        Client clientFind = clients.stream().filter(client -> client.getEmail().equals(email)).findFirst().orElse(null);
        if(clientFind != null) {
            return null;
        }
        else {
            Client newClient = new Client(name,surname,email,mdp, Droit.VIP);
            this.clients.add(newClient);
            return newClient;
        }



    }
}
