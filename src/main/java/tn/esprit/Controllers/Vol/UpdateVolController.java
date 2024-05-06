package tn.esprit.Controllers.Vol;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.Classe;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Vol;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.VolServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateVolController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton destinationButton;
    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton volButton;

    @FXML
    private JFXButton UpdateVol;

    @FXML
    private TextField updateAA;

    @FXML
    private TextField updateAD;

    @FXML
    private ComboBox<String> updateClasse;

    @FXML
    private TextField updateCompagnie;

    @FXML
    private DatePicker updateDateA;

    @FXML
    private DatePicker updateDateD;

    @FXML
    private ComboBox<String> updateDestination;

    @FXML
    private TextField updateDureeVol;

    @FXML
    private TextField updateEscale;

    @FXML
    private TextField updateImage;

    @FXML
    private TextField updateNumVol;

    @FXML
    private TextField updateTarif;

    @FXML
    private TableView<Vol> volTableView;

    @FXML
    private TableColumn<Vol, Integer> vol_cell_id;

    @FXML
    private TableColumn<Vol, String> vol_cell_destination;

    @FXML
    private TableColumn<Vol, String> vol_cell_compagnie;

    @FXML
    private TableColumn<Vol, String> vol_cell_classe;

    @FXML
    private TableColumn<Vol, String> vol_cell_dateD;

    @FXML
    private Label errorLabel;

    private VolServices volServices;
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
        volServices = new VolServices();
        fillVolTableView();
        loadDestinations();
        loadClasses();

        volTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
        errorLabel.setVisible(false);
    }

    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        List<Destination> destinationList = destinationServices.getAllDestinations();
        for (Destination destination : destinationList) {
            updateDestination.getItems().add(destination.toString());
        }
    }

    private void loadClasses() {
        updateClasse.getItems().addAll("ECONOMIC", "BUSINESS", "FIRST");
    }

    private void fillVolTableView() {
        List<Vol> volList = volServices.getAllVols();

        vol_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        vol_cell_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });
        vol_cell_compagnie.setCellValueFactory(new PropertyValueFactory<>("compagnie_a"));
        vol_cell_classe.setCellValueFactory(new PropertyValueFactory<>("classe"));
        vol_cell_dateD.setCellValueFactory(new PropertyValueFactory<>("date_depart"));

        volTableView.getItems().addAll(volList);
    }

    void populateFieldsWithData(Vol vol) {
        updateCompagnie.setText(vol.getCompagnie_a());
        updateNumVol.setText(String.valueOf(vol.getNum_vol()));
        updateAD.setText(vol.getAeroport_depart());
        updateAA.setText(vol.getAeroport_arrivee());
        updateDureeVol.setText(String.valueOf(vol.getDuree_vol()));
        updateTarif.setText(String.valueOf(vol.getTarif()));
        updateEscale.setText(vol.getEscale());
        updateImage.setText(vol.getImage());
        updateClasse.setValue(vol.getClasse().toString());
        updateDestination.setValue(vol.getDestination().toString());
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        updateCompagnie.clear();
        updateNumVol.clear();
        updateAD.clear();
        updateAA.clear();
        updateDateD.setValue(null);
        updateDateA.setValue(null);
        updateDureeVol.clear();
        updateTarif.clear();
        updateEscale.clear();
        updateImage.clear();
        updateClasse.getSelectionModel().clearSelection();
        updateDestination.setValue(null);
        errorLabel.setVisible(false);
        errorLabel.setText("");
    }

    @FXML
    private void handleUpdateVolButtonAction(ActionEvent event) {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun vol sélectionné", "Veuillez sélectionner un vol à mettre à jour.");
            return;
        }

        if (!validateInputs()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous mettre à jour le vol?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectedVol.setCompagnie_a(updateCompagnie.getText());
            selectedVol.setNum_vol(Integer.parseInt(updateNumVol.getText()));
            selectedVol.setAeroport_depart(updateAD.getText());
            selectedVol.setAeroport_arrivee(updateAA.getText());
            selectedVol.setDate_depart(java.sql.Date.valueOf(updateDateD.getValue()));
            selectedVol.setDate_arrivee(java.sql.Date.valueOf(updateDateA.getValue()));
            selectedVol.setEscale(updateEscale.getText());
            selectedVol.setClasse(Classe.valueOf(updateClasse.getValue()));
            selectedVol.setImage(updateImage.getText());
            selectedVol.setTarif(Float.parseFloat(updateTarif.getText()));
            selectedVol.setDuree_vol(Integer.parseInt(updateDureeVol.getText()));

            volServices.updateVol(selectedVol);

            volTableView.refresh();

            showAlert(Alert.AlertType.INFORMATION, "Succès", null, "Vol mis à jour avec succès.");
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
        String compagnie = updateCompagnie.getText().trim();
        if (compagnie.isEmpty() || compagnie.length() < 4 || compagnie.length() > 15) {
            errors.append("'Compagnie' doit comporter entre 4 et 15 lettres.\n");
        }
    }

    private void validateNumVol(StringBuilder errors) {
        try {
            int numVol = Integer.parseInt(updateNumVol.getText().trim());
            if (numVol > 10000) {
                errors.append("'Numéro de vol' ne doit pas dépasser 10000.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'Numéro de vol' doit être un nombre valide.\n");
        }
    }

    private void validateDates(StringBuilder errors) {
        if (updateDateD.getValue() == null || updateDateA.getValue() == null) {
            errors.append("Les dates de départ et d'arrivée sont obligatoires.\n");
            return;
        }
        if (updateDateD.getValue().isAfter(updateDateA.getValue())) {
            errors.append("La date de départ doit être antérieure à la date d'arrivée.\n");
        }
    }

    private void validateAeroports(StringBuilder errors) {
        String aDepart = updateAD.getText().trim();
        String aArrivee = updateAA.getText().trim();
        if (aDepart.isEmpty() || aDepart.length() < 4 || aDepart.length() > 20) {
            errors.append("'Aéroport de départ' doit comporter entre 4 et 20 lettres.\n");
        }
        if (aArrivee.isEmpty() || aArrivee.length() < 4 || aArrivee.length() > 20) {
            errors.append("'Aéroport d'arrivée' doit comporter entre 4 et 20 lettres.\n");
        }
    }

    private void validateTarif(StringBuilder errors) {
        try {
            float tarif = Float.parseFloat(updateTarif.getText().trim());
            if (tarif > 5000.0) {
                errors.append("'Tarif' ne doit pas dépasser 5000.0.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'Tarif' doit être un nombre valide.\n");
        }
    }

    private void validateDuree(StringBuilder errors) {
        String duree = updateDureeVol.getText().trim();
        if (duree.isEmpty()) {
            errors.append("'Durée' est obligatoire.\n");
        }
    }

    private void validateImage(StringBuilder errors) {
        String image = updateImage.getText().trim();
        try {
            new URL(image);
        } catch (MalformedURLException e) {
            errors.append("'Image' doit être une URL valide.\n");
        }
    }

    private void validateClasse(StringBuilder errors) {
        if (updateClasse.getValue() == null) {
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
