package tn.esprit.services;

import tn.esprit.entites.Classe;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Vol;
import tn.esprit.interfaces.IDestinationService;
import tn.esprit.interfaces.IVolService;
import tn.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;


public class VolServices implements IVolService<Vol> {

    @Override
    public void addVol(Vol vol, int destinationId) {
        String query = "INSERT INTO vol(destination_id, compagnie_a, num_vol, aeroport_depart, aeroport_arrivee, date_depart, date_arrivee, duree_vol, tarif, escale, image, classe) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, destinationId);
            pst.setString(2, vol.getCompagnie_a());
            pst.setInt(3, vol.getNum_vol());
            pst.setString(4, vol.getAeroport_depart());
            pst.setString(5, vol.getAeroport_arrivee());
            pst.setDate(6, new java.sql.Date(vol.getDate_depart().getTime()));
            pst.setDate(7, new java.sql.Date(vol.getDate_arrivee().getTime()));
            pst.setInt(8, vol.getDuree_vol());
            pst.setFloat(9, vol.getTarif());
            pst.setString(10, vol.getEscale());
            pst.setString(11, vol.getImage());
            pst.setString(12, vol.getClasse().toString());
            pst.executeUpdate();
            System.out.println("Vol ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du vol: " + e.getMessage());
        }
    }


    @Override
    public void removeVol(int id) {
        String query = "DELETE FROM vol WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Vol supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du vol: " + e.getMessage());
        }
    }

    @Override
    public void updateVol(Vol vol) {
        String query = "UPDATE vol SET destination_id = ?, compagnie_a = ?, num_vol = ?, aeroport_depart = ?, aeroport_arrivee = ?, date_depart = ?, date_arrivee = ?, duree_vol = ?, tarif = ?, escale = ?, image = ?, classe = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, vol.getDestination().getId());
            pst.setString(2, vol.getCompagnie_a());
            pst.setInt(3, vol.getNum_vol());
            pst.setString(4, vol.getAeroport_depart());
            pst.setString(5, vol.getAeroport_arrivee());
            pst.setDate(6, new java.sql.Date(vol.getDate_depart().getTime()));
            pst.setDate(7, new java.sql.Date(vol.getDate_arrivee().getTime()));
            pst.setInt(8, vol.getDuree_vol());
            pst.setFloat(9, vol.getTarif());
            pst.setString(10, vol.getEscale());
            pst.setString(11, vol.getImage());
            pst.setString(12, vol.getClasse().toString());
            pst.setInt(13, vol.getId());
            pst.executeUpdate();
            System.out.println("Vol mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du vol: " + e.getMessage());
        }
    }

    @Override
    public Vol getVolById(int id) {
        String query = "SELECT * FROM vol WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Vol vol = new Vol();
                    vol.setId(rs.getInt("id"));
                    IDestinationService<Destination> destinationService = new DestinationServices();
                    Destination destination = destinationService.getDestinationById(rs.getInt("destination_id"));
                    vol.setDestination(destination);
                    vol.setCompagnie_a(rs.getString("compagnie_a"));
                    vol.setNum_vol(rs.getInt("num_vol"));
                    vol.setAeroport_depart(rs.getString("aeroport_depart"));
                    vol.setAeroport_arrivee(rs.getString("aeroport_arrivee"));
                    vol.setDate_depart(rs.getDate("date_depart"));
                    vol.setDate_arrivee(rs.getDate("date_arrivee"));
                    vol.setDuree_vol(rs.getInt("duree_vol"));
                    vol.setTarif(rs.getFloat("tarif"));
                    vol.setEscale(rs.getString("escale"));
                    vol.setImage(rs.getString("image"));
                    vol.setClasse(Classe.valueOf(rs.getString("classe")));
                    return vol;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du vol: " + e.getMessage());
        }
        return null;
    }


    @Override
    public List<Vol> getAllVols() {
        List<Vol> volList = new ArrayList<>();
        String query = "SELECT * FROM vol";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Vol vol = new Vol();
                    vol.setId(rs.getInt("id"));
                    IDestinationService<Destination> destinationService = new DestinationServices();
                    Destination destination = destinationService.getDestinationById(rs.getInt("destination_id"));
                    vol.setDestination(destination);
                    vol.setCompagnie_a(rs.getString("compagnie_a"));
                    vol.setNum_vol(rs.getInt("num_vol"));
                    vol.setAeroport_depart(rs.getString("aeroport_depart"));
                    vol.setAeroport_arrivee(rs.getString("aeroport_arrivee"));
                    vol.setDate_depart(rs.getDate("date_depart"));
                    vol.setDate_arrivee(rs.getDate("date_arrivee"));
                    vol.setDuree_vol(rs.getInt("duree_vol"));
                    vol.setTarif(rs.getFloat("tarif"));
                    vol.setEscale(rs.getString("escale"));
                    vol.setImage(rs.getString("image"));
                    vol.setClasse(Classe.valueOf(rs.getString("classe")));
                    volList.add(vol);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des vols: " + e.getMessage());
        }
        return volList;
    }
    public List<Vol> getRecentlyAddedVols(int limit) {
        List<Vol> recentlyAddedList = new ArrayList<>();
        String query = "SELECT * FROM vol ORDER BY id DESC LIMIT ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Vol vol = new Vol();
                    vol.setId(rs.getInt("id"));
                    IDestinationService<Destination> destinationService = new DestinationServices();
                    Destination destination = destinationService.getDestinationById(rs.getInt("destination_id"));
                    vol.setDestination(destination);
                    vol.setCompagnie_a(rs.getString("compagnie_a"));
                    vol.setNum_vol(rs.getInt("num_vol"));
                    vol.setAeroport_depart(rs.getString("aeroport_depart"));
                    vol.setAeroport_arrivee(rs.getString("aeroport_arrivee"));
                    vol.setDate_depart(rs.getDate("date_depart"));
                    vol.setDate_arrivee(rs.getDate("date_arrivee"));
                    vol.setDuree_vol(rs.getInt("duree_vol"));
                    vol.setTarif(rs.getFloat("tarif"));
                    vol.setEscale(rs.getString("escale"));
                    vol.setImage(rs.getString("image"));
                    vol.setClasse(Classe.valueOf(rs.getString("classe")));
                    recentlyAddedList.add(vol);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des vols: " + e.getMessage());
        }
        return recentlyAddedList;
    }
    public List<Vol> getClosestFlights() {
        Date currentTime = new Date();
        List<Vol> allVols = getAllVols();
        List<Vol> closestFlights = allVols.stream()
                .filter(vol -> !vol.getDate_depart().before(currentTime))
                .sorted((v1, v2) -> v1.getDate_depart().compareTo(v2.getDate_depart()))
                .collect(Collectors.toList());
        return closestFlights;
    }
}
