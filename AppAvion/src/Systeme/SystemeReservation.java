package Systeme;
import Model.City;
import Model.Client;
import Model.Reservation;
import Model.Vol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public interface SystemeReservation {


    boolean reserver(Vol avion, float prix , Client client, int nbTicket);

    boolean annuler(Reservation reservation);

    HashMap<Vol, Float> chercher(LocalDate dateStart, City startCity, City endCity, int nbTicket);

    ArrayList<Reservation> getReservations(Client client);

    ArrayList<Reservation> getReservations(Vol vol);

    ArrayList<City> getCities();

    //boolean supressionAvion(Vol avion) throws PermissionDeniedException;

    boolean modificationVol(String idVol, LocalDate start, LocalDate end) throws PermissionDeniedException;


}
