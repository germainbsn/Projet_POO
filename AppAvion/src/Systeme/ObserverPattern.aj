package Systeme;

import Model.Client;

import java.util.ArrayList;

public abstract aspect ObserverPattern {

    ArrayList<Observer> Observable.observers = new ArrayList<>();
    abstract pointcut updateObserver(Observable obs);


    public void Observable.addObserver(Observer obs) {

        if(!(this.observers.contains(obs))) {
            observers.add(obs);
        }
    }

    public void Observable.removeObserver(Observer obs) {
        observers.remove(obs);
    }

    public void Observable.notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this); //notification des observers
        }
    }

    after(Observable obs): updateObserver(obs) {
        obs.notifyObservers();
    }


}
