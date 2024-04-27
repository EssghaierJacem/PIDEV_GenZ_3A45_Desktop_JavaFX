package tn.esprit.Controllers.Participation;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entites.Participation;
import tn.esprit.services.ParticipationServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ParticipationByID_BackController implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

    @FXML
    private Label participation_Id;

    @FXML
    private Label participation_nom;

    @FXML
    private Label participation_prenom;

    @FXML
    private Label participation_tel;

    @FXML
    private Label participation_email;

    private Participation currentParticipation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setParticipationData(Participation participation) {
        this.currentParticipation = participation;
        participation_Id.setText(String.valueOf(participation.getId()));
        participation_nom.setText(participation.getNom());
        participation_prenom.setText(participation.getPrenom());
        participation_tel.setText(String.valueOf(participation.getTel()));
        participation_email.setText(participation.getEmail());

    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Participation/UpdateParticipation.fxml"));
        Parent root = loader.load();

        UpdateParticipationController updateParticipationController = loader.getController();
        updateParticipationController.populateFieldsWithData(currentParticipation);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr que vous voulez supprimer?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ParticipationServices participationServices = new ParticipationServices();
                participationServices.removeParticipation(currentParticipation.getId());

                Stage stage = (Stage) Delete.getScene().getWindow();
                stage.close();
            }
        });
    }
}
