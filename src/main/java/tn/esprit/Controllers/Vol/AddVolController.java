package tn.esprit.Controllers.Vol;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.entites.Classe;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Vol;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.VolServices;

import java.net.URL;
import java.util.ResourceBundle;

public class AddVolController implements Initializable {

    @FXML
    private JFXButton AddVol;

    @FXML
    private JFXButton ClearVol;

    @FXML
    private TextField addAArrivee;

    @FXML
    private TextField addADepart;

    @FXML
    private ComboBox<Classe> addClasse;

    @FXML
    private TextField addCompagnie;

    @FXML
    private DatePicker addDateA;

    @FXML
    private DatePicker addDateD;

    @FXML
    private ComboBox<Destination> addDestination;

    @FXML
    private TextField addDuree;

    @FXML
    private TextField addEscale;

    @FXML
    private TextField addImage;

    @FXML
    private TextField addNumVol;

    @FXML
    private TextField addTarif;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBoxes
        addClasse.getItems().addAll(Classe.values());

        // Load destinations into ComboBox
        loadDestinations();
    }

    @FXML
    void handleAddVolButtonAction(ActionEvent event) {
        Vol newVol = new Vol();
        newVol.setCompagnie_a(addCompagnie.getText());
        newVol.setNum_vol(Integer.parseInt(addNumVol.getText()));
        newVol.setAeroport_depart(addADepart.getText());
        newVol.setAeroport_arrivee(addAArrivee.getText());
        newVol.setDate_depart(java.sql.Date.valueOf(addDateD.getValue()));
        newVol.setDate_arrivee(java.sql.Date.valueOf(addDateA.getValue()));
        newVol.setDuree_vol(Integer.parseInt(addDuree.getText()));
        newVol.setTarif(Float.parseFloat(addTarif.getText()));
        newVol.setEscale(addEscale.getText());
        newVol.setImage(addImage.getText());
        newVol.setClasse(addClasse.getValue());


        Destination selectedDestination = addDestination.getValue();


        VolServices volServices = new VolServices();
        volServices.addVol(newVol, selectedDestination.getId());

        // Clear fields
        clearFields();
    }

    @FXML
    void handleClearVolButtonAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        addCompagnie.clear();
        addNumVol.clear();
        addADepart.clear();
        addAArrivee.clear();
        addDateD.setValue(null);
        addDateA.setValue(null);
        addDuree.clear();
        addTarif.clear();
        addEscale.clear();
        addImage.clear();
        addClasse.getSelectionModel().clearSelection();
        addDestination.getSelectionModel().clearSelection();
    }

    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        addDestination.getItems().addAll(destinationServices.getAllDestinations());
    }
}
