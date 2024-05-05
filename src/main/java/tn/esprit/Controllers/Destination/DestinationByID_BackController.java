package tn.esprit.Controllers.Destination;

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
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.DestinationServices;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DestinationByID_BackController implements Initializable {

    @FXML
    private JFXButton Delete;
    @FXML
    private JFXButton PDF;

    @FXML
    private JFXButton destinationButton;
    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton volButton;

    @FXML
    private JFXButton Update;

    @FXML
    private ImageView destinationMultimedia;

    @FXML
    private Label destination_Id;

    @FXML
    private Label destination_abbrev;

    @FXML
    private Label destination_access;

    @FXML
    private Label destination_accomodations;

    @FXML
    private Label destination_attractions;

    @FXML
    private Label destination_cuisine;

    @FXML
    private Label destination_description;

    @FXML
    private Label destination_devise;

    @FXML
    private Label destination_pays;

    @FXML
    private Label destination_ville;

    private Destination currentDestination;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setDestinationData(Destination destination) {
        this.currentDestination = destination;
        destination_Id.setText(String.valueOf(destination.getId()));
        destination_pays.setText(destination.getPays());
        destination_ville.setText(destination.getVille());
        destination_description.setText(destination.getDescription());
        destination_attractions.setText(destination.getAttractions());
        destination_accomodations.setText(destination.getAccomodation());
        destination_devise.setText(destination.getDevise());
        destination_cuisine.setText(destination.getCuisine_locale());
        destination_access.setText(destination.getAccessibilite() ? "Yes" : "No");
        destination_abbrev.setText(destination.getAbbrev());

        if (destination.getMultimedia() != null && !destination.getMultimedia().isEmpty()) {
            destinationMultimedia.setImage(new Image(destination.getMultimedia()));
        }
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/UpdateDestination.fxml"));
        Parent root = loader.load();

        UpdateDestinationController updateDestinationController = loader.getController();
        updateDestinationController.populateFieldsWithData(currentDestination);

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
                DestinationServices destinationServices = new DestinationServices();
                destinationServices.removeDestination(currentDestination.getId());

                Stage stage = (Stage) Delete.getScene().getWindow();
                stage.close();
            }
        });
    }
    @FXML
    void Logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();

        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) Logout.getScene().getWindow();

            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);

            currentStage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToVol(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/ListVol_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) volButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List of Vols");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToDestination(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/ListDestination_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) destinationButton.getScene().getWindow();
            Scene newScene = new Scene(root);

            currentStage.setScene(newScene);

            currentStage.setTitle("List of Destinations");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void exportToPDF(ActionEvent event) {
        DestinationServices destinationServices = new DestinationServices();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("PDF Files", "*.pdf"),
                new ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(((JFXButton) event.getSource()).getScene().getWindow());

        if (file != null) {
            String filePath = file.getAbsolutePath();

            destinationServices.exportToPDF(currentDestination, filePath);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Export");
            alert.setHeaderText(null);
            alert.setContentText("The PDF has been exported successfully!");
            alert.showAndWait();
        }
    }

}
