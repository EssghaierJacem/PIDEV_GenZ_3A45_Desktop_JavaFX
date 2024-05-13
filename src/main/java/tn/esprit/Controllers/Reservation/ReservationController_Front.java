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
import tn.esprit.entites.Reservation;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.ReservationServices;
import tn.esprit.entites.User;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ReservationController_Front implements Initializable {

    @FXML
    private JFXButton Logout;

    @FXML
    private Label connectedUser_Username;

    @FXML
    private TableView<Reservation> reservationTableView;

    @FXML
    private TableColumn<Reservation, String> reservation_cellNom_client;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_Id;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_Num_tel;

    @FXML
    private TableColumn<Reservation, String> reservation_cell_Prenom_client;

    @FXML
    private TableColumn<Reservation, String> reservation_cell_dateR;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_quantite;
    @FXML
    private TextField keywordSearch;
    private Timer searchTimer = new Timer();
    @FXML
    private JFXButton dashboardButton;

    @FXML
    void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/FrontDashboard.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) dashboardButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("User - Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogoutAction(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }

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
        reservation_cellNom_client.setCellValueFactory(new PropertyValueFactory<>("Nom_client"));
        reservation_cell_Prenom_client.setCellValueFactory(new PropertyValueFactory<>("Prenom_client"));
        reservation_cell_Num_tel.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));
        reservation_cell_quantite.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
        reservation_cell_dateR.setCellValueFactory(new PropertyValueFactory<>("Date_reservation"));

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

    private void filterAndUpdateReservationTable(String keyword) {
        new Thread(() -> {
            List<Reservation> filteredReservationList = new ArrayList<>();
            ReservationServices reservationServices = new ReservationServices();
            List<Reservation> originalReservationList = reservationServices.getAllReservations();
            for (Reservation reservation : originalReservationList) {
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
    private void handleAjouterReservationButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Reservation/AddReservation.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
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

}
