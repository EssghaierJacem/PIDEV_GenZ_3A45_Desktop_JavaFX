package tn.esprit.Controllers.Vol;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Vol;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class VolByID_FrontController implements Initializable {

    @FXML
    private JFXButton Logout;

    @FXML
    private Label classe;

    @FXML
    private Label compagnieA;

    @FXML
    private Label dateD;

    @FXML
    private Label destination;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private JFXButton destination_mapButton;

    @FXML
    private Label duree_vol;

    @FXML
    private Label escale;

    @FXML
    private JFXButton volButton;

    @FXML
    private Label num_vol;

    @FXML
    private Label tarif;

    @FXML
    private WebView mapWebView;

    private Vol currentVol;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public void setVolData(Vol vol) {
        if (vol == null) {
            System.out.println("Received a null Vol object in setVolData method.");
            return;
        }

        num_vol.setText(String.valueOf(vol.getNum_vol()));
        dateD.setText(dateFormat.format(vol.getDate_depart()));
        duree_vol.setText(String.valueOf(vol.getDuree_vol()));
        tarif.setText(String.valueOf(vol.getTarif()));
        escale.setText(vol.getEscale() != null ? vol.getEscale() : "Pas d'escale");
        compagnieA.setText(vol.getCompagnie_a());
        classe.setText(vol.getClasse().toString());
        destination.setText(vol.getDestination().toString());

        displayMapWithVolData(vol);
    }

    private void displayMapWithVolData(Vol vol) {
        try {
            String apiKey = "pk.f77b5edcf311ded36d066f24fdb9f87c";
            double[] departureCoordinates = getAirportCoordinates(vol.getAeroport_depart(), apiKey);
            double[] arrivalCoordinates = getAirportCoordinates(vol.getAeroport_arrivee(), apiKey);
            double[] layoverCoordinates = vol.getEscale() != null ? getAirportCoordinates(vol.getEscale(), apiKey) : null;

            String htmlFilePath = getClass().getResource("/Vol/Map.html").toExternalForm();
            WebEngine webEngine = mapWebView.getEngine();
            webEngine.load(htmlFilePath);

            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    String script = String.format("initializeMap(%s, %s, %s);",
                            Arrays.toString(departureCoordinates),
                            Arrays.toString(arrivalCoordinates),
                            layoverCoordinates != null ? Arrays.toString(layoverCoordinates) : "null");
                    webEngine.executeScript(script);
                }
            });
        } catch (IOException e) {
            System.out.println("Error fetching airport coordinates: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing JavaScript: " + e.getMessage());
        }
    }

    private double[] getAirportCoordinates(String airportName, String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = String.format("https://us1.locationiq.com/v1/search.php?key=%s&q=%s&format=json",
                apiKey, airportName);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String jsonResponse = response.body().string();
            JSONArray data = new JSONArray(jsonResponse);

            if (data.length() > 0) {
                JSONObject firstResult = data.getJSONObject(0);
                double latitude = firstResult.getDouble("lat");
                double longitude = firstResult.getDouble("lon");

                return new double[]{latitude, longitude};
            }
        }

        return null;
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
    void goToDestMap(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/DestinationMap.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) destination_mapButton.getScene().getWindow();
            Scene newScene = new Scene(root);

            currentStage.setScene(newScene);

            currentStage.setTitle("List of Destinations");

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
