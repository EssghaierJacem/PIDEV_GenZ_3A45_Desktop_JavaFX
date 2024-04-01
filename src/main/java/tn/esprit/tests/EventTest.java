package tn.esprit.tests;
import tn.esprit.entites.Event;
import tn.esprit.tools.MyConnection;
import tn.esprit.services.EventServices;
import java.sql.Date;



public class EventTest {
    public static void main(String[] args) {
        // EVENT CRUD & GET BY ID :
        EventServices es = new EventServices();
        // Connexion  :
        MyConnection mc = new MyConnection();
        // Add Event
        Event event1 = new Event();
        event1.setNom("my event");
        event1.setDate_debut(Date.valueOf("2024-04-01"));
        event1.setDate_fin(Date.valueOf("2024-04-02"));
        event1.setLieu("paris");
        event1.setDescription("evenement");
        event1.setOrganisateur("linaaaaa");
        event1.setImage("image_url");
        event1.setPrix(500.0);
        es.addEvent(event1);

        // Fetching event by ID :
        int eventId = 1;
        Event fetchedEvent = es.getEventById(eventId);
        if (fetchedEvent != null) {
            System.out.println("Event found by ID: " + fetchedEvent);
        } else {
            System.out.println("No event found with ID: " + eventId);
        }

// Updating an event  by ID:
        if (fetchedEvent != null) {
            es.updateEvent(fetchedEvent);
            System.out.println("Event updated successfully: " + fetchedEvent);
        } else {
            System.out.println("No event found for updating.");
        }

        // List All events :
        System.out.println(es.getAllEvents());

        // Delete Event :
        int eventToDeleteId = 54;
        es.removeEvent(eventToDeleteId);
    }
}




