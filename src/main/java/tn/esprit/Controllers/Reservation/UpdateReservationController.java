package tn.esprit.Controllers.Reservation;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Reservation;
import tn.esprit.services.ReservationServices;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateReservationController implements Initializable {

    public TextField updateQuantity;
    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateReservation;

    @FXML
    private Button update;

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
    private TextField updateNom_client;

    @FXML
    private TextField updatePrenom_client;

    @FXML
    private TextField  updateNum_tel;

    @FXML
    private DatePicker updateDateR;


    private ReservationServices reservationServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationServices = new ReservationServices();
        fillReservationTableView();

        reservationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillReservationTableView() {
        List<Reservation> reservationList = reservationServices.getAllReservations();

        reservation_cell_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        reservation_cellNom_client.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
        reservation_cell_Prenom_client.setCellValueFactory(new PropertyValueFactory<>("prenom_client"));
        reservation_cell_Num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        reservation_cell_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        reservation_cell_dateR.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
        reservationTableView.getItems().addAll(reservationList);
    }

    void populateFieldsWithData(Reservation reservation) {
        updateNom_client.setText(reservation.getNom_client());

        updatePrenom_client.setText(reservation.getPrenom_client());

        updateNum_tel.setText(String.valueOf(reservation.getNum_tel()));


        updateQuantity.setText(String.valueOf(reservation.getQuantite()));


    }
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updateNom_client.clear();
        updatePrenom_client.clear();
        updateNum_tel.clear();
        updateQuantity.clear();
    }

    @FXML
    private void handleUpdateReservationButtonAction(ActionEvent event) {
        Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour la destination?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedReservation.setNom_client(updateNom_client.getText());
                selectedReservation.setPrenom_client(updatePrenom_client.getText());
                selectedReservation.setNum_tel(Integer.parseInt(updateNum_tel.getText()));
                selectedReservation.setQuantite(Integer.parseInt(updateNum_tel.getText()));

                reservationServices.updateReservation(selectedReservation);

                reservationTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("reservation mise à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucune destination sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner une destination à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

}
