package Systeme;

import Model.Client;
import Model.Reservation;
import Model.Vol;

public aspect GestionPrix {

    pointcut changePrice(Vol avion, Client client) : execution (public boolean SystemeReservationImpl.reserver(Vol, Client)) && args(avion,client);
    pointcut changePrice(Reservation reservation) : execution (public boolean SystemeReservationImpl.annuler(Reservation)) && args(reservation);

    after(Vol avion, Client client) : changePrice(avion,client) {
        avion.setPriceCurrent(avion.getPriceMin()*(2-(float) avion.getCapacity() / avion.getCapacityMax()));
    }

    after(Reservation reservation) : changePrice(reservation) {
        Vol avion  = reservation.getVol();
        avion.setPriceCurrent(avion.getPriceMin()*(2-(float) avion.getCapacity() / avion.getCapacityMax()));
    }




}
