package tn.esprit.Controllers.Participation;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import tn.esprit.entites.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.EmailSender;
import tn.esprit.entites.Event;
import tn.esprit.entites.Participation;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.EventServices;
import tn.esprit.services.ParticipationServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddParticipationController implements Initializable{

    @FXML
    private JFXButton AddParticipation;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addEmail;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addPrenom;

    @FXML
    private TextField addTel;

    @FXML
    private ComboBox<Event> events;

    @FXML
    private Label errorLabel;
    //Buttons

    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton commandeButton;

    @FXML
    private Label connectedUser_Username;

    @FXML
    private JFXButton destinationButton;


    @FXML
    private JFXButton dashboardButton;

    @FXML
    private JFXButton eventsButton;

    @FXML
    private JFXButton guideButton;

    @FXML
    private JFXButton participationButton;

    @FXML
    private JFXButton reservationButton;

    @FXML
    private JFXButton tourneeButton;

    @FXML
    private JFXButton volButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        loadEvents();
    }

    @FXML
    void handleAddParticipationButtonAction(ActionEvent event) {
        String nom = addNom.getText();
        String prenom = addPrenom.getText();
        String telStr = addTel.getText();
        String email = addEmail.getText();

        if (nom.isEmpty() || prenom.isEmpty() || telStr.isEmpty() || email.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs.");
            return; // Stop execution if any field is empty
        }

        // Check if nom has between 3 and 10 characters
        if (nom.length() < 3 || nom.length() > 10) {
            errorLabel.setText("Le nom doit avoir entre 3 et 10 caractères.");
            return;
        }

        // Check if prenom has between 3 and 10 characters
        if (prenom.length() < 3 || prenom.length() > 10) {
            errorLabel.setText("Le prénom doit avoir entre 3 et 10 caractères.");
            return;
        }

        // Check if tel contains exactly 8 digits
        if (!telStr.matches("\\d{8}")) {
            errorLabel.setText("Numéro de téléphone invalide. Il doit contenir exactement 8 chiffres.");
            return;
        }

        int tel;
        try {
            // Parse the tel string to an integer
            tel = Integer.parseInt(telStr);
        } catch (NumberFormatException e) {
            errorLabel.setText("Numéro de téléphone invalide. Veuillez saisir uniquement des chiffres.");
            return;
        }

        // Perform additional email validation if needed
        // For simplicity, we're just checking if it contains '@'
        if (!email.contains("@")) {
            errorLabel.setText("Adresse email invalide. Veuillez saisir une adresse email valide.");
            return;
        }

        // If all validations pass, proceed with creating the participation
        Participation newParticipation = new Participation();
        newParticipation.setNom(nom);
        newParticipation.setPrenom(prenom);
        newParticipation.setTel(tel);
        newParticipation.setEmail(email);

        Event selectedEvent = events.getValue();

        if (selectedEvent != null) {
            ParticipationServices participationServices = new ParticipationServices();
            participationServices.addParticipation(newParticipation, selectedEvent.getId());

            // Send welcome email after successfully adding participation
            EmailSender.sendWelcomeEmailWithSignature(newParticipation.getEmail(), newParticipation.getNom());

            clearFields();
            errorLabel.setText("Participation ajoutée avec succès !");
        } else {
            errorLabel.setText("Veuillez sélectionner un événement.");
        }
    }

    private void clearFields() {
        addNom.clear();
        addPrenom.clear();
        addTel.clear();
        addEmail.clear();
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }

    private void loadEvents() {
        EventServices eventServices = new EventServices();
        events.setItems(FXCollections.observableArrayList(eventServices.getAllEvents()));
    }
    @FXML
    void Logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();

        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) Logout.getScene().getWindow();

            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);

            currentStage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToDestination(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/ListDestination_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) destinationButton.getScene().getWindow();
            Scene newScene = new Scene(root);

            currentStage.setScene(newScene);
            currentStage.setTitle("List of Destinations");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToVol(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/ListVol_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) volButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List of Vols");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void goToCommande(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commande/ListCommande_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) commandeButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List des commandes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/BackDashboard.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) dashboardButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("ADMIN - Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void goToEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/ListEvent_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) eventsButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List of Events");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToGuide(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/Guide_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) guideButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des guides");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToParticipation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Participation/ListParticipation_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) participationButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des participations");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/ListReservation_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) reservationButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des reservation");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToTournee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/Tournee_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) tourneeButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des tourneés");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
