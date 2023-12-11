package Systeme;

import Model.Client;

import java.util.Optional;

public interface SystemeAuth {
    Client connexion (String email, String mdp);

    Client inscription (String email, String name, String firstname, String mdp);
}
