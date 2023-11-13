import Model.Client;
import Systeme.SystemeAuth;
import Systeme.SystemeAuthImpl;
import Systeme.SystemeReservation;
import Systeme.SystemeReservationImpl;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static SystemeAuth  systemeAuth= new SystemeAuthImpl();
    static SystemeReservation systemeReservation = new SystemeReservationImpl();

    public static void main(String[] args) {


        while (true) {
            afficherMenuConnexion();
            Client client;
            int choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    client = connexion();
                    affichageValidationConnexion(client);
                    break;
                case 2:
                    client = inscription();
                    affichageValidationInscription(client);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Choix invalide réessayez");
                    main(null);

            }
        }
    }

    private static void afficherMenuConnexion (){
        System.out.println("Veuillez vous connecter ou vous inscrire");
        System.out.println("1- Se connecter");
        System.out.println("2- S'inscrire");
        System.out.println("3- Quitter le programme");

    }

    private static void afficherMenuConnecte (){
        System.out.println("Bienvenue ! ");



    }

    private static Client connexion() {
        System.out.print("Veuillez inscrire votre email : ");
        String email = scanner.next();
        System.out.print("Veuillez inscrire votre mdp");
        String mdp = scanner.next();
        return systemeAuth.connexion(email,mdp);

    }

    private static Client inscription() {
        System.out.print("Veuillez inscrire votre prenom : ");
        String surname = scanner.next();
        System.out.print("Veuillez inscrire votre nom : ");
        String name = scanner.next();
        System.out.print("Veuillez inscrire votre email : ");
        String email = scanner.next();
        System.out.print("Veuillez inscrire votre mdp");
        String mdp = scanner.next();
        return systemeAuth.inscription(email,name,surname,mdp);

    }

    private static void affichageValidationConnexion(Client client) {
        if(client == null) {
            System.out.println("............");
            System.out.println("Identifiant/mdp invalide");
        }
        else {
            System.out.println("............");
            System.out.println("Connexion réussie...");
            afficherMenuConnecte();
        }
    }

    private static void affichageValidationInscription(Client client) {
        if(client == null) {
            System.out.println("............");
            System.out.println("Le mail existe déjà");
        }
        else {
            System.out.println("............");
            System.out.println("Inscription réussie... Connexion....");
            afficherMenuConnecte();
        }
    }




}