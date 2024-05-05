package tn.esprit.Controllers.Guide;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import tn.esprit.entites.Guide;
import tn.esprit.services.GuideServices;

public class ShowGuideController implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private Label Experiences;

    @FXML
    private Label Langues;

    @FXML
    private Label Nationalite;

    @FXML
    private Label Nom;

    @FXML
    private Label Prenom;

    @FXML
    private Label Tarif;

    @FXML
    private Label Tel;

    @FXML
    private JFXButton Update;

    @FXML
    private Label guide_id;
    private Guide currentGuide;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setGuideData (Guide guide) {
        this.currentGuide = guide;
        guide_id.setText(String.valueOf(guide.getId()));
        Nom.setText(guide.getNom());
        Prenom.setText(guide.getPrenom());
        Nationalite.setText(guide.getNationalite());
        Langues.setText(guide.getLangues_parlees());
        Experiences.setText(guide.getExperiences());
        Tarif.setText(String.valueOf(guide.getTarif_horaire()));
        Tel.setText(String.valueOf(guide.getNum_tel()));




    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {

    }

    @FXML
    void handleUpdateButtonAction(ActionEvent event) {

    }

}
