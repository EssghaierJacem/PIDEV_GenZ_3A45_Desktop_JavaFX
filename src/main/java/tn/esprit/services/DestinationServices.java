package tn.esprit.services;

import tn.esprit.entites.Destination;
import tn.esprit.entites.User;
import tn.esprit.interfaces.IDestinationService;
import tn.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DestinationServices implements IDestinationService<Destination> {

    @Override
    public void addDestination(Destination destination) {
        String query = "INSERT INTO destination(pays, ville, description, attractions, accomodation, devise, multimedia, cuisine_locale, accessibilite, abbrev) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, destination.getPays());
            pst.setString(2, destination.getVille());
            pst.setString(3, destination.getDescription());
            pst.setString(4, destination.getAttractions());
            pst.setString(5, destination.getAccomodation());
            pst.setString(6, destination.getDevise());
            pst.setString(7, destination.getMultimedia());
            pst.setString(8, destination.getCuisine_locale());
            pst.setBoolean(9, destination.getAccessibilite());
            pst.setString(10, destination.getAbbrev());
            pst.executeUpdate();
            System.out.println("Destination ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la destination: " + e.getMessage());
        }
    }

    @Override
    public void removeDestination(int id) {
        String query = "DELETE FROM destination WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Destination supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la destination: " + e.getMessage());
        }
    }

    @Override
    public void updateDestination(Destination destination) {
        String query = "UPDATE destination SET pays = ?, ville = ?, description = ?, attractions = ?, accomodation = ?, devise = ?, multimedia = ?, cuisine_locale = ?, accessibilite = ?, abbrev = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, destination.getPays());
            pst.setString(2, destination.getVille());
            pst.setString(3, destination.getDescription());
            pst.setString(4, destination.getAttractions());
            pst.setString(5, destination.getAccomodation());
            pst.setString(6, destination.getDevise());
            pst.setString(7, destination.getMultimedia());
            pst.setString(8, destination.getCuisine_locale());
            pst.setBoolean(9, destination.getAccessibilite());
            pst.setString(10, destination.getAbbrev());
            pst.setInt(11, destination.getId());
            pst.executeUpdate();
            System.out.println("Destination mise à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la destination: " + e.getMessage());
        }
    }

    @Override
    public Destination getDestinationById(int id) {
        String query = "SELECT * FROM destination WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Destination destination = new Destination();
                    destination.setId(rs.getInt("id"));
                    destination.setPays(rs.getString("pays"));
                    destination.setVille(rs.getString("ville"));
                    destination.setDescription(rs.getString("description"));
                    destination.setAttractions(rs.getString("attractions"));
                    destination.setAccomodation(rs.getString("accomodation"));
                    destination.setDevise(rs.getString("devise"));
                    destination.setMultimedia(rs.getString("multimedia"));
                    destination.setCuisine_locale(rs.getString("cuisine_locale"));
                    destination.setAccessibilite(rs.getBoolean("accessibilite"));
                    destination.setAbbrev(rs.getString("abbrev"));
                    return destination;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la destination: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Destination> getAllDestinations() {
        List<Destination> destinationList = new ArrayList<>();
        String query = "SELECT * FROM destination";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Destination destination = new Destination();
                    destination.setId(rs.getInt("id"));
                    destination.setPays(rs.getString("pays"));
                    destination.setVille(rs.getString("ville"));
                    destination.setDescription(rs.getString("description"));
                    destination.setAttractions(rs.getString("attractions"));
                    destination.setAccomodation(rs.getString("accomodation"));
                    destination.setDevise(rs.getString("devise"));
                    destination.setMultimedia(rs.getString("multimedia"));
                    destination.setCuisine_locale(rs.getString("cuisine_locale"));
                    destination.setAccessibilite(rs.getBoolean("accessibilite"));
                    destination.setAbbrev(rs.getString("abbrev"));
                    destinationList.add(destination);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des destinations: " + e.getMessage());
        }
        return destinationList;
    }
    public List<Destination> getRecentlyAddedDestinations(int limit) {
        List<Destination> recentlyAddedList = new ArrayList<>();
        String query = "SELECT * FROM destination ORDER BY id DESC LIMIT ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Destination destination = new Destination();
                    destination.setId(rs.getInt("id"));
                    destination.setPays(rs.getString("pays"));
                    destination.setVille(rs.getString("ville"));
                    destination.setDescription(rs.getString("description"));
                    destination.setAttractions(rs.getString("attractions"));
                    destination.setAccomodation(rs.getString("accomodation"));
                    destination.setDevise(rs.getString("devise"));
                    destination.setMultimedia(rs.getString("multimedia"));
                    destination.setCuisine_locale(rs.getString("cuisine_locale"));
                    destination.setAccessibilite(rs.getBoolean("accessibilite"));
                    destination.setAbbrev(rs.getString("abbrev"));
                    recentlyAddedList.add(destination);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des destinations récemment ajoutées: " + e.getMessage());
        }
        return recentlyAddedList;
    }
    public int getUserCount() {
        String query = "SELECT COUNT(*) FROM user";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
        }
        return 0;
    }
    public int countDistinctDevises() {
        String query = "SELECT COUNT(DISTINCT devise) FROM destination";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des devises " + e.getMessage());
        }
        return 0;
    }
    public List<Destination> getTopDestinationsByUserCount(int limit) {
        List<Destination> topDestinations = new ArrayList<>();
        String query = "SELECT d.id, d.pays, d.ville, COUNT(DISTINCT du.user_id) AS userCount " +
                "FROM destination d " +
                "LEFT JOIN user_destination du ON du.destination_id = d.id " +
                "GROUP BY d.id, d.pays, d.ville " +
                "ORDER BY userCount DESC " +
                "LIMIT ?";

        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Destination destination = new Destination();
                    destination.setId(rs.getInt("id"));
                    destination.setPays(rs.getString("pays"));
                    destination.setVille(rs.getString("ville"));
                    int userCount = rs.getInt("userCount");
                    destination.setUsers(new HashSet<>());
                    for (int i = 0; i < userCount; i++) {
                        destination.getUsers().add(new User());
                    }

                    System.out.println("Destination: " + destination.getPays() + ", User count: " + userCount);

                    topDestinations.add(destination);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving top destinations by user count: " + e.getMessage());
        }
        return topDestinations;
    }

}
