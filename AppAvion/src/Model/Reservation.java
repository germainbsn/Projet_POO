package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private Client client;
    private Vol vol;
    private LocalDate dateReservation;
    private float price;
    private int nbTicket;

    public Reservation(Client client, Vol vol, LocalDate dateReservation, float price, int nbTicket) {
        this.client = client;
        this.vol = vol;
        this.dateReservation = dateReservation;
        this.price = price;
        this.nbTicket = nbTicket;
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

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNbTicket() {
        return nbTicket;
    }

    public void setNbTicket(int nbTicket) {
        this.nbTicket = nbTicket;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return String.format("Reservation{Client='%s %s', Vol=%s, Date de réservation=%s, Ville de départ : '%s'" +
                        "Ville d'arrivée :'%s' }",
                client.getName(), client.getFirstName(), vol.getIdentifiant(),
                dateReservation.format(dateFormatter),vol.getStart().toString(),vol.getEnd().toString());
    }
}
