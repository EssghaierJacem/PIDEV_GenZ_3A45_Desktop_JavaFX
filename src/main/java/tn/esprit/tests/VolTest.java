package tn.esprit.tests;



import tn.esprit.entites.Classe;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Vol;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.VolServices;
import tn.esprit.tools.MyConnection;

import java.sql.Date;

public class VolTest {
    public static void main(String[] args) {
        // VOL CRUD & GET BY ID :
        VolServices vs = new VolServices();
        DestinationServices ds = new DestinationServices();
        // Connexion Test :
        MyConnection mc = new MyConnection();

        // Add Destination
        Destination destination = new Destination("France", "Paris", "Description", "Attractions",
                "Accommodation", "Euro", "Multimedia", "Local cuisine", true, "FR");
        ds.addDestination(destination);

        // Add Vol :
        Vol vol1 = new Vol();
        vol1.setDestination(destination);
        vol1.setCompagnie_a("Air France");
        vol1.setNum_vol(123456);
        vol1.setAeroport_depart("CDG");
        vol1.setAeroport_arrivee("JFK");
        vol1.setDate_depart(Date.valueOf("2024-04-01"));
        vol1.setDate_arrivee(Date.valueOf("2024-04-02"));
        vol1.setDuree_vol(12);
        vol1.setTarif(500.0f);
        vol1.setEscale("Non");
        vol1.setImage("image_url");
        vol1.setClasse(Classe.BUSINESS);

        vs.addVol(vol1,2);

        // List Vols :
        System.out.println(vs.getAllVols());

        // Update Vol :
        int volId = 21;
        Vol volToUpdate = vs.getVolById(volId);
        if (volToUpdate != null) {
            int volIdToUpdate = volToUpdate.getId();
            System.out.println("Vol ID: " + volIdToUpdate);

            volToUpdate.setTarif(600.0f);
            vs.updateVol(volToUpdate);

            System.out.println(volToUpdate);
        } else {
            System.out.println("Pas de vol ayant l'ID : " + volId + ".");
        }

        // Delete Vol :
        vs.removeVol(23);


    }
}
