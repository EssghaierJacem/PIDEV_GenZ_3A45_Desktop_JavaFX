package tn.esprit.Controllers.Vol;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Classe;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Vol;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.VolServices;
import tn.esprit.entites.User;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddVolController implements Initializable {

    @FXML
    private JFXButton AddVol;
    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton volButton;

    @FXML
    private JFXButton ClearVol;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField addAArrivee;

    @FXML
    private TextField addADepart;

    @FXML
    private ComboBox<Classe> addClasse;

    @FXML
    private TextField addCompagnie;

    @FXML
    private DatePicker addDateA;

    @FXML
    private DatePicker addDateD;

    @FXML
    private ComboBox<Destination> addDestination;

    @FXML
    private TextField addDuree;

    @FXML
    private TextField addEscale;

    @FXML
    private TextField addImage;

    @FXML
    private TextField addNumVol;

    @FXML
    private TextField addTarif;
    //Buttons


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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        addClasse.getItems().addAll(Classe.values());
        loadDestinations();
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleAddVolButtonAction(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        Vol newVol = new Vol();
        newVol.setCompagnie_a(addCompagnie.getText());
        newVol.setNum_vol(Integer.parseInt(addNumVol.getText()));
        newVol.setAeroport_depart(addADepart.getText());
        newVol.setAeroport_arrivee(addAArrivee.getText());
        newVol.setDate_depart(java.sql.Date.valueOf(addDateD.getValue()));
        newVol.setDate_arrivee(java.sql.Date.valueOf(addDateA.getValue()));
        newVol.setDuree_vol(Integer.parseInt(addDuree.getText()));
        newVol.setTarif(Float.parseFloat(addTarif.getText()));
        newVol.setEscale(addEscale.getText());
        newVol.setImage(addImage.getText());
        newVol.setClasse(addClasse.getValue());

        Destination selectedDestination = addDestination.getValue();

        VolServices volServices = new VolServices();
        volServices.addVol(newVol, selectedDestination.getId());

        // Clear fields after adding a vol
        clearFields();
    }

    @FXML
    private void handleClearVolButtonAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        addCompagnie.clear();
        addNumVol.clear();
        addADepart.clear();
        addAArrivee.clear();
        addDateD.setValue(null);
        addDateA.setValue(null);
        addDuree.clear();
        addTarif.clear();
        addEscale.clear();
        addImage.clear();
        addClasse.getSelectionModel().clearSelection();
        addDestination.getSelectionModel().clearSelection();
        errorLabel.setVisible(false);
        errorLabel.setText("");
    }

    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        addDestination.getItems().addAll(destinationServices.getAllDestinations());
    }

    private boolean validateInputs() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        StringBuilder errors = new StringBuilder();

        validateCompagnie(errors);
        validateNumVol(errors);
        validateDates(errors);
        validateAeroports(errors);
        validateTarif(errors);
        validateDuree(errors);
        validateImage(errors);
        validateClasse(errors);

        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    // Validation methods
    private void validateCompagnie(StringBuilder errors) {
        String compagnie = addCompagnie.getText().trim();
        if (compagnie.isEmpty() || compagnie.length() < 4 || compagnie.length() > 15) {
            errors.append("'Compagnie' doit comporter entre 4 et 15 lettres.\n");
        }
    }

    private void validateNumVol(StringBuilder errors) {
        try {
            int numVol = Integer.parseInt(addNumVol.getText().trim());
            if (numVol > 10000) {
                errors.append("'Numéro de vol' ne doit pas dépasser 10000.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'Numéro de vol' doit être un nombre valide.\n");
        }
    }

    private void validateDates(StringBuilder errors) {
        if (addDateD.getValue() == null || addDateA.getValue() == null) {
            errors.append("Les dates de départ et d'arrivée sont obligatoires.\n");
            return;
        }
        if (addDateD.getValue().isAfter(addDateA.getValue())) {
            errors.append("La date de départ doit être antérieure à la date d'arrivée.\n");
        }
    }

    private void validateAeroports(StringBuilder errors) {
        String aDepart = addADepart.getText().trim();
        String aArrivee = addAArrivee.getText().trim();
        if (aDepart.isEmpty() || aDepart.length() < 4 || aDepart.length() > 20) {
            errors.append("'Aéroport de départ' doit comporter entre 4 et 20 lettres.\n");
        }
        if (aArrivee.isEmpty() || aArrivee.length() < 4 || aArrivee.length() > 20) {
            errors.append("'Aéroport d'arrivée' doit comporter entre 4 et 20 lettres.\n");
        }
    }

    private void validateTarif(StringBuilder errors) {
        try {
            float tarif = Float.parseFloat(addTarif.getText().trim());
            if (tarif > 5000.0) {
                errors.append("'Tarif' ne doit pas dépasser 5000.0.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'Tarif' doit être un nombre valide.\n");
        }
    }

    private void validateDuree(StringBuilder errors) {
        String duree = addDuree.getText().trim();
        if (duree.isEmpty()) {
            errors.append("'Durée' est obligatoire.\n");
        }
    }

    private void validateImage(StringBuilder errors) {
        String image = addImage.getText().trim();
        try {
            new URL(image);
        } catch (MalformedURLException e) {
            errors.append("'Image' doit être une URL valide.\n");
        }
    }

    private void validateClasse(StringBuilder errors) {
        if (addClasse.getValue() == null) {
            errors.append("'Classe' est obligatoire.\n");
        }
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
