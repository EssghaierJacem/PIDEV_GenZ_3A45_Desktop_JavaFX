package tn.esprit.Controllers.Event;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Event;
import tn.esprit.services.EventServices;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
public class UpdateEventController implements Initializable {
    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateEvent;

    @FXML
    private TableView<Event> eventTableView;

    @FXML
    private TableColumn<Event, Integer> event_cell_id;

    @FXML
    private TableColumn<Event, String> event_cell_nom;

    @FXML
    private TableColumn<Event, String> event_cell_lieu;

    @FXML
    private TableColumn<Event, String> event_cell_description;

    @FXML
    private TableColumn<Event, String> event_cell_organisateur;

    @FXML
    private TableColumn<Event, String> event_cell_dateD;

    @FXML
    private TableColumn<Event, String> event_cell_dateF;

    @FXML
    private TextField updateNom;

    @FXML
    private ComboBox<String> updateLieu;

    @FXML
    private TextField updateDescription;

    @FXML
    private TextField updateOrganisateur;

    @FXML
    private DatePicker updateDateD;

    @FXML
    private DatePicker updateDateF;

    @FXML
    private TextField updateImage;

    @FXML
    private TextField updatePrix;


    private EventServices eventServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventServices = new EventServices();
        fillEventTableView();

        eventTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillEventTableView() {
        List<Event> eventList = eventServices.getAllEvents();

        event_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        event_cell_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        event_cell_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        event_cell_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        event_cell_organisateur.setCellValueFactory(new PropertyValueFactory<>("organisateur"));

        eventTableView.getItems().addAll(eventList);
    }

    void populateFieldsWithData(Event event) {
        updateNom.setText(event.getNom());
        updateDescription.setText(event.getDescription());
        updateOrganisateur.setText(event.getOrganisateur());
        updatePrix.setText(String.valueOf(event.getPrix()));
        updateImage.setText(event.getImage());


    }
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updateNom.clear();
        updateDescription.clear();
        updateOrganisateur.clear();
        updateImage.clear();
        updateDescription.clear();
        updatePrix.clear();
//        updateDateD.setValue(null);
//        updateDateF.setValue(null);

    }

    @FXML
    private void handleUpdateEventButtonAction(ActionEvent event) {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour l evenement?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedEvent.setNom(updateNom.getText());
//                selectedEvent.setLieu(updateLieu.getItems().toString());
                selectedEvent.setDescription(updateDescription.getText());
                selectedEvent.setOrganisateur(updateOrganisateur.getText());
//                java.sql.Date dateDebut = java.sql.Date.valueOf(updateDateD.getValue());
//                selectedEvent.setDate_debut(dateDebut);
//                java.sql.Date dateFin = java.sql.Date.valueOf(updateDateF.getValue());
//                selectedEvent.setDate_fin(dateFin);
                selectedEvent.setImage(updateImage.getText());
                selectedEvent.setPrix((float) Double.parseDouble(updatePrix.getText()));


                eventServices.updateEvent(selectedEvent);

                eventTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("evenement mise à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucun evenement sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner un evenement à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

}
