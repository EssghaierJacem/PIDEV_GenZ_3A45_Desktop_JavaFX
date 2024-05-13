package tn.esprit.Controllers.Commande;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import tn.esprit.entites.User;
import tn.esprit.entites.Commande;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.CommandeServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCommandeController implements Initializable {

    @FXML
    private JFXButton AddCommande;

    @FXML
    private JFXButton Clear;

    @FXML
    private Label errorLabel;


    @FXML
    private TextField addCode_promo;

    @FXML
    private DatePicker addDateC;

    @FXML
    private TextField addEmail;

    @FXML
    private TextField addNum_commande;

    @FXML
    private TextField addType;


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
    void handleAddCommandeButtonAction(ActionEvent event) {
        if (validateInputs()) {
        Commande newCommande = new Commande();
        newCommande.setNum_commande(addNum_commande.getText());
        newCommande.setCode_promo(addCode_promo.getText());
        newCommande.setEmail(addEmail.getText());
        newCommande.setType_paiement(addType.getText());
        newCommande.setDate_commande(java.sql.Date.valueOf(addDateC.getValue()));
        CommandeServices commandeServices = new CommandeServices();
        commandeServices.addCommande(newCommande);
        clearFields();}
        else {
            errorLabel.setText("Veuillez remplir tous les champs.");
        }
    }

    private void clearFields() {
        addNum_commande.clear();
        addCode_promo.clear();
        addType.clear();
        addEmail.clear();
        addDateC.setValue(null);
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }
    private boolean validateInputs() {
        String numCommande = addNum_commande.getText();
        String codePromo = addCode_promo.getText();
        String typePaiement = addType.getText();
        String email = addEmail.getText();
        LocalDate dateCommande = addDateC.getValue();

        if (numCommande.length() < 4 || numCommande.length() > 6) {
            errorLabel.setText("Le numéro de commande doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (codePromo.length() < 4 || codePromo.length() > 6) {
            errorLabel.setText("Le code promo doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (typePaiement.length() < 4 || typePaiement.length() > 6) {
            errorLabel.setText("Le type de paiement doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("L'email n'est pas valide.");
            return false;
        }

        if (dateCommande == null || dateCommande.isAfter(LocalDate.now())) {
            errorLabel.setText("Veuillez sélectionner une date de commande valide (passée ou présente).");
            return false;
        }

        return true;
    }
    private boolean isValidEmail(String email) {
        // Regular expression pattern for validating email addresses
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
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
            addNum_commande.setPromptText("");
            addCode_promo.setPromptText("");
            addType.setPromptText("");
            addEmail.setPromptText("");

            addDateC.setValue(LocalDate.now());
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
