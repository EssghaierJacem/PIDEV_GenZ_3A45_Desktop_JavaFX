// DestinationTest.java
package tn.esprit.tests;


import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;
import tn.esprit.tools.MyConnection;

public class DestinationTest {
    public static void main(String[] args) {
        // VOL CRUD & GET BY ID :
        DestinationServices ds = new DestinationServices();
        // Connexion  :
        MyConnection mc = new MyConnection();
        // Add Destination
        Destination destination = new Destination("France", "Paris", "Description", "Attractions",
                "Accommodation", "Euro", "Multimedia", "Local cuisine", true, "FR");
        ds.addDestination(destination);

        // Fetching destination by ID :
        int destinationId = 1;
        Destination fetchedDestination = ds.getDestinationById(destinationId);
        if (fetchedDestination != null) {
            System.out.println("Destination found by ID: " + fetchedDestination);
        } else {
            System.out.println("No destination found with ID: " + destinationId);
        }

        // Updating a destination  by ID:
        if (fetchedDestination != null) {
            fetchedDestination.setDescription("Testing the update functionality.");
            ds.updateDestination(fetchedDestination);
            System.out.println("Destination updated successfully: " + fetchedDestination);
        } else {
            System.out.println("No destination found for updating.");
        }

        // List All Destinations :
        System.out.println(ds.getAllDestinations());

        // Delete Destination :
        int destinationToDeleteId = 54;
        ds.removeDestination(destinationToDeleteId);
    }
}
