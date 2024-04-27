package tn.esprit.interfaces;

import java.util.List;

public interface IParticipationService<P> {
    void addParticipation(P p);
    void removeParticipation(int id);
    void updateParticipation(P p);
    P getParticipationById(int id);
    List<P> getAllParticipations();
}
