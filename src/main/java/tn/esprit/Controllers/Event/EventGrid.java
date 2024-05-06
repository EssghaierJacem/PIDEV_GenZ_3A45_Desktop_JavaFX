package tn.esprit.Controllers.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.esprit.entites.Event;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;


public class EventGrid implements Initializable {
    @FXML
    private ImageView eventImage;

    @FXML
    private Label GridNom;

    @FXML
    private Label GridLieu;

    @FXML
    private Label GridDescription;

    @FXML
    private Label GridOrganisateur;

    @FXML
    private Label GridDateDepart;

    @FXML
    private Label GridDateFin;

    @FXML
    private Button rollBackButton;

    @FXML
    private Label GridPrix;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Event event) {
        GridNom.setText(event.getNom());
        GridLieu.setText(event.getLieu());
        GridDescription.setText(event.getDescription());
        GridOrganisateur.setText(event.getOrganisateur());
        GridDateDepart.setText(dateFormat.format(event.getDate_debut()));
        GridDateFin.setText(dateFormat.format(event.getDate_fin()));
        GridPrix.setText(String.valueOf(event.getPrix()));
        if (event.getImage() != null && !event.getImage().isEmpty()) {
            try {
                Image image = new Image(event.getImage());
                eventImage.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL or resource not found: " + event.getImage());
            }
        }
    }
    public void handleRollBackButtonAction(ActionEvent actionEvent) {
        // Get the button that triggered the event
        Button rollBackButton = (Button) actionEvent.getSource();

        // Get the stage from the button's scene
        Stage stage = (Stage) rollBackButton.getScene().getWindow();

        try {
            // Load the dashboard FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/Grid.fxml"));
            Parent root = loader.load();

            // Create a new scene with the dashboard
            Scene dashboardScene = new Scene(root);

            // Set the scene in the stage and show it
            stage.setScene(dashboardScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
