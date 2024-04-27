package tn.esprit.Controllers.Event;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entites.Event;
import tn.esprit.services.EventServices;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class AddEventController implements Initializable{

    @FXML
    private JFXButton AddEvent;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addNom;

    @FXML
    private DatePicker addDateD;

    @FXML
    private DatePicker addDateF;

    @FXML
    private TextField addLieu;

    @FXML
    private TextArea addDescription;

    @FXML
    private TextField addOrganisateur;

    @FXML
    private TextField addImage;

    @FXML
    private TextField addPrix;


    @FXML
    void handleAddEventButtonAction(ActionEvent event) {
        Event newEvent = new Event();
        newEvent.setNom(addNom.getText());
        newEvent.setDate_debut(java.sql.Date.valueOf(addDateD.getValue()));
        newEvent.setDate_fin(java.sql.Date.valueOf(addDateF.getValue()));
        newEvent.setLieu(addLieu.getText());
        newEvent.setDescription(addDescription.getText());
        newEvent.setOrganisateur(addOrganisateur.getText());
        newEvent.setImage(addImage.getText());
        newEvent.setPrix(Float.parseFloat(addPrix.getText()));

        EventServices eventServices = new EventServices();
        eventServices.addEvent(newEvent);

        clearFields();
    }

    private void clearFields() {
        addNom.clear();
        addDateD.setValue(null);
        addDateF.setValue(null);
        addDescription.clear();
        addOrganisateur.clear();
        addImage.clear();
        addPrix.clear();
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNom.setPromptText("Enter event's name");
        addDescription.setPromptText("Enter event's description");
        addOrganisateur.setPromptText("Enter event's organisator");
        addLieu.setPromptText("Enter event's location");

        // Example of setting a default value for the date picker
        addDateD.setValue(LocalDate.now());
        addDateF.setValue(LocalDate.now());
    }
}


