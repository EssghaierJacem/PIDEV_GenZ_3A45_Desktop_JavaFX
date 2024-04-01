package tn.esprit.interfaces;

import java.util.List;

public interface IVolService<T> {
    void addVol(T t, int id);
    void removeVol(int id);
    void updateVol(T t);
    T getVolById(int id);
    List<T> getAllVols();
}
