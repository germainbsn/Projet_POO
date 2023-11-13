package Systeme;
import Model.City;
import Model.Client;
import Model.Reservation;
import Model.Vol;

import java.util.ArrayList;
import java.util.Date;

public interface SystemeReservation {


    boolean reserver(Vol avion, Client client);

    boolean annuler(Vol avion, Client client);

    ArrayList<Vol> chercher(Date dateStart, City startCity, City endCity);

    ArrayList<Reservation> getReservations(Client client);

    ArrayList<Reservation> getReservations(Vol vol);

}
