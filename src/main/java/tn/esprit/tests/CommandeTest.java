// CommandesTest.java
package tn.esprit.tests;

import tn.esprit.entites.Commande;
import tn.esprit.services.CommandeServices;
import tn.esprit.tools.MyConnection;

import java.sql.Date;

public class CommandeTest {
    public static void main(String[] args) {
        // VOL CRUD & GET BY ID :
        CommandeServices cs = new CommandeServices();
        // Connexion  :
        MyConnection mc = new MyConnection();
        Commande commande1 = new Commande();
        commande1.setNum_commande("");
        commande1.setCode_promo("");
        commande1.setType_paiement("");
        commande1.setEmail("");
        commande1.setDate_commande(Date.valueOf(""));
        cs.addCommande(commande1);

        // List Reservations :
        System.out.println(cs.getAllCommandes());

        // Updating a reservation  by ID:
        int commandeID = 60;
        Commande commandeToUpdate = cs.getCommandeById(commandeID);
        if (commandeToUpdate != null) {
            int commandeIdToUpdate = commandeToUpdate.getId();
            System.out.println("Vol ID: " + commandeIdToUpdate);

            commandeToUpdate.setNum_commande("10");
            cs.updateCommande( commandeToUpdate);

            System.out.println(commandeToUpdate);
        } else {
            System.out.println("Pas de vol ayant l'ID : " + commandeID + ".");
        }

        // List All Reservation :
        System.out.println(cs.getAllCommandes());

        // Delete Reservation :
        int reservationToDeleteId = 59;
        cs.removeCommande(reservationToDeleteId);
    }
}
