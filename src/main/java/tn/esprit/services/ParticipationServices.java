package tn.esprit.services;
import tn.esprit.entites.Participation;
import tn.esprit.interfaces.IParticipationService;
import tn.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ParticipationServices implements IParticipationService<Participation> {


    @Override
    public void addParticipation(Participation participation, int eventID) {
        String query = "INSERT INTO participation(event_id, nom, prenom, tel, email) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, eventID); // Insert the event ID
            pst.setString(2, participation.getNom());
            pst.setString(3, participation.getPrenom());
            pst.setInt(4, participation.getTel());
            pst.setString(5, participation.getEmail());

            pst.executeUpdate();
            System.out.println("Participation ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la Participation: " + e.getMessage());
        }
    }




    public void removeParticipation(int id) {
        String query = "DELETE FROM participation WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Participation supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la Participation: " + e.getMessage());
        }

    }

    @Override
    public void updateParticipation(Participation participation) {
        String query = "UPDATE participation SET nom = ?, prenom = ?, tel = ?, email = ? WHERE id = ?";

        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, participation.getNom());
            pst.setString(2, participation.getPrenom());
            pst.setInt(3, (participation.getTel()));
            pst.setString(4, participation.getEmail());
            pst.setInt(5, participation.getId());


            pst.executeUpdate();
            System.out.println("Participation mise à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la participation: " + e.getMessage());
        }

    }

    @Override
    public Participation getParticipationById(int id) {
        String query = "SELECT * FROM participation WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Participation participation = new Participation();
                    participation.setId(rs.getInt("id"));
                    participation.setNom(rs.getString("nom"));
                    participation.setPrenom(rs.getString("prenom"));
                    participation.setTel(rs.getInt("tel"));
                    participation.setEmail(rs.getString("email"));
                    return participation;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l participation : " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Participation> getAllParticipations() {
        List<Participation> participationList = new ArrayList<>();
        String query = "SELECT * FROM participation";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Participation participation = new Participation();
                    participation.setId(rs.getInt("id"));
                    participation.setNom(rs.getString("nom"));
                    participation.setPrenom(rs.getString("prenom"));
                    participation.setTel(rs.getInt("tel"));
                    participation.setEmail(rs.getString("email"));
                    participationList.add(participation);

                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des participations: " + e.getMessage());
        }
        return participationList;

    }
}
