package Model;

import java.util.Date;

public class Reservation {
    private Client client;
    private Vol vol;
    private Date dateReservation;
    private int price;

    public Reservation(Client client, Vol vol, Date dateReservation, int price) {
        this.client = client;
        this.vol = vol;
        this.dateReservation = dateReservation;
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
