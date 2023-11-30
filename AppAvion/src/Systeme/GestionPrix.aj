package Systeme;

import Model.Client;
import Model.Reservation;
import Model.Vol;

public aspect GestionPrix {
    private float Vol.benefice;

    pointcut changePrice(Vol avion, float prix ,Client client) : execution (public boolean SystemeReservationImpl.reserver(Vol,float, Client)) && args(avion,prix,client);
    pointcut changePriceAnnulation(Reservation reservation) : execution (public boolean SystemeReservationImpl.annuler(Reservation)) && args(reservation);

    public float Vol.getBenefice() {
        return this.benefice;
    }

    public float Vol.setBenefice(float benefice) {
        return this.benefice = benefice;
    }

    after(Vol avion,float prix ,Client client) : changePrice(avion,prix,client) {
        avion.benefice += avion.getPrice();
        avion.setPriceCurrent(avion.getPriceMin()*(2-(float) avion.getCapacity() / avion.getCapacityMax()));
    }

    after(Reservation reservation) : changePriceAnnulation(reservation) {
        Vol avion  = reservation.getVol();
        avion.benefice -= reservation.getPrice();
        avion.setPriceCurrent(avion.getPriceMin()*(2-(float) avion.getCapacity() / avion.getCapacityMax()));
    }




}
