package tn.esprit.Controllers.Vol;

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
import tn.esprit.entites.Vol;
import tn.esprit.services.VolServices;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class VolByID_BackController implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

    @FXML
    private Label aeroport_arrivee;

    @FXML
    private Label aeroport_depart;

    @FXML
    private Label classe;

    @FXML
    private Label compagnieA;

    @FXML
    private Label dateA;

    @FXML
    private Label dateD;

    @FXML
    private Label destination;

    @FXML
    private Label duree_vol;

    @FXML
    private Label escale;

    @FXML
    private ImageView imageVol;

    @FXML
    private Label num_vol;

    @FXML
    private Label tarif;
    private Vol currentVol;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setVolData(Vol vol) {
        this.currentVol = vol;
        num_vol.setText(String.valueOf(vol.getNum_vol()));
        aeroport_depart.setText(vol.getAeroport_depart());
        aeroport_arrivee.setText(vol.getAeroport_arrivee());
        dateD.setText(dateFormat.format(vol.getDate_depart()));
        dateA.setText(dateFormat.format(vol.getDate_arrivee()));
        duree_vol.setText(String.valueOf(vol.getDuree_vol()));
        tarif.setText(String.valueOf(vol.getTarif()));
        escale.setText(vol.getEscale());
        compagnieA.setText(vol.getCompagnie_a());
        classe.setText(vol.getClasse().toString());
        destination.setText(vol.getDestination().toString());


        if (vol.getImage() != null) {
            imageVol.setImage(new Image(vol.getImage()));
        }
    }
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/UpdateVol.fxml"));
        Parent root = loader.load();

        UpdateVolController updateVolController = loader.getController();
        updateVolController.populateFieldsWithData(currentVol);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void handleDeleteButtonAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr que vous voulez supprimer?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                VolServices volServices = new VolServices();
                volServices.removeVol(currentVol.getId());

                Stage stage = (Stage) Delete.getScene().getWindow();
                stage.close();
            }
        });
    }
}
