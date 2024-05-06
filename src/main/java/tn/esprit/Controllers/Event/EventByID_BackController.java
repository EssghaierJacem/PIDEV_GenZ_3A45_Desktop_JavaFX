package tn.esprit.Controllers.Event;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entites.Event;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.EventServices;

import java.io.File;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EventByID_BackController implements Initializable {
    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

    @FXML
    private ImageView eventImage;

    @FXML
    private Label event_Id;

    @FXML
    private Label event_nom;
    @FXML
    private Label event_dateD;

    @FXML
    private Label event_dateF;

    @FXML
    private Label event_lieu;

    @FXML
    private Label event_description;

    @FXML
    private Label event_organisateur;

    @FXML
    private Label event_prix;
    
    private Event currentEvent;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setEventData(Event event) {
        this.currentEvent = event;
        event_Id.setText(String.valueOf(event.getId()));
        event_nom.setText(event.getNom());
        event_lieu.setText(event.getLieu());
        event_description.setText(event.getDescription());
        event_organisateur.setText(event.getOrganisateur());
        event_dateD.setText(dateFormat.format(event.getDate_debut()));
        event_dateF.setText(dateFormat.format(event.getDate_fin()));
        event_prix.setText(String.valueOf(event.getPrix()));


        if (event.getImage() != null && !event.getImage().isEmpty()) {
            eventImage.setImage(new Image(event.getImage()));
        }
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/UpdateDestination.fxml"));
        Parent root = loader.load();

        Object updateEventController = loader.getController();
        updateEventController.equals(currentEvent);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr que vous voulez supprimer?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                EventServices destinationServices = new EventServices();
                destinationServices.removeEvent(currentEvent.getId());

                Stage stage = (Stage) Delete.getScene().getWindow();
                stage.close();
            }
        });
    }
    @FXML
    void exportToPDF(ActionEvent event) {
        EventServices eventServices = new EventServices();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(((JFXButton) event.getSource()).getScene().getWindow());

        if (file != null) {
            String filePath = file.getAbsolutePath();

            eventServices.exportToPDF(currentEvent, filePath);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Export");
            alert.setHeaderText(null);
            alert.setContentText("The PDF has been exported successfully!");
            alert.showAndWait();
        }
    }

}

