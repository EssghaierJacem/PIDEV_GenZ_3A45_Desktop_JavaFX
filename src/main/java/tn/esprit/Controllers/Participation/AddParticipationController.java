package tn.esprit.Controllers.Participation;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.entites.EmailSender;
import tn.esprit.entites.Event;
import tn.esprit.entites.Participation;
import tn.esprit.services.EventServices;
import tn.esprit.services.ParticipationServices;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
