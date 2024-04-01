package tn.esprit.interfaces;

import java.util.List;

public interface IEventService<E> {
    void addEvent(E e);
    void removeEvent(int id);
    void updateEvent(E e);
    E getEventById(int id);
    List<E> getAllEvents();
}

