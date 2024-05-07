package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DestinationGrid implements Initializable {

    @FXML
    private ImageView destinationMultimedia;

    @FXML
    private Label GridCuisine;

    @FXML
    private Label GridDevise;

    @FXML
    private Label GridPays;

    @FXML
    private Label GridVille;
    @FXML
    private JFXButton voirPlusButton;
    private Destination destination;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        voirPlusButton.setOnAction(this::onVoirPlusClick);
    }
    public void setData(Destination destination) {
        this.destination=destination;
        GridPays.setText(destination.getPays());
        GridVille.setText(destination.getVille());
        GridDevise.setText(destination.getDevise());
        GridCuisine.setText(destination.getCuisine_locale());
        if (destination.getMultimedia() != null && !destination.getMultimedia().isEmpty()) {
            try {
                Image image = new Image(destination.getMultimedia());
                destinationMultimedia.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL or resource not found: " + destination.getMultimedia());
            }
        }
    }
    @FXML
    private void onVoirPlusClick(ActionEvent event) {
        if (destination == null) {
            System.out.println("Destination object is null in onVoirPlusClick method.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/DestinationByID_Front.fxml"));
            Parent root = loader.load();

           DestinationByID_FrontController controller = loader.getController();
           controller.setDestinationData(destination);

            Stage currentStage = (Stage) voirPlusButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.setTitle("Détails a propos la destination");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
