package Systeme;

import Model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public aspect GestionPrix {
    private HashMap<Droit,Float> reductions = new HashMap<>();
    private float Vol.benefice;

    pointcut setReduction() : execution(public static void main(String[]));
    pointcut changePrice(Vol avion, float prix ,Client client) : execution (public boolean SystemeReservationImpl.reserver(Vol,float, Client)) && args(avion,prix,client);
    pointcut changePriceAnnulation(Reservation reservation) : execution (public boolean SystemeReservationImpl.annuler(Reservation)) && args(reservation);
    pointcut applyDiscount(LocalDate dateStart, City startCity, City endCity) : call ( public HashMap<Vol,Float> SystemeReservation.chercher(LocalDate, City, City)) && args(dateStart, startCity, endCity);



    public float Vol.getBenefice() {
        return this.benefice;
    }

    public float Vol.setBenefice(float benefice) {
        return this.benefice = benefice;
    }

    before() : setReduction() {
        reductions.put(Droit.CLASSIQUE, 1.0F);
        reductions.put(Droit.VIP,0.90F);
        reductions.put(Droit.SALARIE,0.60F);
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

    HashMap<Vol,Float> around(LocalDate dateStart, City startCity, City endCity) :  applyDiscount(dateStart, startCity, endCity) {
        HashMap<Vol,Float> vols = proceed(dateStart,startCity,endCity);
        if(Main.clientConnecte.getDroit() == Droit.CLASSIQUE){
            return vols;
        } else {
            for(Map.Entry<Vol,Float> vol : vols.entrySet()) {
                vol.setValue((vol.getValue()*this.reductions.get(Main.clientConnecte.getDroit())));
            }
        }
        return vols;

    }




}
