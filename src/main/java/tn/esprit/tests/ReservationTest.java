package tn.esprit.tests;




import tn.esprit.entites.Reservation;
import tn.esprit.services.ReservationsServices;
import tn.esprit.tools.MyConnection;
public class ReservationTest {
    public static void main(String[] args) {
        // VOL CRUD & GET BY ID :
        ReservationsServices ds = new ReservationsServices();
        // Connexion  :
        MyConnection mc = new MyConnection();
        // Add Reservation
        Reservation reservation = new Reservation("1", "Mahmoud", "Touhemi", "52308505", "5",
                "date", "QRCODE");
        ds.addReservation(reservation);

        // Fetching reservation by ID :
        int reservationId = 59;
        Reservation fetchedReservation = ds.getReservationById(reservationId);
        if (fetchedReservation != null) {
            System.out.println("Reservation found by ID: " + fetchedReservation);
        } else {
            System.out.println("No reservation found with ID: " + reservationId);
        }

        // Updating a reservation  by ID:
        if (fetchedReservation != null) {

            ds.updateReservation(fetchedReservation);
            System.out.println("Reservation updated successfully: " + fetchedReservation);
        } else {
            System.out.println("No reservation found for updating.");
        }

        // List All Reservations :
        System.out.println(ds.getAllReservations());

        // Delete Reservation :
        int reservationToDeleteId = 54;
        ds.removeReservation(reservationToDeleteId);
    }
}
