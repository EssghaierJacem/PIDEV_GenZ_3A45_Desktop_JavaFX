package tn.esprit.Controllers.Dashboard_J;

import com.jfoenix.controls.JFXButton;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.entites.Vol;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.VolServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardBack_J implements Initializable {

    @FXML
    private JFXButton Logout;

    @FXML
    private Label aerports_total;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private Label devise_count;

    @FXML
    private Label some_tarif;

    @FXML
    private Label user_count;

    @FXML
    private Label connectedUser_Username;

    @FXML
    private JFXButton volButton;
    @FXML
    private LineChart<String, Number> companyRevenue;
    @FXML
    private PieChart topDestination;

    private DestinationServices destinationServices = new DestinationServices();
    private VolServices volServices = new VolServices();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Partie el user
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }

        // Partie mtaa l dashboard
        int userCount = destinationServices.getUserCount();
        user_count.setText(userCount + " Users");

        int totalTarifs = volServices.countAllTarifs();
        some_tarif.setText(totalTarifs + " TND");

        int totalAirports = volServices.countDistinctAirports();
        aerports_total.setText(totalAirports + " Aeroports");

        int distinctDevises = destinationServices.countDistinctDevises();
        devise_count.setText(distinctDevises + " Devises");

        populateCompanyRevenueChart();
        populateTopDestinationChart();
    }
    private void populateCompanyRevenueChart() {
        List<Vol> topCompanies = volServices.getTopCompaniesByRevenue(5);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Company Revenue");
        for (Vol company : topCompanies) {
            series.getData().add(new XYChart.Data<>(company.getCompagnie_a(), company.getTarif()));
        }

        companyRevenue.getData().clear();
        companyRevenue.getData().add(series);
    }




    private void populateTopDestinationChart() {
        if (topDestination == null) {
            System.err.println("Error: PieChart topDestination is null.");
            return;
        }

        List<Destination> topDestinations = destinationServices.getTopDestinationsByUserCount(5);
        topDestination.getData().clear();

        for (Destination destination : topDestinations) {
            int userCount = destination.getUsers().size();
            System.out.println("Adding destination: " + destination.getPays() + " with user count: " + userCount);
            PieChart.Data data = new PieChart.Data(destination.getPays(), userCount);
            topDestination.getData().add(data);
        }

        System.out.println("Top destinations added to PieChart: " + topDestination.getData());
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
    void goToDestination(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/ListDestination_Back.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/ListVol_Back.fxml"));
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
