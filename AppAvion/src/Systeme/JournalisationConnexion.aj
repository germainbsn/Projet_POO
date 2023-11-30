package Systeme;

import Model.Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public aspect JournalisationConnexion {

    private String fileName = "Connexion/suiviConnexion.txt";

    pointcut generateNewFile() : execution(SystemeAuth.new(..));
    pointcut writeConnexion(String email, String mdp) : execution (public Client Systeme.SystemeAuth.connexion(String, String)) && args(email, mdp);
    pointcut writeInscription(String email,String name,String surname, String mdp) : execution (public Client Systeme.SystemeAuth.inscription(String,String,String,String)) && args(email,name,surname,mdp);

    before() : generateNewFile() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
            writer.write( "------------Fichier de journalisation des connexions---------- \n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    after(String email, String mdp) returning (Client client) : writeConnexion(email, mdp) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bf = new BufferedWriter(writer);
            if(client == null) {
                bf.write(LocalTime.now().toString() + " : Tentative de connexion échoué avec l'email " + email+"\n");
            }
            else {
                bf.write(LocalTime.now().toString() + " Connexion de "
                        + client.getEmail() +" "+ client .getName()+" "
                        + client.getSurname()+"\n");
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    after(String email,String name,String surname,String mdp) returning (Client client) : writeInscription(email,name,surname,mdp) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bf = new BufferedWriter(writer);
            if(client == null) {
                bf.write(LocalTime.now().toString() + " : Echec inscription " + email+"\n");
            }
            else {
                bf.write(LocalTime.now().toString() + " Nouvelle inscription de "
                        + client.getEmail() +" "+ client .getName()+" "
                        + client.getSurname() + "!\n");
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}
