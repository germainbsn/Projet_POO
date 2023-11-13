package Model;

import java.util.ArrayList;
import java.util.Date;

public class Vol {

    private int numero;
    private City start;
    private City end;
    private Date dateStart;
    private Date dateEnd;
    private int capacity;
    private float price;


    public Vol(ArrayList<Client> clients, int numero, City start, City end, Date dateStart, Date dateEnd, int capacity, float price) {
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
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


}
