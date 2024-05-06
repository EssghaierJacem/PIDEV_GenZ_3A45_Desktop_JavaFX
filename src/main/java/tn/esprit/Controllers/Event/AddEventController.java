package tn.esprit.Controllers.Event;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entites.Event;
import tn.esprit.services.EventServices;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
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
    private Label errorLabel;



    @FXML
    void handleAddEventButtonAction(ActionEvent event) {
        if (validateInputs()) {
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

        clearFields();}
        else {
            errorLabel.setText("Veuillez remplir tous les champs.");
        }

    }

    private void clearFields() {
        addNom.clear();
        addDateD.setValue(null);
        addDateF.setValue(null);
        addDescription.clear();
        addOrganisateur.clear();
        addImage.clear();
        addPrix.clear();
        addLieu.clear();
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }
    private boolean validateInputs() {
        String nom = addNom.getText();
        String lieu = addLieu.getText();
        String description = addDescription.getText();
        LocalDate date_debut = addDateD.getValue();
        LocalDate date_fin = addDateF.getValue();
        String prix = addPrix.getText();

        StringBuilder errorMessage = new StringBuilder();

        if (nom.isEmpty() || lieu.isEmpty() || description.isEmpty() || date_debut == null || date_fin == null || prix.isEmpty() || prix.equals("A remplir...")) {
            errorMessage.append("Veuillez remplir tous les champs.\n");
        }

        if (nom.length() < 4 || nom.length() > 20) {
            errorMessage.append("Le nom de l'événement doit avoir entre 4 et 20 caractères.\n");
        }

        if (lieu.length() < 4 || lieu.length() > 20) {
            errorMessage.append("Le lieu de l'événement doit avoir entre 4 et 20 caractères.\n");
        }

        if (description.length() < 4 || description.length() > 50) {
            errorMessage.append("La description de l'événement doit avoir entre 4 et 50 caractères.\n");
        }

        if (date_debut.isAfter(LocalDate.now())) {
            errorMessage.append("Veuillez sélectionner une date d'événement valide (passée ou présente).\n");
        }

        if (date_fin.isAfter(LocalDate.now())) {
            errorMessage.append("Veuillez sélectionner une date d'événement valide (passée ou présente).\n");
        }

        try {
            float prixValue = Float.parseFloat(prix);
            if (prixValue <= 0) {
                errorMessage.append("Le prix de l'événement doit être supérieur à zéro.\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Le prix de l'événement doit être un nombre valide.\n");
        }

        if (errorMessage.length() > 0) {
            errorLabel.setText(errorMessage.toString());
            return false;
        }

        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNom.setPromptText("Enter event's name");
        addDescription.setPromptText("Enter event's description");
        addOrganisateur.setPromptText("Enter event's organisator");
        addLieu.setPromptText("Enter event's location");

        addDateD.setValue(LocalDate.now());
        addDateF.setValue(LocalDate.now());

    }
}


