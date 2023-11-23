package Systeme;

import Model.Client;
import Model.Reservation;
import Model.Vol;
import jdk.dynalink.StandardOperation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;

public aspect Journalisation {

    private String Vol.nomFichier = null;

    pointcut generateNewFile() : execution(Model.Vol.new(..));
    pointcut writeReservation(Client client, Vol avion ) : execution (public boolean SystemeReservationImpl.reserver(Vol, Client)) && args(avion,client);
    pointcut writeAnnulation(Reservation reservation) : execution (public boolean SystemeReservationImpl.annuler(Reservation)) && args(reservation);

    void around() : generateNewFile() {
        try {
            proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        Vol avion = (Vol) thisJoinPoint.getTarget();
        avion.nomFichier = "Vol-"+ avion.getIdentifiant()+".txt" ;

        try {
            FileWriter writer = new FileWriter(avion.nomFichier);
            writer.write(avion.toString() + "Place restantes : " + avion.getCapacity() +"\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    boolean around(Vol avion, Client client) : writeReservation(client, avion) {
        try {
            FileWriter writer = new FileWriter(avion.nomFichier, true);
            BufferedWriter bf = new BufferedWriter(writer);
            bf.write("\n"+LocalDate.now().toString() + " : " + client.toString()+ " a réservé au prix de "+ avion.getPrice()+"€");
            bf.close();
            List<String> lignes = Files.readAllLines(Path.of(avion.nomFichier));
            lignes.set(7,"Places restantes : ");
            Files.write(Path.of(avion.nomFichier),lignes, StandardOpenOption.WRITE);
            return proceed(avion,client);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    after(Vol avion, Client client) : writeReservation(client, avion) {
        try {
            List<String> lignes = Files.readAllLines(Path.of(avion.nomFichier));
            lignes.set(7,"Places restantes : " + avion.getCapacity());
            Files.write(Path.of(avion.nomFichier),lignes, StandardOpenOption.WRITE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean around(Reservation reservation) : writeAnnulation(reservation) {
        try {
            FileWriter writer = new FileWriter(reservation.getVol().nomFichier, true);
            BufferedWriter bf = new BufferedWriter(writer);
            bf.write(LocalDate.now().toString() + " : " + reservation.getClient().toString()+ " a annulé ");
            bf.close();
            return proceed(reservation);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    after(Reservation reservation) : writeAnnulation(reservation) {
        try {
            List<String> lignes = Files.readAllLines(Path.of(reservation.getVol().nomFichier));
            lignes.set(7,"Places restantes : "+ reservation.getVol().getCapacity());
            Files.write(Path.of(reservation.getVol().nomFichier),lignes, StandardOpenOption.WRITE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
