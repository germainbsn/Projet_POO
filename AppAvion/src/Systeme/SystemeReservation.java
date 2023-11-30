package Systeme;
import Model.City;
import Model.Client;
import Model.Reservation;
import Model.Vol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public interface SystemeReservation {


    boolean reserver(Vol avion, float prix , Client client);

    boolean annuler(Reservation reservation);

    HashMap<Vol, Float> chercher(LocalDate dateStart, City startCity, City endCity);

    ArrayList<Reservation> getReservations(Client client);

    ArrayList<Reservation> getReservations(Vol vol);

    ArrayList<City> getCities();

    //boolean supressionAvion(Vol avion) throws PermissionDeniedException;

    //boolean modificationReservation(Reservation reservation) throws PermissionDeniedException;


}
