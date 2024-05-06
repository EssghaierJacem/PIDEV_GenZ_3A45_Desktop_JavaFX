//// ParticipationTest.java
//package tn.esprit.tests;
//
//
//import tn.esprit.entites.Event;
//import tn.esprit.entites.Participation;
//import tn.esprit.entites.Vol;
//import tn.esprit.services.EventServices;
//import tn.esprit.services.ParticipationServices;
//import tn.esprit.tools.MyConnection;
//
//import java.sql.Date;
//
//public class ParticipationTest {
//    public static void main(String[] args) {
//        // VOL CRUD & GET BY ID :
//        EventServices es = new EventServices();
//        ParticipationServices ps = new ParticipationServices();
//        // Connexion  :
//        MyConnection mc = new MyConnection();
//        // Add Event :
//        Event event1 = new Event();
//        event1.setNom("Coachella");
//        event1.setLieu("paris");
//        event1.setDate_debut(Date.valueOf("2024-04-01"));
//        event1.setDate_fin(Date.valueOf("2024-04-02"));
//        event1.setDescription("hello");
//        event1.setOrganisateur("hello helloooo");
//        event1.setPrix(500.0f);
//        event1.setImage("image_url");
//
//        es.addEvent(event1);
//        // Add Participation
//        Participation participation1 = new Participation();
//        participation1.setEvent(event1);
//        participation1.setNom("Lina");
//        participation1.setPrenom("Mejri");
//        participation1.setTel(12345678);
//        participation1.setEmail("linamejri158@gmail.com");
////        ps.addParticipation(participation1);
//
//        // Fetching participation by ID :
//        int participationId = 1;
//        Participation fetchedParticipation = ps.getParticipationById(participationId);
//        if (fetchedParticipation != null) {
//            System.out.println("Participation found by ID: " + fetchedParticipation);
//        } else {
//            System.out.println("No participation found with ID: " + participationId);
//        }
//
//        // Updating a participation  by ID:
//        if (fetchedParticipation != null) {
//            fetchedParticipation.setNom("Testing the update functionality.");
//            ps.updateParticipation(fetchedParticipation);
//            System.out.println("Participation updated successfully: " + fetchedParticipation);
//        } else {
//            System.out.println("No Participation found for updating.");
//        }
//
//        // List All Participations :
//        System.out.println(ps.getAllParticipations());
//
//        // Delete Participation :
//        int participationToDeleteId = 54;
//        ps.removeParticipation(participationToDeleteId);
//    }
//}
