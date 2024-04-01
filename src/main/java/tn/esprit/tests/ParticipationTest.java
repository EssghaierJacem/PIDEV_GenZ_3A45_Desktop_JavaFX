package tn.esprit.tests;
import tn.esprit.entites.Participation;
import tn.esprit.services.ParticipationServices;
import tn.esprit.tools.MyConnection;
import tn.esprit.services.EventServices;
import tn.esprit.entites.Event;
import java.sql.Date;



public class ParticipationTest {
    public static void main(String[] args) {
        // PARTICIPATION CRUD & GET BY ID :
        ParticipationServices ps = new ParticipationServices();
        EventServices es = new EventServices();
        // Connexion  :
        MyConnection mc = new MyConnection();

        // Add Participation
        Participation participation1 = new Participation();
        participation1.setNom("linaaaa");
        participation1.setPrenom("mejriiii");
        participation1.setTel(22538453);
        participation1.setEmail("linamejri158@gmail.com");
        ps.addParticipation(participation1);
        // List Participation :
        System.out.println(ps.getAllParticipations());

        // Fetching event by ID :
        int participationId = 55;
        Participation fetchedParticipation = ps.getParticipationById(participationId);
        if (fetchedParticipation != null) {
            System.out.println("Participation found by ID: " + fetchedParticipation);
        } else {
            System.out.println("No participation found with ID: " + participationId);
        }

        // Update Participation :
        if (fetchedParticipation != null) {
            ps.updateParticipation(fetchedParticipation);
            System.out.println("Participation updated successfully: " + fetchedParticipation);
        } else {
            System.out.println("No Participation found for updating.");
        }

        // Delete Participation :
        ps.removeParticipation(23);


    }
}




