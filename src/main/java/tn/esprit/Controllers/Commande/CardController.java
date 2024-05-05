package tn.esprit.Controllers.Commande;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tn.esprit.entites.Commande;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController  implements Initializable {

    @FXML
    private ImageView ImageViewSample;

    @FXML
    private HBox SampleBox;

    @FXML
    private Label LabelNum_commande;

    @FXML
    private Label labelCode_promo;

    @FXML
    private Label labelType_paiement;

    private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    int randomIndex = (int) (Math.random() * colors.length);
    String randomColor = colors[randomIndex];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Commande commande) {
        LabelNum_commande.setText(commande.getNum_commande());
        labelCode_promo.setText(commande.getCode_promo());
        String style = String.format("-fx-background-color: #%s; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0,10);", randomColor);
        SampleBox.setStyle(style);

    }
}
