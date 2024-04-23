package tn.esprit.Controllers.Guide;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.services.GuideServices;
import tn.esprit.entites.Guide;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Guide_BackController implements Initializable {

    @FXML
    private JFXButton AjouterGuide;

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetGuide;

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
    private TableColumn<Guide, Integer> Guide_id;

    @FXML
    private JFXButton Update;

    @FXML
    private TableView<Guide> guideTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addGuideShowListData();

    }




    private void addGuideShowListData() {

        GuideServices guideServices = new GuideServices();
        List<Guide> guideList = guideServices.getAllGuides();

        Guide_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Guide_Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Guide_Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        Guide_Nationalite.setCellValueFactory(new PropertyValueFactory<>("Nationalite"));
        Guide_Langues.setCellValueFactory(new PropertyValueFactory<>("Langues_parlees"));
        Guide_Experience.setCellValueFactory(new PropertyValueFactory<>("Experiences"));
        Guide_Tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif_horaire"));
        Guide_Tel.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));

        guideTableView.getItems().addAll(guideList);
    }

    @FXML
    void handleAjouterGuideButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Guide/AddGuide.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();





    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    GuideServices guideServices = new GuideServices();
                    guideServices.removeGuide(selectedGuide.getId());

                    guideTableView.getItems().remove(selectedGuide);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun guide sélectionné");
            alert.setContentText("Veuillez choisir un uide à supprimer.");
            alert.showAndWait();
        }

    }

    @FXML
    void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Guide sélectionné");
            alert.setContentText("Veuillez choisir un Guide à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/GuideUpdate.fxml"));
        Parent root = loader.load();

        UpdateGuideController updateGuideController = loader.getController();
        updateGuideController.populateFieldsWithData(selectedGuide);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void handleVoirPlusButtonAction(ActionEvent event) throws IOException{
        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Guide sélectionné");
            alert.setContentText("Veuillez choisir un Guide à afficher.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/GuideShow.fxml"));
        Parent root = loader.load();

        ShowGuideController showGuideController = loader.getController();
        showGuideController.setGuideData(selectedGuide);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }




}
