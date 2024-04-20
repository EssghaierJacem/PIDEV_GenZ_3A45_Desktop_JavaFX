package tn.esprit.Controllers.Destination;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import tn.esprit.entites.Destination;

import javafx.scene.image.ImageView;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Destination destination) {
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
}
