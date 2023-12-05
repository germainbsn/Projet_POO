package Systeme;

import Model.Client;
import Model.Reservation;
import Model.Vol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public aspect JournalisationInfoAvion {

    private String Vol.nomFichier = null;

    pointcut generateNewFile() : execution(Model.Vol.new(..));
    pointcut writeReservation(Client client, float prix, Vol avion, int nbTicket ) : execution (public boolean Systeme.SystemeReservationImpl.reserver(Vol, float , Client, int)) && args(avion,prix,client,nbTicket);
    pointcut writeAnnulation(Reservation reservation) : execution (public boolean Systeme.SystemeReservationImpl.annuler(Reservation)) && args(reservation);
    pointcut writeCurrentPrice(Vol avion) : target (avion) && call(void Model.Vol.setPriceCurrent(float));
    pointcut deleteAllFile() : execution(Systeme.SystemeReservationImpl.new(..));


    before() : deleteAllFile() {
        try (Stream<Path> fichiers = Files.list(Path.of("volFile/"))) {
            fichiers.filter(file ->  file.getFileName().toString().startsWith("Vol")
                            && file.getFileName().toString().endsWith(".txt"))
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    void around() : generateNewFile() {
        try {
            proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        Vol avion = (Vol) thisJoinPoint.getTarget();
        avion.nomFichier = "volFile/Vol-"+ avion.getIdentifiant()+".txt" ;

        try {
            FileWriter writer = new FileWriter(avion.nomFichier);
            writer.write(avion.toString() + "\nPlaces restantes : " + avion.getCapacity());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    boolean around(Vol avion, float prix, Client client, int nbTicket) : writeReservation(client, prix,avion,nbTicket) {
        try {
            List<String> lignes = Files.readAllLines(Path.of(avion.nomFichier));
            System.out.println(lignes);
            lignes.set(7,"Prix courrant : "+avion.getPriceCurrent());
            lignes.set(8,"Places restantes : "+avion.getCapacity());
            Files.write(Path.of(avion.nomFichier),lignes, StandardOpenOption.WRITE);
            FileWriter writer = new FileWriter(avion.nomFichier, true);
            writer.write(LocalDate.now().toString() + " : " + client.toString()+ " a réservé "+ nbTicket +
                    " sieges au prix "+ prix +"€ par siège\n");
            writer.close();
            return proceed(avion,prix,client,nbTicket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    after(Vol avion, float prix, Client client, int nbTicket) : writeReservation(client, prix, avion, nbTicket) {
        try {
            List<String> lignes = Files.readAllLines(Path.of(avion.nomFichier));
            lignes.set(8,"Places restantes : " + avion.getCapacity());
            Files.write(Path.of(avion.nomFichier),lignes, StandardOpenOption.WRITE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    after(Vol avion) : writeCurrentPrice(avion) {
        try {
            System.out.println("passage current");
            List<String> lignes = Files.readAllLines(Path.of(avion.nomFichier));
            lignes.set(7,"Prix courrant : " + avion.getPriceCurrent());
            Files.write(Path.of(avion.nomFichier),lignes, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean around(Reservation reservation) : writeAnnulation(reservation) {
        try {
            boolean isRemove = proceed(reservation);
            if(isRemove) {
                Vol avion = reservation.getVol();
                List<String> lignes = Files.readAllLines(Path.of(avion.nomFichier));
                lignes.set(7,"Places restantes : "+avion.getCapacity());
                Files.write(Path.of(avion.nomFichier),lignes, StandardOpenOption.WRITE);
                FileWriter writer = new FileWriter(avion.nomFichier, true);
                writer.write(LocalDate.now().toString() + " : " + reservation.getClient().toString()+ " a annulé");
                writer.close();
            }

            return isRemove;

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
