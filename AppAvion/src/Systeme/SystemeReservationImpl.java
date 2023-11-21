package Systeme;

import Model.City;
import Model.Client;
import Model.Reservation;
import Model.Vol;

import java.time.LocalDate;
import java.util.ArrayList;
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
            vols.add(new Vol(1,citiesDeserve.get(0),citiesDeserve.get(1),
                    nextWeek,nextWeek, 200,300));
        }

    }


    @Override
    public boolean reserver(Vol avion, Client client) {

        reservations.add(new Reservation(client,avion,LocalDate.now(),avion.getPrice()));
        return true;
    }

    @Override
    public boolean annuler(Reservation reservation) {
        this.reservations.remove(reservation);
        return true;
    }

    @Override
    public ArrayList<Vol> chercher(LocalDate dateStart, City startCity, City endCity) {
        int a = 2;
        return (ArrayList<Vol>) vols.stream().filter(vol -> vol.getDateStart().equals(dateStart)
                && vol.getStart().equals(startCity) && vol.getEnd() == endCity)
                .collect(Collectors.toList());
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
}
