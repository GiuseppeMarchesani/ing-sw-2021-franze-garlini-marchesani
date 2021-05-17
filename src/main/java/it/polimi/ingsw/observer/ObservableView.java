package it.polimi.ingsw.observer;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Observable class that can be observed by the ObserverView.
 */
public abstract class ObservableView {
    protected final List<ObserverView> observers = new ArrayList<>();

    /**
     * Adds an observer.
     * @param observer the observer to be added.
     */
    public void addObserver(ObserverView observer) {
        observers.add(observer);
    }

    /**
     * Adds a list of observers.
     * @param observerList the list of observers to be added.
     */
    public void addAllObservers(List<ObserverView> observerList) {
        observers.addAll(observerList);
    }

    /**
     * Removes an observer.
     * @param observerView the observer to be removed.
     */
    public void removeObserver(ObserverView observerView) {
        observers.remove(observerView);
    }

    /**
     * Removes a list of observers.
     * @param observerList the list of observers to be removed.
     */
    public void removeAllObservers(List<ObserverView> observerList) {
        observers.removeAll(observerList);
    }

    /**
     * Notifies all the current observers through the lambda argument.
     *
     * @param lambda the lambda to be called on the observers.
     */
    protected void notifyObserver(Consumer<ObserverView> lambda) {
        for (ObserverView observer : observers) {
            lambda.accept(observer);
        }
    }
}
