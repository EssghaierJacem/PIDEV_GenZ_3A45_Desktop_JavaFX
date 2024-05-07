package tn.esprit.Controllers.Event;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.jfoenix.controls.JFXButton;

import javafx.stage.Stage;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.EventServices;
import tn.esprit.entites.Event;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class EventController_Back implements Initializable {
    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetEvent;

    @FXML
    private JFXButton Update;

    @FXML
    private Button rollBackButton;

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
    private TableColumn<Event, Float> event_cell_prix;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    @FXML
    private TableView<Event> eventTableView;
    @FXML
    private TextField keywordSearch;
    private List<Event> originalEventList;
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

    private Timer searchTimer = new Timer();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        addEventShowListData();
        keywordSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTimer.cancel();
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    filterAndUpdateEventTable(newValue);
                }
            }, 100);
        });

    }

    private void addEventShowListData() {
        EventServices eventServices = new EventServices();
        List<Event> eventList = eventServices.getAllEvents();

        event_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        event_cell_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        event_cell_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        event_cell_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        event_cell_organisateur.setCellValueFactory(new PropertyValueFactory<>("organisateur"));
        event_cell_dateD.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        event_cell_dateF.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        event_cell_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        eventTableView.getItems().addAll(eventList);

    }
    private void filterAndUpdateEventTable(String keyword) {
        new Thread(() -> {
            List<Event> filteredEventList = new ArrayList<>();
            EventServices eventServices = new EventServices();
            originalEventList = eventServices.getAllEvents();



            for (Event event : originalEventList) {
                if (event.getNom().toLowerCase().contains(keyword.toLowerCase()) ||
                        event.getLieu().toLowerCase().contains(keyword.toLowerCase()) ||
                        event.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredEventList.add(event);
                }
            }

            Platform.runLater(() -> {
                eventTableView.getItems().clear();

                eventTableView.getItems().addAll(filteredEventList);
            });
        }).start();
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    EventServices eventServices = new EventServices();
                    eventServices.removeEvent(selectedEvent.getId());

                    eventTableView.getItems().remove(selectedEvent);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun evenement sélectionnée");
            alert.setContentText("Veuillez choisir un evenement à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun evenement sélectionnée");
            alert.setContentText("Veuillez choisir un evenement à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/UpdateEvent.fxml"));
        Parent root = loader.load();

        Object updateEventController = loader.getController();
        updateEventController.equals(selectedEvent);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleAjouterEventButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event/AddEvent.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun evenement sélectionné");
            alert.setContentText("Veuillez choisir un evenement à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/EventByID_Back.fxml"));
        Parent root = loader.load();

        EventByID_BackController eventByIDBackController = loader.getController();

        eventByIDBackController.setEventData(selectedEvent);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    public void handleRollBackButtonAction(ActionEvent actionEvent) {
        // Get the button that triggered the event
        Button rollBackButton = (Button) actionEvent.getSource();

        // Get the stage from the button's scene
        Stage stage = (Stage) rollBackButton.getScene().getWindow();

        try {
            // Load the dashboard FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/FrontDashboard.fxml"));
            Parent root = loader.load();

            // Create a new scene with the dashboard
            Scene dashboardScene = new Scene(root);

            // Set the scene in the stage and show it
            stage.setScene(dashboardScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

