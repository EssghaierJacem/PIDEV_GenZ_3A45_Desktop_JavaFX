package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.User;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.DestinationServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DestinationController_Front implements Initializable {
    @FXML
    private HBox cardLayout;

    @FXML
    private JFXButton Logout;

    @FXML
    private Label connectedUser_Username;
    @FXML
    private GridPane destinationContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);


        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        DestinationServices destinationServices = new DestinationServices();
        List<Destination> recentlyAdded = destinationServices.getRecentlyAddedDestinations(5);

        int column =0;
        int row = 1;


        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Destination/Card.fxml"));

                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardBox);
            }

            List<Destination> allAddedDestinations = destinationServices.getAllDestinations();


            for (Destination destination : allAddedDestinations) {
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
    @FXML
    void handleLogoutAction(ActionEvent event) {
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