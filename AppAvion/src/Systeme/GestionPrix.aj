package Systeme;

import Model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public aspect GestionPrix {
    private HashMap<Droit,Float> reductions = new HashMap<>();
    private final double MULTIPLICATOR = 2;

    pointcut setReduction() : execution(public static void main(String[]));
    pointcut changePrice(Vol avion, float prix ,Client client, int nbTicket) : execution (public boolean SystemeReservationImpl.reserver(Vol,float, Client,int)) && args(avion,prix,client,nbTicket);
    pointcut changePriceAnnulation(Reservation reservation) : execution (public boolean SystemeReservationImpl.annuler(Reservation)) && args(reservation);
    pointcut applyDiscount(LocalDate dateStart, City startCity, City endCity, int nbTicket) : call ( public HashMap<Vol,Float> SystemeReservation.chercher(LocalDate, City, City,int)) && args(dateStart, startCity, endCity, nbTicket);


    before() : setReduction() {
        reductions.put(Droit.CLASSIQUE, 1.0F);
        reductions.put(Droit.VIP,0.90F);
        reductions.put(Droit.SALARIE,0.60F);
        reductions.put(Droit.ADMIN,0.60F);
    }

    after(Vol avion,float prix ,Client client,int nbTicket) : changePrice(avion,prix,client,nbTicket) {
        avion.setPriceCurrent((float) (avion.getPriceMin()*(MULTIPLICATOR-(float) avion.getCapacity() / avion.getCapacityMax())));
    }

    after(Reservation reservation) : changePriceAnnulation(reservation) {
        Vol avion  = reservation.getVol();
        avion.setPriceCurrent((float) (avion.getPriceMin()*(MULTIPLICATOR-(float) avion.getCapacity() / avion.getCapacityMax())));
    }

    HashMap<Vol,Float> around(LocalDate dateStart, City startCity, City endCity, int nbTicket) :  applyDiscount(dateStart, startCity, endCity, nbTicket) {
        HashMap<Vol,Float> vols = proceed(dateStart,startCity,endCity,nbTicket);
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
