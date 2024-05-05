package tn.esprit.Controllers.Guide;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.Guide;
import tn.esprit.services.GuideServices;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GuideFrontController implements Initializable {

    @FXML
    private TableColumn<Guide, String> Guide_Experience;

    @FXML
    private TableColumn<Guide, String> Guide_Langues;

    @FXML
    private TableColumn<Guide, String> Guide_Nationalite;

    @FXML
    private TableColumn<Guide, String> Guide_Nom;

    @FXML
    private TableColumn<Guide, String> Guide_Prenom;

    @FXML
    private TableColumn<Guide, Integer> Guide_Tarif;

    @FXML
    private TableColumn<Guide, Integer> Guide_Tel;

    @FXML
    private TableColumn<Guide, Integer> Guide_Id;






    @FXML
    private TableView<Guide> guideTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addGuideShowListData();

    }




    private void addGuideShowListData() {

        GuideServices guideServices = new GuideServices();
        List<Guide> guideList = guideServices.getAllGuides();

        Guide_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Guide_Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Guide_Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        Guide_Nationalite.setCellValueFactory(new PropertyValueFactory<>("Nationalite"));
        Guide_Langues.setCellValueFactory(new PropertyValueFactory<>("Langues_parlees"));
        Guide_Experience.setCellValueFactory(new PropertyValueFactory<>("Experiences"));
        Guide_Tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif_horaire"));
        Guide_Tel.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));

        guideTableView.getItems().addAll(guideList);
    }

    /*@FXML
    void goToTournee2(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/Tournee_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) nav.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Tournees");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
