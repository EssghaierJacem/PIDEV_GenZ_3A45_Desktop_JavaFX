package tn.esprit.Controllers.Destination;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.entites.Destination;

import java.net.URL;
import java.util.ResourceBundle;

public class CardController  implements Initializable {

    @FXML
    private ImageView ImageViewSample;

    @FXML
    private HBox SampleBox;

    @FXML
    private Label labelAttractions;

    @FXML
    private Label labelPays;

    @FXML
    private Label labelVille;

    private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    int randomIndex = (int) (Math.random() * colors.length);
    String randomColor = colors[randomIndex];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Destination reservation) {
        labelPays.setText(reservation.getPays());
        labelVille.setText(reservation.getVille());
        labelAttractions.setText(reservation.getAttractions());
        if (reservation.getMultimedia() != null && !reservation.getMultimedia().isEmpty()) {
            try {
                Image image = new Image(reservation.getMultimedia());
                ImageViewSample.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL or resource not found: " + reservation.getMultimedia());
            }
        }
        String style = String.format("-fx-background-color: #%s; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0,10);", randomColor);
        SampleBox.setStyle(style);

    }
}
