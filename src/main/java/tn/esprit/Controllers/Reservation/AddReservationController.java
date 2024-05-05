package tn.esprit.Controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.entites.Commande;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Reservation;
import tn.esprit.services.CommandeServices;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.ReservationServices;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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
}

