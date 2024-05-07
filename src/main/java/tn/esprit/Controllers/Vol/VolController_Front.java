package tn.esprit.Controllers.Vol;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.Controllers.Vol.CardController;
import tn.esprit.Controllers.Vol.VolGrid;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Vol;
import tn.esprit.services.VolServices;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class VolController_Front implements Initializable {

    @FXML
    private HBox cardLayout;

    @FXML
    private JFXButton Logout;

    @FXML
    private GridPane volContainer;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private TextField keywordSearch;
    @FXML
    private JFXButton dashboardButton;

    @FXML
    private JFXButton mapButton;

    @FXML
    void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/FrontDashboard.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) dashboardButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("User - Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Vol> originalVolList;

    private Timer searchTimer = new Timer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCardLayout();

        populateVolGridPane();

        keywordSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTimer.cancel();
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    filterAndUpdateGridPane(newValue);
                }
            }, 300);
        });
    }

    private void populateCardLayout() {
        VolServices volServices = new VolServices();
        List<Vol> recentlyAdded = volServices.getRecentlyAddedVols(5);

        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Vol/Card.fxml"));

                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));

                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void populateVolGridPane() {
        VolServices volServices = new VolServices();
        originalVolList = volServices.getAllVols();

        int column = 0;
        int row = 1;

        try {
            for (Vol vol : originalVolList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Vol/Grid.fxml"));
                VBox cardBox = fxmlLoader.load();
                VolGrid volGrid = fxmlLoader.getController();
                volGrid.setData(vol);

                volContainer.add(cardBox, column, row);
                GridPane.setMargin(cardBox, new Insets(10));

                column++;
                if (column == 6) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterAndUpdateGridPane(String keyword) {
        new Thread(() -> {
            List<Vol> filteredVolList = new ArrayList<>();

            for (Vol vol : originalVolList) {
                if (vol.getDestination().getPays().toLowerCase().contains(keyword.toLowerCase()) ||
                        vol.getCompagnie_a().toLowerCase().contains(keyword.toLowerCase()) ||
                        vol.getAeroport_depart().toLowerCase().contains(keyword.toLowerCase()) ||
                        vol.getAeroport_arrivee().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredVolList.add(vol);
                }
            }
            Platform.runLater(() -> {
                volContainer.getChildren().clear();

                int column = 0;
                int row = 1;

                try {
                    for (Vol vol : filteredVolList) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/Vol/Grid.fxml"));
                        VBox cardBox = fxmlLoader.load();
                        VolGrid volGrid = fxmlLoader.getController();
                        volGrid.setData(vol);

                        volContainer.add(cardBox, column, row);
                        GridPane.setMargin(cardBox, new Insets(10));

                        column++;
                        if (column == 6) {
                            column = 0;
                            row++;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
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
    void goToMap(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/DestinationMap.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) mapButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Destinations - Map");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            Stage currentStage = (Stage) Logout.getScene().getWindow();

            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
