package tn.esprit.Controllers.Reservation;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.Controllers.Reservation.ReservationByID_BackController;
import tn.esprit.Controllers.Reservation.UpdateReservationController;
import tn.esprit.entites.Reservation;
import tn.esprit.services.ReservationServices;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
    public void initialize(URL location, ResourceBundle resources) {
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


}
