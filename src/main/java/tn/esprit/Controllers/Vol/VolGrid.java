package tn.esprit.Controllers.Vol;

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
import javafx.stage.Stage;
import tn.esprit.entites.Vol;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class VolGrid implements Initializable {

    @FXML
    private Label GridCompagnie;

    @FXML
    private Label GridDateDepart;

    @FXML
    private Label GridDestination;

    @FXML
    private Label GridTarif;

    @FXML
    private ImageView VolImage;
    @FXML
    private JFXButton voirPlusButton;
    private Vol vol;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        voirPlusButton.setOnAction(this::onVoirPlusClick);
    }
    public void setData(Vol vol) {
        this.vol = vol;
        GridCompagnie.setText(vol.getCompagnie_a());
        GridDateDepart.setText(dateFormat.format(vol.getDate_depart()));
        GridDestination.setText(vol.getDestination().toString());
        GridTarif.setText(String.valueOf(vol.getTarif()));
        if (vol.getImage() != null && !vol.getImage().isEmpty()) {
            try {
                Image image = new Image(vol.getImage());
                VolImage.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL or resource not found: " + vol.getImage());
            }
        }
    }
    @FXML
    private void onVoirPlusClick(ActionEvent event) {
        if (vol == null) {
            System.out.println("Vol object is null in onVoirPlusClick method.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/VolByID_Front.fxml"));
            Parent root = loader.load();

            VolByID_FrontController controller = loader.getController();
            controller.setVolData(vol);

            Stage currentStage = (Stage) voirPlusButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.setTitle("Vol Details");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
