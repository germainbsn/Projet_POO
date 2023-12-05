package Systeme;

import Model.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        System.out.println("3- Gérer mes avantages");
        System.out.println("4- Outil administrateur (réserver aux administrateur)");
        System.out.println("5- Se deconnecter");
        int choix = scanner.nextInt();
        switch (choix) {
            case 1 :
                afficherMenuRecherche();
                break;
            case 2 :
                afficherMenuBillet();
                break;
            case 3 :
                afficherAvantage();
                break;
            case 4 :
                afficherAdmin();
            case 5 :
                System.out.println("Deconnexion...");
                clientConnecte = null;
                main(null);
                break;

            default :
                System.out.println("Choix invalide réessayez");
                afficherMenuConnecte();
        }

    }

    private static void afficherAdmin() {
        System.out.println("Bienvenue dans l'outil administrateur ");
        System.out.println("1- Modifier date d'un vol");
        System.out.println("2- Retour");
        System.out.println("3- Deconnexion");

        int choix = scanner.nextInt();
        switch (choix) {
            case 1 :
                afficherMenuModifVol();
                break;
            case 2 :
                afficherMenuConnecte();
                break;
            case 3 :
                System.out.println("Deconnexion...");
                clientConnecte = null;
                main(null);
                break;

            default :
                System.out.println("Choix invalide réessayez");
                afficherAdmin();
        }

    }

    private static void afficherMenuModifVol() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("Entrez l'identifiant de vol a modifier : ");
        String idVol = scanner.next();
        System.out.print("Entrez la nouvelle date de depart : ");
        LocalDate newStartDate = LocalDate.parse(scanner.next(), dateFormatter);
        System.out.print("Entrez la nouvelle date d'arrivé : ");
        LocalDate newEndDate = LocalDate.parse(scanner.next(), dateFormatter);
        if(systemeReservation.modificationVol(idVol,newStartDate,newEndDate)) {
            System.out.println("Vol modifié avec succés");
        } else{
            System.out.println("Erreur veuillez réessayer");
        }
        afficherAdmin();

    }

    private static void afficherAvantage() {
        System.out.println("---------Vos avantage-----------");
        System.out.println("Vos droits : " + clientConnecte.getDroit());
        if(clientConnecte.getDroit() == Droit.CLASSIQUE) {
            System.out.println("Vous pouvez passer en VIP et bénéficier de réduction pour 300€ ! ");
            System.out.println("Pour passer VIP tapez 1, pour retourner au menu tapez 2");
            int choix = scanner.nextInt();
            if (choix == 1 ) {
                System.out.println("Vous passez vip !");
                clientConnecte.setDroit(Droit.VIP);
            }
        }
        afficherMenuConnecte();

    }

    private static void afficherMenuBillet() {
        System.out.println("...........");
        System.out.println("Affichage de vos billets...");
        ArrayList<Reservation> reservations = systemeReservation.getReservations(clientConnecte);
        int i  = 1;
        for(Reservation reservation : reservations) {
            System.out.println(i + " - ");
            System.out.println(reservation.toString());
            i++;
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

        HashMap<Vol,Float> volsTrouve = systemeReservation.chercher(dateDebut,startCity,endCity);
        List<Vol> vols =  new ArrayList<>();
        vols.addAll(volsTrouve.keySet());

        int k = 1;
        for (Map.Entry<Vol, Float> entry : volsTrouve.entrySet()) {
            System.out.println(k+" - ");
            System.out.println(entry.getKey().toStringWithoutPrice());
            System.out.println("Prix : "+ entry.getValue() +"€");
            k++;
        }

        System.out.println("Selectionnez le vol à reserver, 0 pour retourner en arriere");

        int choix = scanner.nextInt();

        if (choix == 0 ) {
            System.out.println("... Retour au Menu...");
        }
        else {
            Vol choixVol = vols.get(choix-1);
            systemeReservation.reserver(choixVol,volsTrouve.get(choixVol),clientConnecte);
            System.out.println ("Vol réservé");
        }
        afficherMenuConnecte();

    }

    private static Client connexion() {
        System.out.print("Veuillez inscrire votre email : ");
        String email = scanner.next();
        System.out.print("Veuillez inscrire votre mot de passe : ");
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