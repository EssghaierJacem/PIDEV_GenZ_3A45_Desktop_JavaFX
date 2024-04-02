package tn.esprit.interfaces;

import java.util.List;

public interface ITourneeService<T> {
    void addTournee(T t, int id1, int id2);
    void removeTournee(int id);
    void updateTournee(T t);
    T getTourneeById(int id);
    List<T> getAllTournees();
}
