package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONObject;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DestinationMap implements Initializable {

    @FXML
    private JFXButton Logout;

    @FXML
    private Label connectedUser_Username;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private WebView mapWebView;

    @FXML
    private JFXButton volButton;

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

            Stage currentStage = (Stage) volButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List of Vols");

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        DestinationServices destinationServices = new DestinationServices();

        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }

        WebEngine webEngine = mapWebView.getEngine();
        String htmlFilePath = getClass().getResource("/Destination/Map.html").toExternalForm();
        webEngine.load(htmlFilePath);

        List<Destination> destinations = destinationServices.getAllDestinations();
        Map<String, Integer> countryCounts = new HashMap<>();
        for (Destination destination : destinations) {
            String country = destination.getAbbrev();
            if (country != null && !country.isEmpty()) {
                countryCounts.put(country, countryCounts.getOrDefault(country, 0) + 1);
            }
        }

        JSONObject countryCountsJson = new JSONObject(countryCounts);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                String escapedJson = countryCountsJson.toString().replace("\\", "\\\\").replace("\"", "\\\"");
                webEngine.executeScript("initializeMap(\"" + escapedJson + "\");");
            }
        });

        System.out.println("JSON Data: " + countryCountsJson.toString());
    }
}
