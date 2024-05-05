// ReservationTest.java
package tn.esprit.tests;


import tn.esprit.entites.Reservation;
import tn.esprit.services.ReservationServices;
import tn.esprit.tools.MyConnection;

import java.sql.Date;

public class ReservationTest {
    public static void main(String[] args) {
        // VOL CRUD & GET BY ID :
        ReservationServices rs = new ReservationServices();
        // Connexion  :
        MyConnection mc = new MyConnection();
        Reservation reservation1 = new Reservation();
        reservation1.setNom_client("");
        reservation1.setPrenom_client("");
        reservation1.setNum_tel(1);
        reservation1.setQuantite(1);
        reservation1.setDate_reservation(Date.valueOf("2024-04-01"));
        rs.addReservation(reservation1);

        // List Reservations :
        System.out.println(rs.getAllReservations());

        // Updating a reservation  by ID:
        int reservationID = 60;
        Reservation reservationToUpdate = rs.getReservationById(reservationID);
        if (reservationToUpdate != null) {
            int reservationIdToUpdate = reservationToUpdate.getId();
            System.out.println("Vol ID: " + reservationIdToUpdate);

            reservationToUpdate.setQuantite(10);
            rs.updateReservation( reservationToUpdate);

            System.out.println( reservationToUpdate);
        } else {
            System.out.println("Pas de vol ayant l'ID : " + reservationID + ".");
        }

        // List All Reservation :
        System.out.println(rs.getAllReservations());

        // Delete Reservation :
        int reservationToDeleteId = 59;
        rs.removeReservation(reservationToDeleteId);
    }
}
