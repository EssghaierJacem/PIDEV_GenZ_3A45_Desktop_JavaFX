package tn.esprit.Controllers.Destination;

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
import tn.esprit.entites.User;


import javafx.stage.Stage;
import tn.esprit.Controllers.Destination.DestinationByID_BackController;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class DestinationController_Back implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetDestination;

    @FXML
    private JFXButton Update;
    @FXML
    private JFXButton destinationButton;
    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton volButton;

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
    private TableColumn<Destination, String> destination_cell_abbrev;

    @FXML
    private TableColumn<Destination, String> destination_cell_attractions;

    @FXML
    private TableColumn<Destination, String> destination_cell_accomodation;

    @FXML
    private TableColumn<Destination, Boolean> destination_cell_access;

    @FXML
    private TableColumn<Destination, String> destination_cell_description;

    @FXML
    private TableView<Destination> destinationTableView;
    @FXML
    private TextField keywordSearch;
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

    private List<Destination> originalDestinationList;

    private Timer searchTimer = new Timer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        populateDestinationTableView();

        keywordSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTimer.cancel();
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    filterAndUpdateDestinationTable(newValue);
                }
            }, 100);
        });
    }

    private void populateDestinationTableView() {
        DestinationServices destinationServices = new DestinationServices();
        originalDestinationList = destinationServices.getAllDestinations();

        destination_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        destination_cell_pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        destination_cell_ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        destination_cell_devise.setCellValueFactory(new PropertyValueFactory<>("devise"));
        destination_cell_cuisinelocale.setCellValueFactory(new PropertyValueFactory<>("cuisine_locale"));
        destination_cell_abbrev.setCellValueFactory(new PropertyValueFactory<>("abbrev"));
        destination_cell_attractions.setCellValueFactory(new PropertyValueFactory<>("attractions"));
        destination_cell_accomodation.setCellValueFactory(new PropertyValueFactory<>("accomodation"));
        destination_cell_access.setCellValueFactory(new PropertyValueFactory<>("accessibilite"));
        destination_cell_description.setCellValueFactory(new PropertyValueFactory<>("description"));

        destinationTableView.getItems().addAll(originalDestinationList);
    }

    private void filterAndUpdateDestinationTable(String keyword) {
        new Thread(() -> {
            List<Destination> filteredDestinationList = new ArrayList<>();

            for (Destination destination : originalDestinationList) {
                if (destination.getPays().toLowerCase().contains(keyword.toLowerCase()) ||
                        destination.getVille().toLowerCase().contains(keyword.toLowerCase()) ||
                        destination.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredDestinationList.add(destination);
                }
            }

            Platform.runLater(() -> {
                destinationTableView.getItems().clear();

                destinationTableView.getItems().addAll(filteredDestinationList);
            });
        }).start();
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Destination selectedDestination = destinationTableView.getSelectionModel().getSelectedItem();

        if (selectedDestination != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DestinationServices destinationServices = new DestinationServices();
                    destinationServices.removeDestination(selectedDestination.getId());

                    destinationTableView.getItems().remove(selectedDestination);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune destination sélectionnée");
            alert.setContentText("Veuillez choisir une destination à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Destination selectedDestination = destinationTableView.getSelectionModel().getSelectedItem();

        if (selectedDestination == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune destination sélectionnée");
            alert.setContentText("Veuillez choisir une destination à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/UpdateDestination.fxml"));
        Parent root = loader.load();

        UpdateDestinationController updateDestinationController = loader.getController();
        updateDestinationController.populateFieldsWithData(selectedDestination);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleAjouterDestinationButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Destination/AddDestination.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Destination selectedDestination = destinationTableView.getSelectionModel().getSelectedItem();

        if (selectedDestination == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune destination sélectionné");
            alert.setContentText("Veuillez choisir une destination à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/DestinationByID_Back.fxml"));
        Parent root = loader.load();

        DestinationByID_BackController destinationByIDBackController = loader.getController();

        destinationByIDBackController.setDestinationData(selectedDestination);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
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
