package tn.esprit.Controllers.Destination;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.DestinationServices;
import tn.esprit.entites.User;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDestinationController implements Initializable {

    @FXML
    private JFXButton AddDestination;

    @FXML
    private JFXButton Clear;
    @FXML
    private JFXButton destinationButton;
    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton volButton;
    @FXML
    private Label errorLabel;

    @FXML
    private TextField addAbbrev;

    @FXML
    private ComboBox<String> addAccess;

    @FXML
    private TextField addAccomodation;

    @FXML
    private TextField addAttractions;

    @FXML
    private TextField addCuisine;

    @FXML
    private TextArea addDescription;

    @FXML
    private TextField addDevise;

    @FXML
    private TextField addMultimedia;

    @FXML
    private TextField addPays;

    @FXML
    private TextField addVille;
    //Buttons


    @FXML
    private JFXButton commandeButton;

    @FXML
    private Label connectedUser_Username;



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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        addAccess.setItems(FXCollections.observableArrayList("Accessible", "Inaccessible"));
        errorLabel.setVisible(false);
    }

    @FXML
    void handleAddDestinationButtonAction(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        Destination newDestination = new Destination();
        newDestination.setPays(addPays.getText());
        newDestination.setVille(addVille.getText());
        newDestination.setDescription(addDescription.getText());
        newDestination.setAttractions(addAttractions.getText());
        newDestination.setAccomodation(addAccomodation.getText());
        newDestination.setDevise(addDevise.getText());
        newDestination.setMultimedia(addMultimedia.getText());
        newDestination.setCuisine_locale(addCuisine.getText());
        newDestination.setAccessibilite(addAccess.getValue().equals("Accessible"));
        newDestination.setAbbrev(addAbbrev.getText());

        DestinationServices destinationServices = new DestinationServices();
        destinationServices.addDestination(newDestination);

        clearFields();
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
        errorLabel.setVisible(false);
    }

    private void clearFields() {
        addPays.clear();
        addVille.clear();
        addDescription.clear();
        addAttractions.clear();
        addAccomodation.clear();
        addDevise.clear();
        addMultimedia.clear();
        addCuisine.clear();
        addAccess.getSelectionModel().clearSelection();
        addAbbrev.clear();
    }

    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();

        if (!validatePays(errors)) {
            errors.append(" 'Pays' doit comporter entre 4 et 10 caractères.\n");
        }

        if (!validateVille(errors)) {
            errors.append(" 'Ville' doit comporter entre 4 et 10 caractères.\n");
        }

        if (!validateDescription(errors)) {
            errors.append(" La description doit comporter au moins 5 caractères.\n");
        }

        if (!validateAttractions(errors)) {
            errors.append(" 'Attractions' doit comporter au moins 5 caractères.\n");
        }

        if (!validateAccomodation(errors)) {
            errors.append(" 'Accomodation' doit comporter au moins 5 caractères.\n");
        }

        if (!validateMultimedia(errors)) {
            errors.append(" 'Multimedia' doit être une URL valide.\n");
        }

        if (!validateCuisine(errors)) {
            errors.append(" 'Cuisine locale' must not be empty.\n");
        }

        if (!validateAbbrev(errors)) {
            errors.append(" 'Abbreviation (Abbrev)' must be between 1 and 4 uppercase letters.\n");
        }

        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean validatePays(StringBuilder errors) {
        String pays = addPays.getText().trim();
        if (pays.isEmpty() || pays.length() > 10 || pays.length() < 4) {
            return false;
        }
        return true;
    }

    private boolean validateVille(StringBuilder errors) {
        String ville = addVille.getText().trim();
        if (ville.isEmpty() || ville.length() > 10 || ville.length() < 4) {
            return false;
        }
        return true;
    }

    private boolean validateDescription(StringBuilder errors) {
        String description = addDescription.getText().trim();
        if (description.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateAttractions(StringBuilder errors) {
        String attractions = addAttractions.getText().trim();
        if (attractions.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateAccomodation(StringBuilder errors) {
        String accomodation = addAccomodation.getText().trim();
        if (accomodation.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateMultimedia(StringBuilder errors) {
        String multimedia = addMultimedia.getText().trim();
        try {
            new URL(multimedia);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    private boolean validateCuisine(StringBuilder errors) {
        String cuisine = addCuisine.getText().trim();
        if (cuisine.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateAbbrev(StringBuilder errors) {
        String abbrev = addAbbrev.getText().trim();
        if (abbrev.isEmpty() || abbrev.length() > 4 || !abbrev.matches("[A-Z]+")) {
            return false;
        }
        return true;
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
