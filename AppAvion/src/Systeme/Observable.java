package Systeme;

public interface Observable {

    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers();

}
