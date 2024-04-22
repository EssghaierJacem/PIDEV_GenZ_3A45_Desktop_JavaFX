package tn.esprit.Controllers.Vol;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.Controllers.Vol.CardController;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Vol;
import tn.esprit.services.VolServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VolController_Front implements Initializable {

    @FXML
    private HBox cardLayout;
    @FXML
    private JFXButton Logout;

    @FXML
    private GridPane volContainer;
    @FXML
    private JFXButton destinationButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VolServices volServices = new VolServices();
        List<Vol> recentlyAdded = volServices.getRecentlyAddedVols(5);

        int column =0;
        int row = 1;


        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Vol/Card.fxml"));

                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardBox);
            }

            List<Vol> allAddedVols = volServices.getAllVols();


            for (Vol vol : allAddedVols) {
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
    @FXML
    private void goToDestination(ActionEvent event) {
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
}