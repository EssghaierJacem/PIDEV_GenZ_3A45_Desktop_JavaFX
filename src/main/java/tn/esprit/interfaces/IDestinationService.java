package tn.esprit.interfaces;

import java.util.List;

public interface IDestinationService<T> {
    void addDestination(T t);
    void removeDestination(int id);
    void updateDestination(T t);
    T getDestinationById(int id);
    List<T> getAllDestinations();
}
