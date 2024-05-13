package tn.esprit.Controllers.Event;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.Event;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.EventServices;
import tn.esprit.entites.User;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
public class UpdateEventController implements Initializable {
    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateEvent;

    @FXML
    private TableView<Event> eventTableView;

    @FXML
    private TableColumn<Event, Integer> event_cell_id;

    @FXML
    private TableColumn<Event, String> event_cell_nom;

    @FXML
    private TableColumn<Event, String> event_cell_lieu;

    @FXML
    private TableColumn<Event, String> event_cell_description;

    @FXML
    private TableColumn<Event, String> event_cell_organisateur;

    @FXML
    private TableColumn<Event, String> event_cell_dateD;

    @FXML
    private TableColumn<Event, String> event_cell_dateF;

    @FXML
    private TextField updateNom;

    @FXML
    private ComboBox<String> updateLieu;

    @FXML
    private TextField updateDescription;

    @FXML
    private TextField updateOrganisateur;

    @FXML
    private DatePicker updateDateD;

    @FXML
    private DatePicker updateDateF;

    @FXML
    private TextField updateImage;

    @FXML
    private TextField updatePrix;

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

    private EventServices eventServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }

        eventServices = new EventServices();
        fillEventTableView();

        eventTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillEventTableView() {
        List<Event> eventList = eventServices.getAllEvents();

        event_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        event_cell_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        event_cell_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        event_cell_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        event_cell_organisateur.setCellValueFactory(new PropertyValueFactory<>("organisateur"));

        eventTableView.getItems().addAll(eventList);
    }

    void populateFieldsWithData(Event event) {
        updateNom.setText(event.getNom());
        updateDescription.setText(event.getDescription());
        updateOrganisateur.setText(event.getOrganisateur());
        updatePrix.setText(String.valueOf(event.getPrix()));
        updateImage.setText(event.getImage());


    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updateNom.clear();
        updateDescription.clear();
        updateOrganisateur.clear();
        updateImage.clear();
        updateDescription.clear();
        updatePrix.clear();
//        updateDateD.setValue(null);
//        updateDateF.setValue(null);

    }

    @FXML
    private void handleUpdateEventButtonAction(ActionEvent event) {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            if (validateInputs()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour l evenement?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedEvent.setNom(updateNom.getText());
//                selectedEvent.setLieu(updateLieu.getItems().toString());
                selectedEvent.setDescription(updateDescription.getText());
                selectedEvent.setOrganisateur(updateOrganisateur.getText());
//                java.sql.Date dateDebut = java.sql.Date.valueOf(updateDateD.getValue());
//                selectedEvent.setDate_debut(dateDebut);
//                java.sql.Date dateFin = java.sql.Date.valueOf(updateDateF.getValue());
//                selectedEvent.setDate_fin(dateFin);
                selectedEvent.setImage(updateImage.getText());
                selectedEvent.setPrix((float) Double.parseDouble(updatePrix.getText()));


                eventServices.updateEvent(selectedEvent);

                eventTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("evenement mise à jour avec succès");
                successAlert.showAndWait();
                }
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucun evenement sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner un evenement à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

    private boolean validateInputs() {
        String nom = updateNom.getText();
        String organisateur = updateOrganisateur.getText();
        String description = updateDescription.getText();
        String prix = updatePrix.getText();
        StringBuilder errorMessage = new StringBuilder();


        if (nom.length() < 4 || nom.length() > 20) {
            errorLabel.setText("Le nom de l evenement doit avoir entre 4 et 20 caractères.");
            return false;
        }

        if (organisateur.length() < 4 || organisateur.length() > 20) {
            errorLabel.setText("L organisateur doit avoir entre 4 et 20 caractères.");
            return false;
        }

        if (description.length() < 4 || description.length() > 6) {
            errorLabel.setText("La description doit avoir entre 4 et 20 caractères.");
            return false;
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
