// DestinationTest.java
package tn.esprit.tests;


import tn.esprit.entites.Classe;
import tn.esprit.entites.Event;
import tn.esprit.entites.Vol;
import tn.esprit.services.EventServices;
import tn.esprit.tools.MyConnection;

import java.sql.Date;

public class EventTest {
    public static void main(String[] args) {
        // VOL CRUD & GET BY ID :
        EventServices es = new EventServices();
        // Connexion  :
        MyConnection mc = new MyConnection();
        // Add Event :
        Event event1 = new Event();
        event1.setNom("Coachella");
        event1.setLieu("paris");
        event1.setDate_debut(Date.valueOf("2024-04-01"));
        event1.setDate_fin(Date.valueOf("2024-04-02"));
        event1.setDescription("hello");
        event1.setOrganisateur("hello helloooo");
        event1.setPrix(500.0f);
        event1.setImage("image_url");

        es.addEvent(event1);

        // Fetching events by ID :
        int eventId = 1;
        Event fetchedEvent = es.getEventById(eventId);
        if (fetchedEvent != null) {
            System.out.println("Event found by ID: " + fetchedEvent);
        } else {
            System.out.println("No event found with ID: " + eventId);
        }

        // Updating an event  by ID:
        if (fetchedEvent != null) {
            fetchedEvent.setDescription("Testing the update functionality.");
            es.updateEvent(fetchedEvent);
            System.out.println("Event updated successfully: " + fetchedEvent);
        } else {
            System.out.println("No event found for updating.");
        }

        // List All events :
        System.out.println(es.getAllEvents());

        // Delete event :
        int eventToDeleteId = 54;
        es.removeEvent(eventToDeleteId);
    }
}
