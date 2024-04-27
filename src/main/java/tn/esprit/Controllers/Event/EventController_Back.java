package tn.esprit.Controllers.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.jfoenix.controls.JFXButton;

import javafx.stage.Stage;
import tn.esprit.entites.Vol;
import tn.esprit.services.EventServices;
import tn.esprit.entites.Event;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;


public class EventController_Back implements Initializable {
    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetEvent;

    @FXML
    private JFXButton Update;

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
    private TableColumn<Event, Float> event_cell_prix;

    
    
    @FXML
    private TableView<Event> eventTableView;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        addEventShowListData();
    }

    private void addEventShowListData() {
        EventServices eventServices = new EventServices();
        List<Event> eventList = eventServices.getAllEvents();

        event_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        event_cell_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        event_cell_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        event_cell_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        event_cell_organisateur.setCellValueFactory(new PropertyValueFactory<>("organisateur"));
        event_cell_dateD.setCellValueFactory(new PropertyValueFactory<>("date_depart"));
        event_cell_dateF.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        event_cell_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        eventTableView.getItems().addAll(eventList);
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    EventServices eventServices = new EventServices();
                    eventServices.removeEvent(selectedEvent.getId());

                    eventTableView.getItems().remove(selectedEvent);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun evenement sélectionnée");
            alert.setContentText("Veuillez choisir un evenement à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun evenement sélectionnée");
            alert.setContentText("Veuillez choisir un evenement à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/UpdateEvent.fxml"));
        Parent root = loader.load();

        Object updateEventController = loader.getController();
        updateEventController.equals(selectedEvent);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleAjouterEventButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event/AddEvent.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun evenement sélectionné");
            alert.setContentText("Veuillez choisir un evenement à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/EventByID_Back.fxml"));
        Parent root = loader.load();

        EventByID_BackController eventByIDBackController = loader.getController();

        eventByIDBackController.setEventData(selectedEvent);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}

