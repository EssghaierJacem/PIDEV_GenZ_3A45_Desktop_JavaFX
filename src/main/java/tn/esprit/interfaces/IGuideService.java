package tn.esprit.interfaces;

import java.util.List;

public interface IGuideService<T> {
    void addGuide(T t);
    void removeGuide(int id);
    void updateGuide(T t);
    T getGuideById(int id);
    List<T> getAllGuides();
}
