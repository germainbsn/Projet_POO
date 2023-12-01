package Systeme;

import Model.City;
import Model.Client;
import Model.Reservation;
import Model.Vol;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SystemeReservationImpl implements SystemeReservation{

    private ArrayList<Reservation> reservations;

    private ArrayList<Vol> vols;

    private ArrayList<City> citiesDeserve;

    public SystemeReservationImpl(){
        this.vols = new ArrayList<>();
        this.citiesDeserve = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.initVol();
    }

    private void initVol() {

        citiesDeserve.add(new City("Canada","Montreal"));
        citiesDeserve.add(new City("Canada","Ottawa"));
        citiesDeserve.add(new City("Canada","Saguenay"));
        citiesDeserve.add(new City("Etats-unies","New-york"));
        citiesDeserve.add(new City("France","Paris"));

        LocalDate startDay = LocalDate.now();
        for(int i =1; i <10; i++) {
            LocalDate nextWeek = startDay.plusDays(7*i);
            vols.add(new Vol(generateRandomString(),citiesDeserve.get(0),citiesDeserve.get(1),
                    nextWeek,nextWeek, 200,300));
        }

    }


    @Override
    public boolean reserver(Vol avion, float prix, Client client) {
        reservations.add(new Reservation(client,avion,LocalDate.now(),avion.getPriceCurrent()));
        avion.setCapacity(avion.getCapacity()-1);
        return true;
    }

    @Override
    public boolean annuler(Reservation reservation) {
        boolean remove = this.reservations.remove(reservation);
        if (remove) {
            reservation.getVol().setCapacity(reservation.getVol().getCapacity()+1);
        }
        return remove;
    }

    @Override
    public HashMap<Vol, Float> chercher(LocalDate dateStart, City startCity, City endCity) {
        int a = 2;
        return (HashMap<Vol, Float>) vols.stream().filter(vol -> vol.getDateStart().equals(dateStart)
                && vol.getStart().equals(startCity)
                && vol.getEnd() == endCity
                && vol.getCapacity()>0)
                .collect(Collectors.toMap(
                        vol -> vol,Vol::getPriceCurrent));
    }

    @Override
    public ArrayList<Reservation> getReservations(Client client) {
        return (ArrayList<Reservation>) reservations.stream().filter(reservation -> reservation.getClient() ==client)
                .collect(Collectors.toList());

    }

    @Override
    public ArrayList<Reservation> getReservations(Vol vol) {
        return null;
    }

    @Override
    public ArrayList<City> getCities() {
        return this.citiesDeserve;
    }

    private String generateRandomString() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();

    }
}
