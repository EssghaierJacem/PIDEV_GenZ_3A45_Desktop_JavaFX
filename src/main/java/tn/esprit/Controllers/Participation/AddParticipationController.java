package tn.esprit.Controllers.Participation;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.entites.Event;
import tn.esprit.entites.Participation;
import tn.esprit.services.EventServices;
import tn.esprit.services.ParticipationServices;

import java.net.URL;
import java.util.ResourceBundle;

public class AddParticipationController implements Initializable{

    @FXML
    private JFXButton AddParticipation;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addEmail;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addPrenom;

    @FXML
    private TextField addTel;

    @FXML
    private ComboBox<Event> events;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEvents();
    }


    @FXML
    void handleAddParticipationButtonAction(ActionEvent event) {
        Participation newParticipation = new Participation();
        newParticipation.setNom(addNom.getText());
        newParticipation.setPrenom(addPrenom.getText());
        newParticipation.setTel(Integer.parseInt(addTel.getText()));
        newParticipation.setEmail(addEmail.getText());

        Event selectedEvent = events.getValue();

        if (selectedEvent != null) {
            ParticipationServices participationServices = new ParticipationServices();
            participationServices.addParticipation(newParticipation, selectedEvent.getId());

            clearFields();
        } else {
            System.out.println("Please select an event");
        }
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

    private void loadEvents() {
        EventServices eventServices = new EventServices();
        events.setItems(FXCollections.observableArrayList(eventServices.getAllEvents()));
    }
}
