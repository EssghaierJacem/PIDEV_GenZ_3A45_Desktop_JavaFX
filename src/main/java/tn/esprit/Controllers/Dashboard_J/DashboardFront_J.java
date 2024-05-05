package tn.esprit.Controllers.Dashboard_J;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.entites.Vol;
import tn.esprit.services.CurrencyConversionService;
import tn.esprit.services.VolServices;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardFront_J implements Initializable {

    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton commandesButton;

    @FXML
    private Label connectedUser_Username;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private JFXButton eventsButton;

    @FXML
    private JFXButton guideButton;

    @FXML
    private TextField keywordSearch;

    @FXML
    private JFXButton participationButton;

    @FXML
    private JFXButton reservationButton;

    @FXML
    private JFXButton tourneeButton;

    @FXML
    private JFXButton userButton;

    @FXML
    private JFXButton volButton;

    @FXML
    private Label flightNotification;
    @FXML
    private JFXButton calculer;
    @FXML
    private ComboBox<String> currencies;
    @FXML
    private Label resultConversion;

    @FXML
    private Label currencyConversion;
    @FXML
    private TextField conversionText;

    private CurrencyConversionService currencyService = new CurrencyConversionService();
    private VolServices volServices = new VolServices();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }

        updateFlightNotification();
        currencies.getItems().addAll(
                "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CNY", "INR", "SGD", "BRL", "RUB",
                "MXN", "ZAR", "SEK", "CHF", "NZD", "KRW", "NOK", "TRY", "THB", "MYR", "DKK",
                "PLN", "HKD", "PHP", "IDR", "VND", "NGN", "ARS", "COP", "CLP", "EGP", "KWD",
                "ILS", "HUF", "CZK", "PKR", "UAH", "BDT", "LKR", "PEN", "RON", "TWD"
        );
    }

    @FXML
    private void updateFlightNotification() {


        List<Vol> closestFlights = volServices.getClosestFlights();
        if (closestFlights != null && !closestFlights.isEmpty()) {
            int count = 0;
            StringBuilder notificationText = new StringBuilder("Closest Flights:\n");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (Vol flight : closestFlights) {
                notificationText.append("Flight No: ")
                        .append(flight.getNum_vol())
                        .append(" - Departure: ")
                        .append(dateFormat.format(flight.getDate_depart()))
                        .append("\n");
                count++;
                if (count >= 3) {
                    break;
                }

            }
            flightNotification.setText(notificationText.toString());
        } else {
            flightNotification.setText("No upcoming flights found.");
        }
    }

    @FXML
    private void handleCalculate(ActionEvent event) {
        try {
            if (conversionText.getText() == null || conversionText.getText().trim().isEmpty()) {
                resultConversion.setText("Veuillez entrer un montant valide.");
                return;
            }
            float amount = Float.parseFloat(conversionText.getText().trim());
            String baseCurrency = currencies.getSelectionModel().getSelectedItem();
            if (baseCurrency == null) {
                resultConversion.setText("Veuillez sélectionner une devise.");
                return;
            }
            String targetCurrency = "USD";
            float convertedAmount = currencyService.convertCurrency(amount, baseCurrency, targetCurrency);

            resultConversion.setText(String.format(" %.2f %s équivaut à %.2f USD.",
                    amount, baseCurrency, convertedAmount));
        } catch (NumberFormatException e) {
            resultConversion.setText("Veuillez entrer un montant valide.");
        } catch (Exception e) {
            e.printStackTrace();
            resultConversion.setText("Erreur dans la conversion de la devise.");
        }
    }
    // Navigation methods

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/ListDestination_Front.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/ListVol_Front.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) volButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.setTitle("List of Vols");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
