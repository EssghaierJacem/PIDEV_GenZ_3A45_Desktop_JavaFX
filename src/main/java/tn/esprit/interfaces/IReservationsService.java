package tn.esprit.interfaces;

import java.util.List;

public interface IReservationsService<R> {
    void addReservation(R r);
    void removeReservation(int id);
    void updateReservation(R r);
    R getReservationById(int id);
    List<R> getAllReservations();
}
