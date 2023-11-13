package Systeme;

import Model.City;
import Model.Client;
import Model.Reservation;
import Model.Vol;

import java.util.ArrayList;
import java.util.Date;

public class SystemeReservationImpl implements SystemeReservation{

    private ArrayList<Reservation> reservations;

    public void SystemeReservation(){
        this.reservations = new ArrayList<>();
    }



    @Override
    public boolean reserver(Vol avion, Client client) {
        return true;
    }

    @Override
    public boolean annuler(Vol avion, Client client) {
        return true;
    }

    @Override
    public ArrayList<Vol> chercher(Date dateStart, City startCity, City endCity) {

        return null;
    }

    @Override
    public ArrayList<Reservation> getReservations(Client client) {
        return null;
    }

    @Override
    public ArrayList<Reservation> getReservations(Vol vol) {
        return null;
    }
}
