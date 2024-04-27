package tn.esprit.services;

import tn.esprit.entites.Reservation;
import tn.esprit.interfaces.IReservationService;
import tn.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class ReservationServices implements IReservationService<Reservation> {

    @Override
    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO reservation(nom_client, prenom_client, num_tel, quantite, date_reservation) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, reservation.getNom_client());
            pst.setString(2, reservation.getPrenom_client());
            pst.setInt(3, reservation.getNum_tel());
            pst.setInt(4, reservation.getQuantite());
            pst.setDate(5, reservation.getDate_reservation());
            pst.executeUpdate();
            System.out.println("Reservation ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la reservation: " + e.getMessage());
        }

    }

    @Override
    public void removeReservation(int id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Reservation supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la reservation: " + e.getMessage());
        }

    }

    @Override
    public void updateReservation(Reservation reservation) {

        String query = "UPDATE reserevation SET nom_client = ?, prenom_client = ?, num_tel = ?, quantite = ?, date_reservation = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, reservation.getNom_client());
            pst.setString(2, reservation.getPrenom_client());
            pst.setInt(3, reservation.getNum_tel());
            pst.setInt(4, reservation.getQuantite());
            pst.setDate(5, reservation.getDate_reservation());
            pst.setInt(6, reservation.getId());
            pst.executeUpdate();
            System.out.println("Reservation mise à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la destination: " + e.getMessage());
        }


    }

    @Override
    public Reservation getReservationById(int id) {
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setNom_client(rs.getString("nom_client"));
                    reservation.setPrenom_client(rs.getString("prenom_client"));
                    reservation.setNum_tel(rs.getInt("num_tel"));
                    reservation.setQuantite(rs.getInt("quantite"));
                    reservation.setDate_reservation(rs.getDate("date_reservation"));
                    return reservation;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la reservation: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setNom_client(rs.getString("nom_client"));
                    reservation.setPrenom_client(rs.getString("prenom_client"));
                    reservation.setNum_tel(rs.getInt("num_tel"));
                    reservation.setQuantite(rs.getInt("quantite"));
                    reservation.setDate_reservation(rs.getDate("date_reservation"));
                    reservationList.add(reservation);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des destinations: " + e.getMessage());
        }
        return reservationList;
    }

}


