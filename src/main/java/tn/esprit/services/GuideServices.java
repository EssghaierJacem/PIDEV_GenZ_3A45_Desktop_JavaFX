package tn.esprit.services;

import tn.esprit.entites.Guide;
import tn.esprit.interfaces.IGuideService;
import tn.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuideServices implements IGuideService<Guide> {
    @Override
    public void addGuide(Guide guide) {
        String query = "INSERT INTO guide(nom, prenom, nationalite, langues_parlees, experiences, tarif_horaire, num_tel) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, guide.getNom());
            pst.setString(2, guide.getPrenom());
            pst.setString(3, guide.getNationalite());
            pst.setString(4, guide.getLangues_parlees());
            pst.setString(5, guide.getExperiences());
            pst.setDouble(6, guide.getTarif_horaire());
            pst.setInt(7, guide.getNum_tel());
            pst.executeUpdate();
            System.out.println("Guide ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du guide: " + e.getMessage());
        }
    }

    @Override
    public void removeGuide(int id) {
        String query = "DELETE FROM guide WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Guide supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du guide: " + e.getMessage());
        }
    }

    @Override
    public void updateGuide(Guide guide) {
        String query = "UPDATE guide SET nom = ?, prenom = ?, nationalite = ?, langues_parlees = ?, experiences = ?, tarif_horaire = ?, num_tel = ? WHERE id = ?";

        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, guide.getNom());
            pst.setString(2, guide.getPrenom());
            pst.setString(3, guide.getNationalite());
            pst.setString(4, guide.getLangues_parlees());
            pst.setString(5, guide.getExperiences());
            pst.setDouble(6, guide.getTarif_horaire());
            pst.setInt(7, guide.getNum_tel());
            pst.setInt(8, guide.getId());
            pst.executeUpdate();
            System.out.println("Guide modifié avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du guide: " + e.getMessage());
        }
    }

    @Override
    public Guide getGuideById(int id) {
        String query = "SELECT * FROM guide WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Guide guide = new Guide();
                    guide.setId(rs.getInt("id"));
                    guide.setNom(rs.getString("nom"));
                    guide.setPrenom(rs.getString("prenom"));
                    guide.setNationalite(rs.getString("nationalite"));
                    guide.setLangues_parlees(rs.getString("langues_parlees"));
                    guide.setExperiences(rs.getString("experiences"));
                    guide.setTarif_horaire(rs.getDouble("tarif_horaire"));
                    guide.setNum_tel(rs.getInt("num_tel"));

                    return guide;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du guide: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Guide> getAllGuides() {
        List<Guide> guideList = new ArrayList<>();
        String query = "SELECT * FROM guide";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Guide guide = new Guide();
                    guide.setId(rs.getInt("id"));
                    guide.setNom(rs.getString("nom"));
                    guide.setPrenom(rs.getString("prenom"));
                    guide.setNationalite(rs.getString("nationalite"));
                    guide.setLangues_parlees(rs.getString("langues_parlees"));
                    guide.setExperiences(rs.getString("experiences"));
                    guide.setTarif_horaire(rs.getDouble("tarif_horaire"));
                    guide.setNum_tel(rs.getInt("num_tel"));

                    guideList.add(guide);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des guide: " + e.getMessage());
        }
        return guideList;
    }
}
