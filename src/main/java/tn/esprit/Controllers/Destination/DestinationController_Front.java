package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.services.DestinationServices;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class DestinationController_Front implements Initializable {

    @FXML
    private HBox cardLayout;

    @FXML
    private JFXButton Logout;

    @FXML
    private Label connectedUser_Username;

    @FXML
    private JFXButton volButton;

    @FXML
    private GridPane destinationContainer;

    @FXML
    private TextField keywordSearch;

    private List<Destination> originalDestinationList;

    private Timer searchTimer = new Timer();
    @FXML
    private JFXButton dashboardButton;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }

        populateCardLayout();
        populateDestinationGridPane();

        keywordSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTimer.cancel();
            searchTimer = new Timer();

            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    filterAndUpdateDestinationGrid(newValue);
                }
            }, 300);
        });
    }

    private void populateCardLayout() {
        DestinationServices destinationServices = new DestinationServices();
        List<Destination> recentlyAdded = destinationServices.getRecentlyAddedDestinations(5);

        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Destination/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void populateDestinationGridPane() {
        DestinationServices destinationServices = new DestinationServices();
        originalDestinationList = destinationServices.getAllDestinations();

        int column = 0;
        int row = 1;

        try {
            for (Destination destination : originalDestinationList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Destination/Grid.fxml"));
                VBox cardBox = fxmlLoader.load();
                DestinationGrid destinationGrid = fxmlLoader.getController();
                destinationGrid.setData(destination);

                destinationContainer.add(cardBox, column, row);
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
    private void filterAndUpdateDestinationGrid(String keyword) {
        new Thread(() -> {
            List<Destination> filteredDestinationList = new ArrayList<>();

            for (Destination destination : originalDestinationList) {
                if (destination.getPays().toLowerCase().contains(keyword.toLowerCase()) ||
                        destination.getVille().toLowerCase().contains(keyword.toLowerCase()) ||
                        destination.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                        destination.getAttractions().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredDestinationList.add(destination);
                }
            }

            Platform.runLater(() -> {
                destinationContainer.getChildren().clear();

                int column = 0;
                int row = 1;

                try {
                    for (Destination destination : filteredDestinationList) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/Destination/Grid.fxml"));
                        VBox cardBox = fxmlLoader.load();
                        DestinationGrid destinationGrid = fxmlLoader.getController();
                        destinationGrid.setData(destination);

                        destinationContainer.add(cardBox, column, row);
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
    private void goToVol(ActionEvent event) {
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
