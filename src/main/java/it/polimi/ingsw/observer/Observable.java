package it.polimi.ingsw.observer;

import it.polimi.ingsw.messages.GeneralMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class.
 */
public class Observable {

    private final List<Observer> observerList = new ArrayList<>();

    /**
     * Adds an observer.
     * @param observer the observer to be added in the list.
     */
    public void add(Observer observer) {
        observerList.add(observer);
    }

    /**
     * Removes an observer.
     * @param observer the observer to be removed.
     */
    public void remove(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * Notifies all the observers passing a message.
     * @param message the message to be passed to the observers.
     */
    protected void notify(GeneralMessage message) {
        for (Observer observer : observerList) {
            observer.update(message);
        }
    }
}
