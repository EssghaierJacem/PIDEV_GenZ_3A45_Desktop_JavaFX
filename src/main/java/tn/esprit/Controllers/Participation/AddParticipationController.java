package tn.esprit.Controllers.Participation;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entites.Participation;
import tn.esprit.services.ParticipationServices;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AddParticipationController implements Initializable {

    @FXML
    private JFXButton AddParticipation;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addPrenom;

    @FXML
    private TextField addTel;

    @FXML
    private TextField addEmail;


    @FXML
    void handleAddParticipationButtonAction(ActionEvent event) {
        Participation newParticipation = new Participation();
        newParticipation.setNom(addNom.getText());
        newParticipation.setPrenom(addPrenom.getText());
        newParticipation.setTel(Integer.parseInt(addTel.getText()));
        newParticipation.setEmail(addEmail.getText());

        ParticipationServices participationServices = new ParticipationServices();
        participationServices.addParticipation(newParticipation);

        clearFields();
    }

    private void clearFields() {
        addNom.clear();
        addPrenom.clear();
        addTel.clear();
        addEmail.clear();
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }

}
