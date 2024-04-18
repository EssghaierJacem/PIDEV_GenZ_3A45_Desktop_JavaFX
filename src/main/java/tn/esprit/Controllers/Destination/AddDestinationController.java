package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDestinationController implements Initializable {

    @FXML
    private JFXButton AddDestination;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addAbbrev;

    @FXML
    private ComboBox<String> addAccess;

    @FXML
    private TextField addAccomodation;

    @FXML
    private TextField addAttractions;

    @FXML
    private TextField addCuisine;

    @FXML
    private TextArea addDescription;

    @FXML
    private TextField addDevise;

    @FXML
    private TextField addMultimedia;

    @FXML
    private TextField addPays;

    @FXML
    private TextField addVille;

    @FXML
    void handleAddDestinationButtonAction(ActionEvent event) {
        Destination newDestination = new Destination();
        newDestination.setPays(addPays.getText());
        newDestination.setVille(addVille.getText());
        newDestination.setDescription(addDescription.getText());
        newDestination.setAttractions(addAttractions.getText());
        newDestination.setAccomodation(addAccomodation.getText());
        newDestination.setDevise(addDevise.getText());
        newDestination.setMultimedia(addMultimedia.getText());
        newDestination.setCuisine_locale(addCuisine.getText());
        newDestination.setAccessibilite(addAccess.getValue().equals("Accessible"));
        newDestination.setAbbrev(addAbbrev.getText());

        DestinationServices destinationServices = new DestinationServices();
        destinationServices.addDestination(newDestination);

        clearFields();
    }

    private void clearFields() {
        addPays.clear();
        addVille.clear();
        addDescription.clear();
        addAttractions.clear();
        addAccomodation.clear();
        addDevise.clear();
        addMultimedia.clear();
        addCuisine.clear();
        addAccess.getSelectionModel().clearSelection();
        addAbbrev.clear();
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAccess.setItems(FXCollections.observableArrayList("Accessible","Inaccessible"));
    }
}
