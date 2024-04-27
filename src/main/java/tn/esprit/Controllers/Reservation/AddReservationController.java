package tn.esprit.Controllers.Reservation;

import com.sun.javafx.charts.Legend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.entites.Reservation;
import tn.esprit.services.ReservationServices;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddReservationController implements Initializable {

    @FXML
    private Button AddReservation;

    @FXML
    private Button Clear;

    @FXML
    private TextField addNom_client;

    @FXML
    private TextField addPrenom_client;

    @FXML
    private TextField addNum_tel;

    @FXML
    private TextField addQuantite;

    @FXML
    private DatePicker addDateR;
    private Legend addAccess;

    @FXML
    void handleAddReservationButtonAction(ActionEvent event) {
        Reservation newReservation = new Reservation();
        newReservation.setNom_client(addNom_client.getText());
        newReservation.setPrenom_client(addPrenom_client.getText());
        newReservation.setNum_tel(Integer.parseInt(addNum_tel.getText()));
        newReservation.setQuantite(Integer.parseInt(addQuantite.getText()));
        newReservation.setDate_reservation(java.sql.Date.valueOf(addDateR.getValue()));

        ReservationServices reservationServices = new ReservationServices();
        reservationServices.addReservation(newReservation);

        clearFields();
    }

    private void clearFields() {
        addNom_client.clear();
        addPrenom_client.clear();
        addNum_tel.clear();
        addQuantite.clear();
        addDateR.setValue(null);
    }
    private void handleClear(ActionEvent event) {
        clearFields();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNom_client.setPromptText("Enter client's last name");
        addPrenom_client.setPromptText("Enter client's first name");
        addNum_tel.setPromptText("Enter client's phone number");
        addQuantite.setPromptText("Enter reservation quantity");

        // Example of setting a default value for the date picker
        addDateR.setValue(LocalDate.now());
    }
}

