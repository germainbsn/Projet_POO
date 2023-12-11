package Systeme;

import Model.Client;
import Model.Droit;

import java.util.ArrayList;
import java.util.Optional;

public class SystemeAuthImpl implements SystemeAuth {

    ArrayList<Client> clients;

    public SystemeAuthImpl() {
        this.clients = new ArrayList<>();
        clients.add (new Client("adminName","adminFName","admin@gmail.com","admin",Droit.ADMIN));
        clients.add(new Client ("normalName","normalFName","normal@gmail.com","normal",Droit.CLASSIQUE));
        clients.add(new Client ("vipName","vipFName","vip@gmail.com","vip",Droit.VIP));
        clients.add(new Client ("salarieName","salarieFName","salarie@gmail.com","salarie",Droit.SALARIE));
    }

    @Override
    public Client connexion(String email, String mdp) throws IllegalArgumentException {
        return clients.stream().filter(client -> client.getEmail().equals(email) && client.getMdp().equals(mdp)).findFirst().orElse(null);
    }

    @Override
    public Client inscription(String email, String name, String firstname, String mdp) {
        Client clientFind = clients.stream().filter(client -> client.getEmail().equals(email)).findFirst().orElse(null);
        if(clientFind != null) {
            return null;
        }
        else {
            Client newClient = new Client(name,firstname,email,mdp, Droit.VIP);
            this.clients.add(newClient);
            return newClient;
        }



    }
}
