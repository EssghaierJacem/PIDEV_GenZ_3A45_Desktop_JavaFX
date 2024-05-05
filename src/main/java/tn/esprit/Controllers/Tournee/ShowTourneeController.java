package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entites.Tournee;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ShowTourneeController implements Initializable {

    @FXML
    private JFXButton Update;

    @FXML
    private Label age;

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Label destination;

    @FXML
    private Label duree;

    @FXML
    private Label guide;



    @FXML
    private Label monuments;

    @FXML
    private Label nom;

    @FXML
    private Label tarif;

    @FXML
    private Label transport;

    @FXML
    private Label id;

    private Tournee currentTournee;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    public void setTourneeData(Tournee tournee) {
        this.currentTournee = tournee;
        id.setText(String.valueOf(tournee.getId()));
        nom.setText(String.valueOf(tournee.getNom()));
        date.setText(dateFormat.format(tournee.getDate_debut()));
        duree.setText(tournee.getDuree());
        desc.setText(tournee.getDescription());
        monuments.setText(tournee.getMonuments());
        transport.setText(tournee.getMoyen_transport());
        age.setText(String.valueOf(tournee.getTranche_age()));
        tarif.setText(String.valueOf(tournee.getTarif()));
        guide.setText(tournee.getGuide().toString());
        destination.setText(tournee.getDestination().toString());


    }


    @FXML
    void handleUpdateButtonAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/UpdateTournee.fxml"));
        Parent root = loader.load();

        UpdateTourneeController updateTourneeController = loader.getController();
        updateTourneeController.populateFieldsWithData(currentTournee);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
