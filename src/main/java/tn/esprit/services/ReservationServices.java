package tn.esprit.services;

import javafx.scene.control.Label;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entites.Reservation;
import tn.esprit.interfaces.IReservationService;
import tn.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            pst.setString(5, reservation.getDate_reservation().toString());
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

        String query = "UPDATE reservation SET nom_client = ?, prenom_client = ?, num_tel = ?, quantite = ?, date_reservation = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, reservation.getNom_client());
            pst.setString(2, reservation.getPrenom_client());
            pst.setInt(3, reservation.getNum_tel());
            pst.setInt(4, reservation.getQuantite());
            pst.setDate(5, new java.sql.Date(reservation.getDate_reservation().getTime()));
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
                    reservation.setPrenom_client("prenom_client");
                    reservation.setNum_tel(rs.getInt("num_tel"));
                    reservation.setQuantite((rs.getInt("quantite")));
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
    public List<Reservation> getRecentlyAddedReservations(int limit) {
        List<Reservation> recentlyAddedList = new ArrayList<>();
        String query = "SELECT * FROM reservation ORDER BY id DESC LIMIT ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setNom_client(rs.getString("nom_client"));
                    reservation.setPrenom_client("Sarra");
                    reservation.setNum_tel(rs.getInt("num_tel"));
                    reservation.setQuantite(rs.getInt("quantite"));
                    reservation.setDate_reservation(rs.getDate("date_reservation"));

                    recentlyAddedList.add(reservation);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des reservation récemment ajoutées: " + e.getMessage());
        }
        return recentlyAddedList;
    }
    public void exportToPDF(Reservation reservation, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);

                contentStream.showText("Reservation ID: " + reservation.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Nom_client: " + reservation.getNom_client());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Prenom_client: " + reservation.getPrenom_client());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Num_client: " + reservation.getNum_tel());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Quantie: " + reservation.getQuantite());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date_reservation: " + reservation.getDate_reservation());
                contentStream.newLineAtOffset(0, -20);
                contentStream.endText();
            }

            document.save(filePath);
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



