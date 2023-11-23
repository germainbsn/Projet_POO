package Systeme;

import Model.City;
import Model.Client;
import Model.Reservation;
import Model.Vol;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static SystemeAuth  systemeAuth= new SystemeAuthImpl();
    static SystemeReservation systemeReservation = new SystemeReservationImpl();
    static Client clientConnecte = null;
    public static void main(String[] args) {


        while (true) {
            afficherMenuConnexion();
            Client client;
            int choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    clientConnecte = connexion();
                    affichageValidationConnexion(clientConnecte);
                    break;
                case 2:
                    clientConnecte = inscription();
                    affichageValidationInscription(clientConnecte);
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
        System.out.println("Bienvenue que souhaitez-vous faire ! ");
        System.out.println("1- Rechercher un vol");
        System.out.println("2- Consulter mes billets");
        System.out.println("3- Se deconnecter");
        int choix = scanner.nextInt();
        switch (choix) {
            case 1 :
                afficherMenuRecherche();
                break;
            case 2 :
                afficherMenuBillet();
                break;
            case 3 :
                System.out.println("Deconnexion...");
                clientConnecte = null;
                main(null);
                break;
            default :
                System.out.println("Choix invalide réessayez");
                afficherMenuConnecte();
        }

    }

    private static void afficherMenuBillet() {
        System.out.println("...........");
        System.out.println("Affichage de vos billets...");
        ArrayList<Reservation> reservations = systemeReservation.getReservations(clientConnecte);
        int i  = 1;
        for(Reservation reservation : reservations) {
            System.out.println(i + " - ");
            System.out.println(reservation.toString());
        }
        System.out.println("Vous pouvez annuler vos billets en tapant le numero de reservation, 0 pour retourner");
        int choix = scanner.nextInt();
        while (choix!=0) {
            boolean annulation = systemeReservation.annuler(reservations.get(choix-1));
            if(annulation) {
                System.out.println("Réservation annulé avec succés");
            } else{
                System.out.println("Erreur réesayez");
            }
            choix = scanner.nextInt();
        }
        afficherMenuConnecte();
    }

    private static void afficherMenuRecherche() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ArrayList<City> cities = systemeReservation.getCities();

        int i = 1;
        for (City city : cities) {
            System.out.println( i + " - " + city.getName() +" "+city.getCountry());
            i++;
        }
        System.out.print("Entrez le numero de la ville de départ : ");
        int choixDepart = scanner.nextInt();
        City startCity = cities.get(choixDepart-1);
        System.out.print("Entrez le numéro de la ville d'arrivée : ");
        int choixArrive = scanner.nextInt();
        City endCity = cities.get(choixArrive-1);


        System.out.print("Entrez la date de depart : ");
        scanner.nextLine();
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine(), dateFormatter);

        ArrayList<Vol> volsTrouve = systemeReservation.chercher(dateDebut,startCity,endCity);

        int k = 1;
        for (Vol vol : volsTrouve) {
            System.out.println(k+" - ");
            System.out.println(vol.toString());
            k++;
        }

        System.out.println("Selectionnez le vol à reserver, 0 pour retourner en arriere");

        int choix = scanner.nextInt();

        if (choix == 0 ) {
            System.out.println("... Retour au Menu...");
        }
        else {
            systemeReservation.reserver(volsTrouve.get(choix-1),clientConnecte);
            System.out.println ("Vol réservé");
        }
        afficherMenuConnecte();

    }

    private static Client connexion() {
        System.out.print("Veuillez inscrire votre email : ");
        String email = scanner.next();
        System.out.print("Veuillez inscrire votre  : ");
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
        System.out.print("Veuillez inscrire votre mdp : ");
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
            clientConnecte = client;
            afficherMenuConnecte();
        }
    }




}