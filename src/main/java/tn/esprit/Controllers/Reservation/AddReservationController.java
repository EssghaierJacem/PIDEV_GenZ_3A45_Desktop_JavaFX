package tn.esprit.Controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import tn.esprit.entites.User;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Commande;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Reservation;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.CommandeServices;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.ReservationServices;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddReservationController implements Initializable {

    @FXML
    private JFXButton AddReservation;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addNom_client;

    @FXML
    private TextField addPrenom_client;

    @FXML
    private TextField addNum_tel;

    @FXML
    private TextField addQuantite;

    @FXML
    private DatePicker addDateR;
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


    public static final String ACCOUNT_SID = "AC9e17e31fc96e03da676d3dec6660a311";
    public static final String AUTH_TOKEN = "0b01f8eced8992d189513e68185a36b8";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @FXML
    void handleAddReservationButtonAction(ActionEvent event) {
        if (validateInputs()) {
        Reservation newReservation = new Reservation();
        newReservation.setNom_client(addNom_client.getText());
        newReservation.setPrenom_client(addPrenom_client.getText());
        newReservation.setNum_tel(Integer.parseInt(addNum_tel.getText()));
        newReservation.setQuantite(Integer.parseInt(addQuantite.getText()));
        newReservation.setDate_reservation(java.sql.Date.valueOf(addDateR.getValue()));
        ReservationServices reservationServices = new ReservationServices();
        reservationServices.addReservation(newReservation);
            String recipientPhoneNumber = "+21652308505";
            String messageBody = "Reservation is set!!";
            sendSMS(recipientPhoneNumber, messageBody);


        clearFields();}
        else {
            errorLabel.setText("Veuillez remplir tous les champs correctement.");
        }
    }
    private boolean validateInputs() {
        String nomClient = addNom_client.getText();
        String prenomClient = addPrenom_client.getText();
        String numTel = addNum_tel.getText();
        String quantite = addQuantite.getText();
        LocalDate dateReservation = addDateR.getValue();

        if (nomClient.isEmpty() || prenomClient.isEmpty() || numTel.isEmpty() || quantite.isEmpty() || dateReservation == null) {
            return false; // Au moins un champ est vide
        }

        if (numTel.length() != 8 || !isNumeric(numTel)) {
            return false; // Le numéro de téléphone doit avoir 8 chiffres
        }

        try {
            int quantiteValue = Integer.parseInt(quantite);
            if (quantiteValue <= 0) {
                return false; // La quantité doit être un nombre positif
            }
        } catch (NumberFormatException e) {
            return false; // La quantité n'est pas un nombre valide
        }

        // Ajoutez d'autres validations selon vos besoins (par exemple, vérification de l'email, etc.)

        return true;
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+"); // Vérifie si la chaîne ne contient que des chiffres
    }

    private void clearFields() {
        addNom_client.clear();
        addPrenom_client.clear();
        addNum_tel.clear();
        addQuantite.clear();
        addDateR.setValue(null);

    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
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

        addNom_client.setPromptText("Enter client's last name");
        addPrenom_client.setPromptText("Enter client's first name");
        addNum_tel.setPromptText("Enter client's phone number");
        addQuantite.setPromptText("Enter reservation quantity");

        // Example of setting a default value for the date picker
        addDateR.setValue(LocalDate.now());
    }
    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        String twilioPhoneNumber = "+15097693487";

        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        messageBody)
                .create();

        System.out.println("SMS sent successfully. SID: " + message.getSid());
    }

    @FXML
    private void sendSMS(ActionEvent event) {

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

