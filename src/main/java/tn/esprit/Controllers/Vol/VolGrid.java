package tn.esprit.Controllers.Vol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entites.Vol;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class VolGrid implements Initializable {

    @FXML
    private Label GridCompagnie;

    @FXML
    private Label GridDateDepart;

    @FXML
    private Label GridDestination;

    @FXML
    private Label GridTarif;

    @FXML
    private ImageView VolImage;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Vol vol) {
        GridCompagnie.setText(vol.getCompagnie_a());
        GridDateDepart.setText(dateFormat.format(vol.getDate_depart()));
        GridDestination.setText(vol.getDestination().toString());
        GridTarif.setText(String.valueOf(vol.getTarif()));
        if (vol.getImage() != null && !vol.getImage().isEmpty()) {
            try {
                Image image = new Image(vol.getImage());
                VolImage.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL or resource not found: " + vol.getImage());
            }
        }

    }
}
