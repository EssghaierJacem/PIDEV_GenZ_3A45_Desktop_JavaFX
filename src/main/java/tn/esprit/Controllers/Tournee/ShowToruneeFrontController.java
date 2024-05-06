package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.JSONObject;
import tn.esprit.entites.Tournee;

import javafx.application.Platform;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowToruneeFrontController implements Initializable {

    @FXML
    private Label Destination;

    @FXML
    private JFXButton Logout;

    @FXML
    private Label age;

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Label destination;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private JFXButton destination_mapButton;

    @FXML
    private Label duree;

    @FXML
    private Label guide;

    @FXML
    private Label monuments;

    @FXML
    private Label nom;

    @FXML
    private Label tarif;

    @FXML
    private Label transport;

    @FXML
    private JFXButton volButton;

    @FXML
    private Label weather;
    private static final String API_KEY = "83f90283ab134e65dc776b2264abb365";
    private HttpClient httpClient = HttpClient.newHttpClient();
    private Tournee currentTournee;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public void setTourneeData(Tournee tournee) {
        this.currentTournee = tournee;
        nom.setText(String.valueOf(tournee.getNom()));
        date.setText(dateFormat.format(tournee.getDate_debut()));
        duree.setText(tournee.getDuree());
        desc.setText(tournee.getDescription());
        monuments.setText(tournee.getMonuments());
        transport.setText(tournee.getMoyen_transport());
        age.setText(String.valueOf(tournee.getTranche_age()));
        tarif.setText(String.valueOf(tournee.getTarif()));
        guide.setText(tournee.getGuide().toString());
        destination.setText(tournee.getDestination().toString());
        fetchAndDisplayWeather();
    }
    private void fetchAndDisplayWeather() {
        String city = currentTournee.getDestination().getVille();
        String country = currentTournee.getDestination().getPays();
        String weatherUrl = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,%s&appid=%s&units=metric",
                city, country, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(weatherUrl))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        JSONObject weatherData = new JSONObject(response.body());

                        double temperature = weatherData.getJSONObject("main").getDouble("temp");
                        String description = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
                        double windSpeed = weatherData.getJSONObject("wind").getDouble("speed");
                        int humidity = weatherData.getJSONObject("main").getInt("humidity");
                        int visibility = weatherData.getInt("visibility");
                        int cloudCoverage = weatherData.getJSONObject("clouds").getInt("all");
                        long sunrise = weatherData.getJSONObject("sys").getLong("sunrise");
                        long sunset = weatherData.getJSONObject("sys").getLong("sunset");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        String sunriseTime = timeFormat.format(new Date(sunrise * 1000));
                        String sunsetTime = timeFormat.format(new Date(sunset * 1000));

                        Platform.runLater(() -> {
                            weather.setText(String.format(
                                    "Temperature: %.1f°C\nDescription: %s\nWind Speed: %.1f m/s\nHumidity: %d%%\nVisibility: %d m\nCloud Coverage: %d%%\nSunrise: %s\nSunset: %s",
                                    temperature, description, windSpeed, humidity, visibility, cloudCoverage, sunriseTime, sunsetTime));
                        });
                    } else {
                        Platform.runLater(() -> {
                            weather.setText("Failed to fetch weather data.");
                        });
                    }
                }).exceptionally(ex -> {
                    Platform.runLater(() -> {
                        weather.setText("Error fetching weather data.");
                    });
                    return null;
                });
    }
    @FXML
    void Logout(ActionEvent event) {

    }

    @FXML
    void goToDestMap(ActionEvent event) {

    }

    @FXML
    void goToDestination(ActionEvent event) {

    }

    @FXML
    void goToVol(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
