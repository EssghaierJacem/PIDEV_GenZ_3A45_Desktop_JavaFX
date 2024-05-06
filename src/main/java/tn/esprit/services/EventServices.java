package tn.esprit.services;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Event;
import tn.esprit.interfaces.IEventService;
import tn.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.common.PDStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;



public class EventServices implements IEventService<Event> {
    @Override
    public void addEvent(Event event) {
        String query = "INSERT INTO event(nom, date_debut, date_fin, lieu, description, organisateur, image, prix) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, event.getNom());
            pst.setDate(2, event.getDate_debut());
            pst.setDate(3, event.getDate_fin());
            pst.setString(4, event.getLieu());
            pst.setString(5, event.getDescription());
            pst.setString(6, event.getOrganisateur());
            pst.setString(7, event.getImage());
            pst.setDouble(8, event.getPrix());
            pst.executeUpdate();
            System.out.println("Event ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la event: " + e.getMessage());
        }
    }

    @Override
    public void removeEvent(int id) {
        String query = "DELETE FROM event WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Event supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l Event: " + e.getMessage());
        }
    }

    @Override
    public void updateEvent(Event event) {
        String query = "UPDATE event SET nom = ?, date_debut = ?, date_fin = ?, lieu = ?, description = ?, organisateur = ?, image = ?, prix = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, event.getNom());
            pst.setDate(2, event.getDate_debut());
            pst.setDate(3, event.getDate_fin());
            pst.setString(4, event.getLieu());
            pst.setString(5, event.getDescription());
            pst.setString(6, event.getOrganisateur());
            pst.setString(7, event.getImage());
            pst.setDouble(8, event.getPrix());
            pst.setInt(9, event.getId());
            pst.executeUpdate();
            System.out.println("Event mise à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l event: " + e.getMessage());
        }
    }

    @Override
    public Event getEventById(int id) {
        String query = "SELECT * FROM event WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("id"));
                    event.setNom(rs.getString("nom"));
                    event.setDate_debut(rs.getDate("date_debut"));
                    event.setDate_fin(rs.getDate("date_fin"));
                    event.setLieu(rs.getString("lieu"));
                    event.setDescription(rs.getString("description"));
                    event.setImage(rs.getString("image"));
                    event.setPrix(rs.getFloat("prix"));
                    return event;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l event: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String query = "SELECT * FROM event";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("id"));
                    event.setNom(rs.getString("nom"));
                    event.setDate_debut(rs.getDate("date_debut"));
                    event.setDate_fin(rs.getDate("date_fin"));
                    event.setLieu(rs.getString("lieu"));
                    event.setOrganisateur(rs.getString("organisateur"));
                    event.setDescription(rs.getString("description"));
                    event.setImage(rs.getString("image"));
                    event.setPrix(rs.getFloat("prix"));
                    eventList.add(event);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des events: " + e.getMessage());
        }
        return eventList;

    }
    public List<Event> getRecentlyAddedEvents(int limit) {
        List<Event> recentlyAddedList = new ArrayList<>();
        String query = "SELECT * FROM reservation ORDER BY id DESC LIMIT ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, limit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("id"));
                    event.setNom(rs.getString("nom"));
                    event.setLieu(rs.getString("lieu"));
                    event.setDescription(rs.getString("description"));
                    event.setOrganisateur(rs.getString("organisateur"));
                    event.setDate_debut(rs.getDate("date_debut"));
                    event.setDate_fin(rs.getDate("date_fin"));

                    recentlyAddedList.add(event);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des evenements récemment ajoutées: " + e.getMessage());
        }
        return recentlyAddedList;
    }

    public void exportToPDF(Event event, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);

                contentStream.showText("Event ID: " + event.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Nom: " + event.getNom());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date Début: " + event.getDate_debut());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date Fin: " + event.getDate_fin());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Lieu: " + event.getLieu());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Description: " + event.getDescription());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Prix: " + event.getPrix());
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

