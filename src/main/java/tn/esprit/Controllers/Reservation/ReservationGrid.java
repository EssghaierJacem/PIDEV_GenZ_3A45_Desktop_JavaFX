package tn.esprit.Controllers.Reservation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.esprit.entites.Reservation;

import java.net.URL;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;

public class ReservationGrid implements Initializable {

    @FXML
    private Label GridQuantite;

    @FXML
    private Label GridNum_tel;

    @FXML
    private Label GridNom_client;

    @FXML
    private Label GridPrenom_client;
    @FXML
    private Label GridDate_reservation;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Reservation reservation) {
        GridNom_client.setText(reservation.getNom_client());
        GridPrenom_client.setText(reservation.getPrenom_client());
        GridNum_tel.setText(String.valueOf(reservation.getNum_tel()));
        GridQuantite.setText(String.valueOf(reservation.getQuantite()));
        GridDate_reservation.setText(dateFormat.format(String.valueOf(reservation.getDate_reservation())));
        }
    }

