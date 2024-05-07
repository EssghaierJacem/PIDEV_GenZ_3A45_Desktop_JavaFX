package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DestinationByID_FrontController implements Initializable {
    @FXML
    private JFXButton Logout;

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

    @FXML
    private HBox cardLayout;
    @FXML
    private JFXButton dashboardButton;
    public void setDestinationData(Destination destination) {
        if (destination == null) {
            System.out.println("Received a null Destination object in setVolData method.");
            return;
        }

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/FrontDashboard.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) dashboardButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("User - Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
