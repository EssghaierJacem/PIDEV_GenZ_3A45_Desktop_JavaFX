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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.entites.Reservation;
import tn.esprit.services.ReservationServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;

public class ReservationByID_BackController implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

    @FXML
    private Label reservation_id;

    @FXML
    private Label reservation_nom_client;

    @FXML
    private Label reservation_prenom_client;

    @FXML
    private Label reservation_num_tel;

    @FXML
    private Label reservation_quantite;

    @FXML
    private Label date_R;
    private Label currentReservation;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setReservationData(Reservation reservation) {
        this.currentReservation = reservation_id;
        reservation_id.setText(String.valueOf(reservation.getId()));
        reservation_nom_client.setText(reservation.getNom_client());
        reservation_prenom_client.setText(reservation.getPrenom_client()); // Removed incorrect setter
        reservation_num_tel.setText(String.valueOf(reservation.getNum_tel()));
        reservation_quantite.setText(String.valueOf(reservation.getQuantite())); // Converted int to String
        date_R.setText(dateFormat.format(reservation.getDate_reservation()));
    }


    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/UpdateReservation.fxml"));
        Parent root = loader.load();

        UpdateReservationController updateReservationController = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr que vous voulez supprimer?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && currentReservation != null) {
                ReservationServices reservationServices = new ReservationServices();
                reservationServices.removeReservation(Integer.parseInt(currentReservation.getId()));

                Stage stage = (Stage) Delete.getScene().getWindow();
                stage.close();
            }
        });
    }

}
