package tn.esprit.Controllers.Participation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import tn.esprit.entites.Participation;

import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class ParticipationGrid implements Initializable {

    @FXML
    private Label GridNom;

    @FXML
    private Label GridPrenom;

    @FXML
    private Label GridTel;

    @FXML
    private Label GridEmail;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Participation participation) {
        GridNom.setText(participation.getNom());
        GridPrenom.setText(participation.getPrenom());
        GridTel.setText(participation.getTel());
        GridEmail.setText(participation.getEmail());
    }
}
