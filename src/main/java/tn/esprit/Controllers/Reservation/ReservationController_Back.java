package tn.esprit.Controllers.Reservation;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.Controllers.Reservation.ReservationByID_BackController;
import tn.esprit.Controllers.Reservation.UpdateReservationController;
import tn.esprit.entites.Commande;
import tn.esprit.entites.Reservation;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.CommandeServices;
import tn.esprit.services.ReservationServices;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ReservationController_Back implements Initializable {

    public JFXButton AjouterReservation;
    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetReservation;

    @FXML
    private JFXButton Update;

    @FXML
    private TableView<Reservation> reservationTableView;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_Id;

    @FXML
    private TableColumn<Reservation, String> reservation_cellNom_client;

    @FXML
    private TableColumn<Reservation, String> reservation_cell_Prenom_client;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_Num_tel;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_quantite;
    @FXML
    private TableColumn<Reservation, Date> reservation_cell_dateR;

    @FXML
    private TextField keywordSearch;
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
        keywordSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTimer.cancel();
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    filterAndUpdateReservationTable(newValue);
                }
            }, 100);
        });



        addReservationShowListData();
    }

    private void addReservationShowListData() {
        ReservationServices reservationServices = new ReservationServices();
        List<Reservation> reservationList = reservationServices.getAllReservations();

        reservation_cell_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        reservation_cellNom_client.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
        reservation_cell_Prenom_client.setCellValueFactory(new PropertyValueFactory<>("prenom_client"));
        reservation_cell_Num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        reservation_cell_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        reservation_cell_dateR.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
        reservationTableView.getItems().addAll(reservationList);
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ReservationServices reservationServices = new ReservationServices();
                    reservationServices.removeReservation(selectedReservation.getId());

                    reservationTableView.getItems().remove(selectedReservation);
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
        Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Reservation sélectionnée");
            alert.setContentText("Veuillez choisir une Reservation à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/UpdateReservation.fxml"));
        Parent root = loader.load();

        UpdateReservationController updateReservationController = loader.getController();
        updateReservationController.populateFieldsWithData(selectedReservation);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleAjouterReservationButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Reservation/AddReservation.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune destination sélectionné");
            alert.setContentText("Veuillez choisir une Reservation à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/ReservationByID_Back.fxml"));
        Parent root = loader.load();

        ReservationByID_BackController reservationByIDBackController = loader.getController();

        reservationByIDBackController.setReservationData(selectedReservation);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    private void filterAndUpdateReservationTable(String keyword) {
        new Thread(() -> {
            List<Reservation> filteredReservationList = new ArrayList<>();
            ReservationServices reservationServices = new ReservationServices();
            List<Reservation> originalReservationList= reservationServices.getAllReservations();
            for (Reservation reservation :originalReservationList ) {
                if (reservation.getNom_client().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredReservationList.add(reservation);
                }
            }
            Platform.runLater(() -> {
                reservationTableView.getItems().clear();

                reservationTableView.getItems().addAll(filteredReservationList);
            });
        }).start();

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
