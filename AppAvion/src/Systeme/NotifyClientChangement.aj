package Systeme;

import Model.Client;
import Model.Vol;
import java.time.LocalDate;
import Model.Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public aspect NotifyClientChangement extends ObserverPattern {

    private String Client.message = "";

    public String Client.getMessage() {
        return this.message;
    }

    public void Client.setMessage(String message) {
        this.message = this.getMessage() ;
    }

    public void Client.addMessage(String message) {
        this.message = this.getMessage() + "" +message+"\n";
    }

    declare parents : Client implements Observer;
    declare parents : Vol implements  Observable;

    pointcut updateObserver(Observable obs) : target (obs) && call(void Model.Vol.setDateStart(java.time.LocalDate));
    pointcut addObserver(Client client, float prix, Vol avion) : execution (public boolean Systeme.SystemeReservationImpl.reserver(Vol, float , Client)) && args(avion,prix,client);

    pointcut writeNotificationConnexion(String email, String mdp) : execution (public Client Systeme.SystemeAuth.connexion(String, String)) && args(email, mdp);

    public void Client.update(Observable obs) {

        Vol vol = (Vol) obs;
        this.addMessage(" IMPORTANT : Votre vol à destination de " + vol.getStart().toString() + " au départ de " + vol.getEnd() +
                " a changé de date de départ, nouvelle Date = "+ vol.getDateStart());
    }



    after(Client client, float prix, Vol avion) returning(boolean bool) : addObserver(client, prix, avion) {
        Observable vol = (Observable) avion;
        if(bool) {
            vol.addObserver((Observer) client);
        }
    }

    Client around(String email, String mdp)  : writeNotificationConnexion(email, mdp) {
        Client c = proceed(email,mdp);
        if(c!= null) {
            System.out.println(c.getMessage());
        }
        c.setMessage("");
        return c;

    }


}
