package tn.esprit.tests;

import tn.esprit.entites.Destination;
import tn.esprit.entites.Guide;
import tn.esprit.entites.Tournee;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.GuideServices;
import tn.esprit.services.TourneeServices;

import java.sql.Date;

public class TourneeTest {
    public static void main(String[] args) {
        TourneeServices ts = new TourneeServices();
        DestinationServices ds = new DestinationServices();
        GuideServices gs = new GuideServices();

        Destination destination = new Destination("France", "Paris", "Description", "Attractions",
                "Accommodation", "Euro", "Multimedia", "Local cuisine", true, "FR");
        //ds.addDestination(destination);

        Guide g1 = new Guide("hamdani","dhia","tunisie","tunisien","5ans",20.5,53501622);
        //gs.addGuide(g1);

        /*Tournee t1 = new Tournee();
        t1.setDestination(destination);
        t1.setGuide(g1);
        t1.setNom("plaisir");
        t1.setDate_debut(Date.valueOf("2030-02-06"));
        t1.setDuree("2 jours");
        t1.setDescription("123456");
        t1.setTarif(12.8);
        t1.setMonuments("tour");
        t1.setTranche_age("Non");
        t1.setMoyen_transport("image_url");
        ts.addTournee(t1,16, 64);*/

        //ts.removeTournee(28);

       /* int tourneeId = 15;
        Tournee tourneeToUpdate = ts.getTourneeById(tourneeId);
        if (tourneeToUpdate != null) {
            int tourneeToUpdateid = tourneeToUpdate.getId();

            System.out.println("Tournee ID: " + tourneeToUpdateid);

            tourneeToUpdate.setDuree("10 jrs");
            ts.updateTournee(tourneeToUpdate);

            System.out.println(tourneeToUpdate);
        } else {
            System.out.println("Pas de tournee ayant l'ID : " + tourneeId + ".");
        }*/

        System.out.println(ts.getAllTournees());

    }
}
