package tn.esprit.Controllers.Destination;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DestinationController_Front implements Initializable {
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane destinationContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
