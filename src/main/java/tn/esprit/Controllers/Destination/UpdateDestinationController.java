package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.DestinationServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateDestinationController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateDestination;
    @FXML
    private JFXButton destinationButton;
    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton volButton;

    @FXML
    private TableView<Destination> destinationTableView;

    @FXML
    private TableColumn<Destination, Integer> destination_cell_id;

    @FXML
    private TableColumn<Destination, String> destination_cell_pays;

    @FXML
    private TableColumn<Destination, String> destination_cell_ville;

    @FXML
    private TableColumn<Destination, String> destination_cell_devise;

    @FXML
    private TableColumn<Destination, String> destination_cell_cuisinelocale;

    @FXML
    private TextField updateAbbrev;

    @FXML
    private ComboBox<String> updateAccess;

    @FXML
    private TextField updateAccomodation;

    @FXML
    private TextField updateAttractions;

    @FXML
    private TextField updateCuisine;

    @FXML
    private TextArea updateDescription;

    @FXML
    private TextField updateDevise;

    @FXML
    private TextField updateMultimedia;

    @FXML
    private TextField updatePays;

    @FXML
    private TextField updateVille;

    @FXML
    private Label errorLabel;
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

    private DestinationServices destinationServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        destinationServices = new DestinationServices();
        fillDestinationTableView();

        destinationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });

        updateAccess.setItems(FXCollections.observableArrayList("Yes", "No"));
        errorLabel.setVisible(false);
    }

    private void fillDestinationTableView() {
        List<Destination> destinationList = destinationServices.getAllDestinations();

        destination_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        destination_cell_pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        destination_cell_ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        destination_cell_devise.setCellValueFactory(new PropertyValueFactory<>("devise"));
        destination_cell_cuisinelocale.setCellValueFactory(new PropertyValueFactory<>("cuisine_locale"));

        destinationTableView.getItems().addAll(destinationList);
    }

    void populateFieldsWithData(Destination destination) {
        updatePays.setText(destination.getPays());
        updateVille.setText(destination.getVille());
        updateDevise.setText(destination.getDevise());
        updateCuisine.setText(destination.getCuisine_locale());
        updateAbbrev.setText(destination.getAbbrev());
        updateAccomodation.setText(destination.getAccomodation());
        updateAttractions.setText(destination.getAttractions());
        updateMultimedia.setText(destination.getMultimedia());
        updateDescription.setText(destination.getDescription());
        updateAccess.getSelectionModel().select(destination.getAccessibilite() ? "Yes" : "No");
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        updatePays.clear();
        updateVille.clear();
        updateDevise.clear();
        updateCuisine.clear();
        updateAbbrev.clear();
        updateAccomodation.clear();
        updateAttractions.clear();
        updateMultimedia.clear();
        updateDescription.clear();
        updateAccess.getSelectionModel().clearSelection();
        errorLabel.setVisible(false);
        errorLabel.setText("");
    }

    @FXML
    private void handleUpdateDestinationButtonAction(ActionEvent event) {
        Destination selectedDestination = destinationTableView.getSelectionModel().getSelectedItem();

        if (selectedDestination == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune destination sélectionnée", "Veuillez sélectionner une destination à mettre à jour.");
            return;
        }

        if (!validateInputs()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous mettre à jour la destination?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectedDestination.setPays(updatePays.getText());
            selectedDestination.setVille(updateVille.getText());
            selectedDestination.setDevise(updateDevise.getText());
            selectedDestination.setCuisine_locale(updateCuisine.getText());
            selectedDestination.setAbbrev(updateAbbrev.getText());
            selectedDestination.setAccomodation(updateAccomodation.getText());
            selectedDestination.setAttractions(updateAttractions.getText());
            selectedDestination.setMultimedia(updateMultimedia.getText());
            selectedDestination.setDescription(updateDescription.getText());
            selectedDestination.setAccessibilite(updateAccess.getSelectionModel().getSelectedItem().equals("Yes"));

            destinationServices.updateDestination(selectedDestination);

            destinationTableView.refresh();

            showAlert(Alert.AlertType.INFORMATION, "Success", null, "Destination mise à jour avec succès.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean validateInputs() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        StringBuilder errors = new StringBuilder();

        validatePays(errors);
        validateVille(errors);
        validateDescription(errors);
        validateAttractions(errors);
        validateAccomodation(errors);
        validateMultimedia(errors);
        validateCuisine(errors);
        validateAbbrev(errors);

        // If there are errors, display them and return false
        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        // If there are no errors, return true
        return true;
    }

    // Validation methods with proper error handling
    private void validatePays(StringBuilder errors) {
        String pays = updatePays.getText().trim();
        if (pays.isEmpty() || pays.length() > 10 || pays.length() < 4) {
            errors.append("'Pays' doit comporter entre 4 et 10 caractères.\n");
        }
    }

    private void validateVille(StringBuilder errors) {
        String ville = updateVille.getText().trim();
        if (ville.isEmpty() || ville.length() > 10 || ville.length() < 4) {
            errors.append("'Ville' doit comporter entre 4 et 10 caractères.\n");
        }
    }

    private void validateDescription(StringBuilder errors) {
        String description = updateDescription.getText().trim();
        if (description.length() < 5) {
            errors.append("'Description' doit comporter au moins 5 caractères.\n");
        }
    }

    private void validateAttractions(StringBuilder errors) {
        String attractions = updateAttractions.getText().trim();
        if (attractions.length() < 5) {
            errors.append("'Attractions' doit comporter au moins 5 caractères.\n");
        }
    }

    private void validateAccomodation(StringBuilder errors) {
        String accomodation = updateAccomodation.getText().trim();
        if (accomodation.length() < 5) {
            errors.append("'Accomodation' doit comporter au moins 5 caractères.\n");
        }
    }

    private void validateMultimedia(StringBuilder errors) {
        String multimedia = updateMultimedia.getText().trim();
        try {
            new URL(multimedia);
        } catch (MalformedURLException e) {
            errors.append("'Multimedia' doit être une URL valide.\n");
        }
    }

    private void validateCuisine(StringBuilder errors) {
        String cuisine = updateCuisine.getText().trim();
        if (cuisine.isEmpty()) {
            errors.append("'Cuisine locale' ne doit pas être vide.\n");
        }
    }

    private void validateAbbrev(StringBuilder errors) {
        String abbrev = updateAbbrev.getText().trim();
        if (abbrev.isEmpty() || abbrev.length() > 4 || !abbrev.matches("[A-Z]+")) {
            errors.append("'Abbreviation' doit comporter entre 1 et 4 lettres majuscules.\n");
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
