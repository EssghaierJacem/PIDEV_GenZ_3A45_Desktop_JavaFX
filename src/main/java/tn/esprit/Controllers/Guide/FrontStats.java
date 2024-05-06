package tn.esprit.Controllers.Guide;

import com.jfoenix.controls.JFXButton;
import javafx.scene.chart.LineChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.entites.Guide;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.GuideServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FrontStats implements Initializable {

    @FXML
    private JFXButton logoutButton;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private Label moy_tarif;

    private GuideServices guideServices = new GuideServices();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateToursPerGuideBarChart();
        populateTopGuideTariffsLineChart();

        double averageTariff = guideServices.getAverageTariff();
        moy_tarif.setText("" + String.format("%.2f TND", averageTariff));
    }

    private void populateToursPerGuideBarChart() {
        // Clear previous data from the chart
        barChart.getData().clear();

        // Create a series for tours per guide
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tours per Guide");

        // Fetch the tours per guide data from the service
        Map<Guide, Integer> toursPerGuide = guideServices.getToursPerGuide();

        // Add data points to the series
        toursPerGuide.forEach((guide, tourCount) -> {
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(guide.getNom() + " " + guide.getPrenom(), tourCount);
            series.getData().add(dataPoint);
        });

        // Add the series to the bar chart
        barChart.getData().add(series);
    }

    private void populateTopGuideTariffsLineChart() {
        lineChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Guide Tariffs");

        List<Guide> topGuidesByTariff = guideServices.getTopGuidesByTariff(5);

        for (Guide guide : topGuidesByTariff) {
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(guide.getNom() + " " + guide.getPrenom(), guide.getTarif_horaire());
            series.getData().add(dataPoint);
        }

        lineChart.getData().add(series);
    }

    @FXML
    void logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);

            currentStage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
