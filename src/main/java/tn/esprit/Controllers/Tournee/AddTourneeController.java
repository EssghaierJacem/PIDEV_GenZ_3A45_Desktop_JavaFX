package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Guide;
import tn.esprit.entites.Tournee;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.GuideServices;
import tn.esprit.services.TourneeServices;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTourneeController implements Initializable {

    @FXML
    private JFXButton Add;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addAge;

    @FXML
    private DatePicker addDate;

    @FXML
    private TextArea addDescription;

    @FXML
    private ComboBox<Destination> addDestination;

    @FXML
    private TextField addDuree;

    @FXML
    private ComboBox<Guide> addGuide;

    @FXML
    private TextField addMonuments;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addTarif;

    @FXML
    private TextField addTransport;

    @FXML
    void handleAddButtonAction(ActionEvent event) {

        Tournee newTournee = new Tournee();
        newTournee.setNom(addNom.getText());

        newTournee.setMonuments(addMonuments.getText());
        newTournee.setDuree(addDuree.getText());
        newTournee.setDate_debut(java.sql.Date.valueOf(addDate.getValue()));
        newTournee.setMoyen_transport(addTransport.getText());
        newTournee.setTarif(Float.parseFloat(addTarif.getText()));
        newTournee.setDescription(addDescription.getText());
        newTournee.setTranche_age(addAge.getText());

        Guide selectedGuide = addGuide.getValue();



        Destination selectedDestination = addDestination.getValue();


        TourneeServices tourneeServices = new TourneeServices();
        tourneeServices.addTournee(newTournee, selectedDestination.getId(),selectedGuide.getId());




    }

    @FXML
    void handleClearButtonAction(ActionEvent event) {
        clearFields();

    }
    private void clearFields() {
        addNom.clear();
        addMonuments.clear();
        addTarif.clear();
        addTransport.clear();
        addDate.setValue(null);
        addDuree.clear();
        addAge.clear();
        addDescription.clear();
        addGuide.getSelectionModel().clearSelection();
        addDestination.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDestinations();
        loadGuides();
    }

    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        addDestination.getItems().addAll(destinationServices.getAllDestinations());
    }

    private void loadGuides() {
        GuideServices guideServices = new GuideServices();
        addGuide.getItems().addAll(guideServices.getAllGuides());
    }

}
