package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Vol {

    private int numero;
    private City start;
    private City end;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int capacity;
    private float price;


    public Vol(int numero, City start, City end, LocalDate dateStart, LocalDate dateEnd, int capacity, float price) {
        this.numero = numero;
        this.start = start;
        this.end = end;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.capacity = capacity;
        this.price = price;
    }

    public City getStart() {
        return start;
    }

    public void setStart(City start) {
        this.start = start;
    }

    public City getEnd() {
        return end;
    }

    public void setEnd(City end) {
        this.end = end;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return String.format("Vol numero: %d\n" +
                        "De: %s\n" +
                        "À: %s\n" +
                        "Date de départ: %s\n" +
                        "Date d'arrivée: %s\n" +
                        "Capacité: %d\n" +
                        "Prix: %.2f",
                numero, start.toString(), end.toString(), dateStart.format(dateFormatter),
                dateEnd.format(dateFormatter), capacity, price);
    }





}
