package tn.esprit.Controllers.Vol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.Controllers.Vol.CardController;
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
    private GridPane volContainer;

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
}