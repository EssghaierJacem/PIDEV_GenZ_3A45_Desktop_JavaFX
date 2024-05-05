package tn.esprit.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entites.Commande;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Reservation;
import tn.esprit.interfaces.ICommandeService;
import tn.esprit.interfaces.IReservationService;
import tn.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeServices implements ICommandeService<Commande> {
    @Override
    public void addCommande(Commande commande) {
        String query = "INSERT INTO commande( num_commande, prix, code_promo, type_paiement, email, date_commande) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, commande.getNum_commande());
            pst.setDouble(2, commande.getPrix());
            pst.setString(3, commande.getCode_promo());
            pst.setString(4, commande.getType_paiement());
            pst.setString(5, commande.getEmail());
            pst.setString(6, commande.getDate_commande().toString());
            pst.executeUpdate();
            System.out.println("Commande ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du commande: " + e.getMessage());
        }
    }

    @Override
    public void removeCommande(int id) {

        String query = "DELETE FROM vol WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Commande supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du commande: " + e.getMessage());
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
            pst.setDate(6, new java.sql.Date(commande.getDate_commande().getTime()));
            pst.setInt(7, commande.getId());
            pst.executeUpdate();
            System.out.println("Commande mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du commande: " + e.getMessage());
        }
    }

    @Override
    public Commande getCommandeById(int id) {
        String query = "SELECT * FROM commande WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Commande commande = new Commande();
                    commande.setId(rs.getInt("id"));
                    IReservationService<Reservation> reservationService = new ReservationServices();
                    Reservation reservation = reservationService.getReservationById(rs.getInt("id"));
                    commande.setNum_commande(rs.getString("num_commande"));
                    commande.setPrix(rs.getDouble("prix"));
                    commande.setCode_promo(rs.getString("code_promo"));
                    commande.setType_paiement(rs.getString("type_paiement"));
                    commande.setEmail(rs.getString("email"));
                    commande.setDate_commande(rs.getDate("date_commande"));
                    return commande;

                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération du vol: " + e.getMessage());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Commande> getAllCommandes() {
        List<Commande> commandeList = new ArrayList<>();
        String query = "SELECT * FROM commande";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Commande commande = new Commande();
                    commande.setId(rs.getInt("id"));
                    IReservationService<Reservation> reservationService = new ReservationServices();
                    Reservation reservation = reservationService.getReservationById(rs.getInt("id"));
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

    public List<Commande> getRecentlyAddedCommandes(int limit) {
        List<Commande> recentlyAddedList = new ArrayList<>();
        String query = "SELECT * FROM reservation ORDER BY id DESC LIMIT ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Commande commande = new Commande();
                    commande.setId(rs.getInt("id"));
                    commande.setNum_commande(rs.getString("nom_client"));
                    commande.setCode_promo("Sarra");
                    commande.setType_paiement(rs.getString("num_tel"));
                    commande.setEmail("123456");
                    commande.setDate_commande(rs.getDate("date_commande"));

                    recentlyAddedList.add(commande);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des commandes récemment ajoutées: " + e.getMessage());
        }
        return recentlyAddedList;
    }
    public void exportToPDF(Commande commande, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);

                contentStream.showText("Commande ID: " + commande.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Num_commande: " + commande.getNum_commande());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Prix: " + commande.getPrix());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Code_promo: " + commande.getCode_promo());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email: " + commande.getEmail());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date_commande: " + commande.getDate_commande());
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
