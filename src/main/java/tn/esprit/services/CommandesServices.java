package tn.esprit.services;



import tn.esprit.entites.Commande;
import tn.esprit.interfaces.ICommandesService;
import tn.esprit.tools.MyConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CommandesServices implements ICommandesService<Commande> {

    @Override
    public void addCommande(Commande commande) {
        String query = "INSERT INTO commande(num_commande, prix, code_promo, type_paiement, email, date_commande) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, commande.getNum_commande());
            pst.setDouble(2, commande.getPrix());
            pst.setString(3, commande.getCode_promo());
            pst.setString(4, commande.getType_paiement());
            pst.setString(5, commande.getEmail());
            pst.setDate(6, (Date) commande.getDate_commande());
            pst.executeUpdate();
            System.out.println("Commande ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la commande: " + e.getMessage());
        }
    }

    @Override
    public void removeCommande(int id) {
        String query = "DELETE FROM commande WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Commande supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la commande: " + e.getMessage());
        }
    }

    @Override
    public void updateCommande(Commande commande) {
        String query = "UPDATE commande SET num_commande = ?, prix = ?, code_promo = ?, type_paiement = ?, email = ?, date_commande = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, commande.getNum_commande());
            pst.setDouble(2, commande.getPrix());
            pst.setString(3, commande.getCode_promo());
            pst.setString(4, commande.getType_paiement());
            pst.setString(5, commande.getEmail());
            pst.setDate(6, commande.getDate_commande());
            pst.setInt(7, commande.getId());
            pst.executeUpdate();
            System.out.println("Commande mise à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la commande: " + e.getMessage());
        }
    }

    @Override
    public Commande getCommandeById(int id) {
        String query = "SELECT * FROM commande WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Commande commande = new Commande("6", "100", "1234.0", "12D", "CASH", "sarra.benhamouda@esprit.tn", "122,0,1");
                    commande.setId(rs.getInt("id"));
                    commande.setNum_commande(rs.getString("num_commande"));
                    commande.setPrix(rs.getDouble("prix"));
                    commande.setCode_promo(rs.getString("code_promo"));
                    commande.setType_paiement(rs.getString("type_paiement"));
                    commande.setEmail(rs.getString("email"));
                    commande.setDate_commande(rs.getDate("date_commande"));
                    return commande;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la commande: " + e.getMessage());
        }
        return null;

    }

    @Override
    public List<Commande> getAllCommandes() {
        List<Commande> commandeList = new ArrayList<>();
        String query = "SELECT * FROM commande";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Commande commande = new Commande("6", "100", "1234.0", "12D", "CASH", "sarra.benhamouda@esprit.tn", "122,0,1");
                    commande.setId(rs.getInt("id"));
                    commande.setNum_commande(rs.getString("num_commande"));
                    commande.setPrix(rs.getDouble("prix"));
                    commande.setCode_promo(rs.getString("code_promo"));
                    commande.setType_paiement(rs.getString("type_paiement"));
                    commande.setEmail(rs.getString("email"));
                    commande.setDate_commande(rs.getDate("date_commande"));
                    commandeList.add(commande);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des commandes: " + e.getMessage());
        }
        return commandeList;
    }
}