package tn.esprit.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entites.*;
import tn.esprit.interfaces.IDestinationService;
import tn.esprit.interfaces.IGuideService;
import tn.esprit.interfaces.ITourneeService;
import tn.esprit.tools.MyConnection;



import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourneeServices implements ITourneeService<Tournee> {


    @Override
    public void addTournee(Tournee tournee, int destinationId, int guideId) {
        String query = "INSERT INTO tournee(destination_id, guide_id, nom, date_debut, duree, description, tarif, monuments, tranche_age, moyen_transport) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, destinationId);
            pst.setInt(2, guideId);
            pst.setString(3, tournee.getNom());
            pst.setDate(4, new java.sql.Date(tournee.getDate_debut().getTime()));
            pst.setString(5, tournee.getDuree());
            pst.setString(6, tournee.getDescription());
            pst.setDouble(7, tournee.getTarif());
            pst.setString(8, tournee.getMonuments());
            pst.setString(9, tournee.getTranche_age());
            pst.setString(10, tournee.getMoyen_transport());

            pst.executeUpdate();
            System.out.println("Tournee ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la tournee: " + e.getMessage());
        }
    }

    @Override
    public void removeTournee(int id) {

        String query = "DELETE FROM tournee WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("tournee supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la tournee: " + e.getMessage());
        }
    }

    @Override
    public void updateTournee(Tournee tournee) {



        String query = "UPDATE tournee SET destination_id= ?, guide_id= ?, nom= ?, date_debut= ?, duree= ?, description= ?, tarif= ?, monuments= ?, tranche_age= ?, moyen_transport= ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, tournee.getDestination().getId());
            pst.setInt(2, tournee.getGuide().getId());
            pst.setString(3, tournee.getNom());
            pst.setDate(4, new java.sql.Date(tournee.getDate_debut().getTime()));
            pst.setString(5, tournee.getDuree());
            pst.setString(6, tournee.getDescription());
            pst.setDouble(7, tournee.getTarif());
            pst.setString(8, tournee.getMonuments());
            pst.setString(9, tournee.getTranche_age());
            pst.setString(10, tournee.getMoyen_transport());
            pst.setInt(11, tournee.getId());

            pst.executeUpdate();
            System.out.println("Tournee ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la tournee: " + e.getMessage());
        }
    }

    @Override
    public Tournee getTourneeById(int id) {
        String query = "SELECT * FROM tournee WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {


                if (rs.next()) {
                    Tournee tournee = new Tournee();
                    tournee.setId(rs.getInt("id"));
                    IDestinationService<Destination> destinationService = new DestinationServices();
                    Destination destination = destinationService.getDestinationById(rs.getInt("destination_id"));
                    IGuideService<Guide> guideService = new GuideServices();
                    Guide guide = guideService.getGuideById(rs.getInt("guide_id"));
                    tournee.setDestination(destination);
                    tournee.setGuide(guide);
                    tournee.setNom(rs.getString("nom"));
                    tournee.setDate_debut(rs.getDate("date_debut"));
                    tournee.setDuree(rs.getString("duree"));
                    tournee.setDescription(rs.getString("description"));
                    tournee.setTarif(rs.getDouble("tarif"));
                    tournee.setMonuments(rs.getString("monuments"));
                    tournee.setTranche_age(rs.getString("tranche_age"));
                    tournee.setMoyen_transport(rs.getString("moyen_transport"));

                    return tournee;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la tournee: " + e.getMessage());
        }
        return null;

    }

    @Override
    public List getAllTournees() {
        List<Tournee> tourneeList = new ArrayList<>();
        String query = "SELECT * FROM tournee";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {

            try (ResultSet rs = pst.executeQuery()) {


                while (rs.next()) {
                    Tournee tournee = new Tournee();
                    tournee.setId(rs.getInt("id"));
                    IDestinationService<Destination> destinationService = new DestinationServices();
                    Destination destination = destinationService.getDestinationById(rs.getInt("destination_id"));
                    IGuideService<Guide> guideService = new GuideServices();
                    Guide guide = guideService.getGuideById(rs.getInt("guide_id"));
                    tournee.setDestination(destination);
                    tournee.setGuide(guide);
                    tournee.setNom(rs.getString("nom"));
                    tournee.setDate_debut(rs.getDate("date_debut"));
                    tournee.setDuree(rs.getString("duree"));
                    tournee.setDescription(rs.getString("description"));
                    tournee.setTarif(rs.getDouble("tarif"));
                    tournee.setMonuments(rs.getString("monuments"));
                    tournee.setTranche_age(rs.getString("tranche_age"));
                    tournee.setMoyen_transport(rs.getString("moyen_transport"));

                    tourneeList.add(tournee);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tournee: " + e.getMessage());
        }

        return tourneeList;
    }
    public void exportToPDF(Tournee tournee, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);

                contentStream.showText("Tournée ID : " + tournee.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Destination : " + tournee.getDestination().getVille() + ", " + tournee.getDestination().getPays());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Guide : " + tournee.getGuide().getNom() + ", " + tournee.getGuide().getPrenom());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Nom : " + tournee.getNom());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date de debut : " + tournee.getDate_debut());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Durée : " + tournee.getDuree());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Description : " + tournee.getDescription());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Tarif : " + tournee.getTarif() + " €");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Monuments : " + tournee.getMonuments());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Moyen de transport : " + tournee.getMoyen_transport() );
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Tranche d'age: " + tournee.getTranche_age() );


                contentStream.endText();
            }

            document.save(filePath);
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating PDF: " + e.getMessage());
        }
    }
    public Map<String, Integer> getToursPerDestination() {
        Map<String, Integer> destinationTourCount = new HashMap<>();
        String query = "SELECT d.ville AS destination_name, d.pays AS destination_country, COUNT(*) AS tour_count " +
                "FROM tournee t " +
                "INNER JOIN destination d ON t.destination_id = d.id " +
                "GROUP BY d.ville, d.pays";

        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String destinationName = rs.getString("destination_name");
                String destinationCountry = rs.getString("destination_country");
                int tourCount = rs.getInt("tour_count");
                String destinationKey = destinationName + ", " + destinationCountry;
                destinationTourCount.put(destinationKey, tourCount);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tours per destination: " + e.getMessage());
        }
        return destinationTourCount;
    }




}
