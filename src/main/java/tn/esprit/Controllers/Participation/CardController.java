package tn.esprit.Controllers.Participation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tn.esprit.entites.Participation;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController  implements Initializable {

    @FXML
    private HBox SampleBox;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrenom;

    @FXML
    private Label labelTel;

    @FXML
    private Label labelEmail;

    private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    int randomIndex = (int) (Math.random() * colors.length);
    String randomColor = colors[randomIndex];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Participation participation) {
        labelNom.setText(participation.getNom());
        labelPrenom.setText(participation.getPrenom());
        labelTel.setText(participation.getTel());
        labelEmail.setText(participation.getEmail());

        }
        String style = String.format("-fx-background-color: #%s; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0,10);", randomColor);


    }

