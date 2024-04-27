package tn.esprit.Controllers.Event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tn.esprit.entites.Event;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController  implements Initializable {

    @FXML
    private ImageView ImageViewSample;

    @FXML
    private HBox SampleBox;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelLieu;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelPrix;

    private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    int randomIndex = (int) (Math.random() * colors.length);
    String randomColor = colors[randomIndex];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Event event) {
        labelNom.setText(event.getNom());
        labelLieu.setText(event.getLieu());
        labelDescription.setText(event.getDescription());
        labelPrix.setText(String.valueOf(event.getPrix()));
        if (event.getImage() != null && !event.getImage().isEmpty()) {
            try {
                Image image = new Image(event.getImage());
                ImageViewSample.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL or resource not found: " + event.getImage());
            }
        }
        String style = String.format("-fx-background-color: #%s; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0,10);", randomColor);
        SampleBox.setStyle(style);

    }
}
