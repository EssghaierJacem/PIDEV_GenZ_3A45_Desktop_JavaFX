package tn.esprit.tests;

import tn.esprit.entites.Commande;
import tn.esprit.services.CommandesServices;
import tn.esprit.tools.MyConnection;
public class CommandeTest {
    public static void main(String[] args) {
        // VOL CRUD & GET BY ID :
        CommandesServices ds = new CommandesServices();
        // Connexion  :
        MyConnection mc = new MyConnection();
        // Add Commande
        Commande Commande = new Commande("100", "100", "1234.0", "12D",
                "CASH", "sarra.benhamouda@esprit.tn", "122,0,1");
        ds.addCommande(Commande);

// Fetching commande by ID :
        int commandeId = 55;
        Commande fetchedCommande = ds.getCommandeById(commandeId);
        if (fetchedCommande != null) {
            System.out.println("Commande found by ID: " + fetchedCommande);
        } else {
            System.out.println("No commande found with ID: " + commandeId);
        }
        // Updating a commande  by ID:
        if (fetchedCommande != null) {

            ds.updateCommande(fetchedCommande);
            System.out.println("Commande updated successfully: " + fetchedCommande);
        } else {
            System.out.println("No commande found for updating.");
        }

        // List All Commandes :
        System.out.println(ds.getAllCommandes());

        // Delete Commande :
        int commandeToDeleteId = 50;
        ds.removeCommande(commandeToDeleteId);
    }

    }
