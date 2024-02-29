package ro.ubbcluj.map.demogui.utils.observer;

public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}
