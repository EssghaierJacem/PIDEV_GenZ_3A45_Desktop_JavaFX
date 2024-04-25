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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entites.Vol;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private ImageView ImageViewSample;

    @FXML
    private HBox SampleBox;

    @FXML
    private Label labelCompagnie;

    @FXML
    private Label labelDD;

    @FXML
    private Label labelDestination;

    @FXML
    private Label labeltarif;
    @FXML
    private JFXButton voirPlusButton;
    private Vol vol;

    private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    int randomIndex = (int) (Math.random() * colors.length);
    String randomColor = colors[randomIndex];
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        voirPlusButton.setOnAction(this::onVoirPlusClick);
    }
    public void setData(Vol vol) {
        this.vol = vol;
        labelCompagnie.setText(vol.getCompagnie_a());
        labelDD.setText(dateFormat.format(vol.getDate_depart()));
        labelDestination.setText(vol.getDestination().toString());
        labeltarif.setText(String.valueOf(vol.getTarif()));
        if (vol.getImage() != null && !vol.getImage().isEmpty()) {
            try {
                Image image = new Image(vol.getImage());
                ImageViewSample.setImage(image);
            } catch (Exception e) {
                System.out.println("Invalid image URL or resource not found: " + vol.getImage());
            }
        }
        String style = String.format("-fx-background-color: #%s; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0,10);", randomColor);
        SampleBox.setStyle(style);

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
