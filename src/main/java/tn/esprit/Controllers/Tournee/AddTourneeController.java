package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import com.twilio.rest.api.v2010.account.Message;
import tn.esprit.entites.User;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Guide;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Tournee;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.GuideServices;
import tn.esprit.services.TourneeServices;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTourneeController implements Initializable {

    @FXML
    private JFXButton Add;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addAge;

    @FXML
    private DatePicker addDate;

    @FXML
    private TextArea addDescription;

    @FXML
    private ComboBox<Destination> addDestination;

    @FXML
    private TextField addDuree;

    @FXML
    private ComboBox<Guide> addGuide;

    @FXML
    private TextField addMonuments;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addTarif;

    @FXML
    private TextField addTransport;

    @FXML
    private Label errorLabel;

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

    private static final String ACCOUNT_SID = "ACde90f2a6ff59a35aa69aa9c5554ce829";
    private static final String AUTH_TOKEN = "6fd2a62e9757cb2ca55fce8bc1088524";

    @FXML
    void handleAddButtonAction(ActionEvent event) {
        if (!validateTourneeInputs()) {
            return;
        }

        // Create a new Tournee object
        Tournee newTournee = new Tournee();
        newTournee.setNom(addNom.getText());
        newTournee.setMonuments(addMonuments.getText());
        newTournee.setDuree(addDuree.getText());
        newTournee.setDate_debut(java.sql.Date.valueOf(addDate.getValue()));
        newTournee.setMoyen_transport(addTransport.getText());
        newTournee.setTarif(Float.parseFloat(addTarif.getText()));
        newTournee.setDescription(addDescription.getText());
        newTournee.setTranche_age(addAge.getText());

        Guide selectedGuide = addGuide.getValue();
        Destination selectedDestination = addDestination.getValue();

        TourneeServices tourneeServices = new TourneeServices();
        tourneeServices.addTournee(newTournee, selectedDestination.getId(), selectedGuide.getId());

        String guidePhoneNumber = "+216" + selectedGuide.getNum_tel();
        String messageBody = "Bonjour Mr " + selectedGuide.getNom() + ", vous êtes affecté à la tournée: " + newTournee.getNom();
        sendSMS(guidePhoneNumber, messageBody);
    }

    @FXML
    void handleClearButtonAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        addNom.clear();
        addMonuments.clear();
        addTarif.clear();
        addTransport.clear();
        addDate.setValue(null);
        addDuree.clear();
        addAge.clear();
        addDescription.clear();
        addGuide.getSelectionModel().clearSelection();
        addDestination.getSelectionModel().clearSelection();
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

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        loadDestinations();
        loadGuides();
        errorLabel.setVisible(false);
    }

    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        addDestination.getItems().addAll(destinationServices.getAllDestinations());
    }

    private void loadGuides() {
        GuideServices guideServices = new GuideServices();
        addGuide.getItems().addAll(guideServices.getAllGuides());
    }

    public static void sendMessageToGuide(Tournee tournee, String messageBody) {
        if (tournee != null && tournee.getGuide() != null) {
            Guide guide = tournee.getGuide();
            String guidePhoneNumber = "+216" + guide.getNum_tel();

            sendSMS(guidePhoneNumber, messageBody);
        } else {
            System.out.println("No guide or tour found. Cannot send SMS.");
        }
    }

    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        String twilioPhoneNumber = "+19513197180";

        try {
            Message message = Message.creator(
                    new PhoneNumber(recipientPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();

            System.out.println("SMS sent successfully. SID: " + message.getSid());
        } catch (com.twilio.exception.ApiException e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }

    private boolean validateTourneeInputs() {
        StringBuilder errors = new StringBuilder();
        if (!validateNomTournee()) {
            errors.append("'Nom de la tournée' doit comporter entre 4 et 20 caractères.\n");
        }

        if (!validateDateDebut()) {
            errors.append("Veuillez sélectionner une date valide pour la 'Date de début'.\n");
        }

        if (!validateDuree()) {
            errors.append("'Durée' doit être spécifiée.\n");
        }

        if (!validateDescription()) {
            errors.append("La 'Description' ne peut pas être vide.\n");
        }

        if (!validateTarif()) {
            errors.append("'Tarif' doit être un nombre valide et ne doit pas dépasser 5000.0.\n");
        }

        if (!validateMonuments()) {
            errors.append("Veuillez spécifier au moins un monument.\n");
        }

        if (!validateTrancheAge()) {
            errors.append("Veuillez spécifier une tranche d'âge valide.\n");
        }

        if (!validateMoyenTransport()) {
            errors.append("Veuillez spécifier un moyen de transport.\n");
        }

        if (errors.length() > 0) {
            // Display the errors
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean validateNomTournee() {
        String nomTournee = addNom.getText().trim();
        return nomTournee != null && nomTournee.length() >= 4 && nomTournee.length() <= 20;
    }

    private boolean validateDateDebut() {
        LocalDate dateDebut = addDate.getValue();
        return dateDebut != null && !dateDebut.isBefore(LocalDate.now());
    }

    private boolean validateDuree() {
        String duree = addDuree.getText().trim();
        return duree != null && !duree.isEmpty();
    }

    private boolean validateDescription() {
        String description = addDescription.getText().trim();
        return description != null && !description.isEmpty();
    }

    private boolean validateTarif() {
        String tarifStr = addTarif.getText().trim();
        if (tarifStr.isEmpty()) {
            return false;
        }
        try {
            double tarif = Double.parseDouble(tarifStr);
            return tarif >= 0 && tarif <= 5000.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateMonuments() {
        String monuments = addMonuments.getText().trim();
        return monuments != null && !monuments.isEmpty();
    }

    private boolean validateTrancheAge() {
        String trancheAge = addAge.getText().trim();
        return trancheAge != null && !trancheAge.isEmpty();
    }

    private boolean validateMoyenTransport() {
        String moyenTransport = addTransport.getText().trim();
        return moyenTransport != null && !moyenTransport.isEmpty();
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
            currentStage.setTitle("Liste des reservations");
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
            currentStage.setTitle("Liste des tournees");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
