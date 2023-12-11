package Systeme;

import Model.Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public aspect JournalisationConnexion {

    private String fileName = "Connexion/suiviConnexion.txt";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy : HH:mm:ss");

    pointcut generateNewFile() : execution(SystemeAuthImpl.new(..));
    pointcut writeConnexion(String email) : execution (public Client Systeme.SystemeAuth.connexion(String, *)) && args(email,*);
    pointcut writeInscription(String email,String name,String surname) : execution (public Client Systeme.SystemeAuth.inscription(String,String,String,String)) && args(email,name,surname,*);

    before() : generateNewFile() {;
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName,false);
            writer.write( "------------Fichier de journalisation des connexions---------- \n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    after(String email) returning (Client client) : writeConnexion(email) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bf = new BufferedWriter(writer);
            if(client == null) {
                bf.write(LocalDateTime.now().format(formatter) + " : Tentative de connexion échoué avec l'email " + email+"\n");
            }
            else {
                bf.write(LocalDateTime.now().format(formatter) + " : Connexion de "
                        + client.getEmail() +" "+ client .getName()+" "
                        + client.getFirstName()+"\n");
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    after(String email,String name,String surname) returning (Client client) : writeInscription(email,name,surname) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bf = new BufferedWriter(writer);
            if(client == null) {
                bf.write(LocalDateTime.now().format(formatter) + " : Echec inscription " + email+"\n");
            }
            else {
                bf.write(LocalDateTime.now().format(formatter) + " : Nouvelle inscription de "
                        + client.getEmail() +" "+ client .getName()+" "
                        + client.getFirstName() + "!\n");
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}