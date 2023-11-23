package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vol {

    private String identifiant;
    private City start;
    private City end;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int capacity;
    private int capacityMax;
    private float priceMin;
    private float priceCurrent;


    public Vol(String identifiant, City start, City end, LocalDate dateStart, LocalDate dateEnd, int capacity, float price) {
        this.identifiant = identifiant;
        this.start = start;
        this.end = end;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.capacity = capacity;
        this.capacityMax = capacity;
        this.priceMin = price;
        this.priceCurrent = price;
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
        return priceMin;
    }

    public void setPriceMin(float price) {
        this.priceMin = price;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String id) {
        this.identifiant = id;
    }

    public int getCapacityMax() {
        return capacityMax;
    }

    public void setCapacityMax(int capacityMax) {
        this.capacityMax = capacityMax;
    }

    public float getPriceMin() {
        return priceMin;
    }

    public float getPriceCurrent() {
        return priceCurrent;
    }

    public void setPriceCurrent(float priceCurrent) {
        this.priceCurrent = priceCurrent;
    }

    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return String.format("Vol numero: %s\n" +
                        "De: %s\n" +
                        "À: %s\n" +
                        "Date de départ: %s\n" +
                        "Date d'arrivée: %s\n" +
                        "Capacité: %d\n" +
                        "Prix: %.2f",
                identifiant, start.toString(), end.toString(), dateStart.format(dateFormatter),
                dateEnd.format(dateFormatter), capacity, priceCurrent);
    }





}
