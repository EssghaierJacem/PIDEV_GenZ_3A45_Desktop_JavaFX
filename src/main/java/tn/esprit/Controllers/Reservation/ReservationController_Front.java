package tn.esprit.Controllers.Reservation;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Reservation;
import tn.esprit.services.ReservationServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    void handleLogoutAction(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
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

}
