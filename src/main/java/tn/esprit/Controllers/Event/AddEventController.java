package tn.esprit.Controllers.Event;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Event;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.EventServices;
import javafx.scene.control.Label;
import tn.esprit.entites.User;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class AddEventController implements Initializable{

    @FXML
    private JFXButton AddEvent;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addNom;

    @FXML
    private DatePicker addDateD;

    @FXML
    private DatePicker addDateF;

    @FXML
    private TextField addLieu;

    @FXML
    private TextArea addDescription;

    @FXML
    private TextField addOrganisateur;

    @FXML
    private TextField addImage;

    @FXML
    private TextField addPrix;

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



    @FXML
    void handleAddEventButtonAction(ActionEvent event) {
        if (validateInputs()) {
        Event newEvent = new Event();
        newEvent.setNom(addNom.getText());
        newEvent.setDate_debut(java.sql.Date.valueOf(addDateD.getValue()));
        newEvent.setDate_fin(java.sql.Date.valueOf(addDateF.getValue()));
        newEvent.setLieu(addLieu.getText());
        newEvent.setDescription(addDescription.getText());
        newEvent.setOrganisateur(addOrganisateur.getText());
        newEvent.setImage(addImage.getText());
        newEvent.setPrix(Float.parseFloat(addPrix.getText()));

        EventServices eventServices = new EventServices();
        eventServices.addEvent(newEvent);

        clearFields();}
        else {
            errorLabel.setText("Veuillez remplir tous les champs.");
        }

    }

    private void clearFields() {
        addNom.clear();
        addDateD.setValue(null);
        addDateF.setValue(null);
        addDescription.clear();
        addOrganisateur.clear();
        addImage.clear();
        addPrix.clear();
        addLieu.clear();
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }
    private boolean validateInputs() {
        String nom = addNom.getText();
        String lieu = addLieu.getText();
        String description = addDescription.getText();
        LocalDate date_debut = addDateD.getValue();
        LocalDate date_fin = addDateF.getValue();
        String prix = addPrix.getText();

        StringBuilder errorMessage = new StringBuilder();

        if (nom.isEmpty() || lieu.isEmpty() || description.isEmpty() || date_debut == null || date_fin == null || prix.isEmpty() || prix.equals("A remplir...")) {
            errorMessage.append("Veuillez remplir tous les champs.\n");
        }

        if (nom.length() < 4 || nom.length() > 20) {
            errorMessage.append("Le nom de l'événement doit avoir entre 4 et 20 caractères.\n");
        }

        if (lieu.length() < 4 || lieu.length() > 20) {
            errorMessage.append("Le lieu de l'événement doit avoir entre 4 et 20 caractères.\n");
        }

        if (description.length() < 4 || description.length() > 50) {
            errorMessage.append("La description de l'événement doit avoir entre 4 et 50 caractères.\n");
        }

        if (date_debut.isAfter(LocalDate.now())) {
            errorMessage.append("Veuillez sélectionner une date d'événement valide (passée ou présente).\n");
        }

        if (date_fin.isAfter(LocalDate.now())) {
            errorMessage.append("Veuillez sélectionner une date d'événement valide (passée ou présente).\n");
        }

        try {
            float prixValue = Float.parseFloat(prix);
            if (prixValue <= 0) {
                errorMessage.append("Le prix de l'événement doit être supérieur à zéro.\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Le prix de l'événement doit être un nombre valide.\n");
        }

        if (errorMessage.length() > 0) {
            errorLabel.setText(errorMessage.toString());
            return false;
        }

        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        addNom.setPromptText("Enter event's name");
        addDescription.setPromptText("Enter event's description");
        addOrganisateur.setPromptText("Enter event's organisator");
        addLieu.setPromptText("Enter event's location");

        addDateD.setValue(LocalDate.now());
        addDateF.setValue(LocalDate.now());

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


