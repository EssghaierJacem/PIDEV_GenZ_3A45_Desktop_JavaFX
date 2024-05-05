package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;



import tn.esprit.entites.Tournee;

import tn.esprit.services.GuideServices;
import tn.esprit.services.TourneeServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Tournee_BackController implements Initializable {

    @FXML
    private JFXButton Ajouter;

    @FXML
    private JFXButton Delete;

    @FXML
    private TextField Search;

    @FXML
    private TableColumn<Tournee, String> T_age;

    @FXML
    private TableColumn<Tournee, String> T_date;

    @FXML
    private TableColumn<Tournee, String> T_description;

    @FXML
    private TableColumn<Tournee, String> T_destination;

    @FXML
    private TableColumn<Tournee, String> T_duree;

    @FXML
    private TableColumn<Tournee, String> T_guide;

    @FXML
    private TableColumn<Tournee, Integer> T_id;

    @FXML
    private TableColumn<Tournee, String> T_monument;

    @FXML
    private TableColumn<Tournee, String> T_nom;

    @FXML
    private TableColumn<Tournee, Float> T_tarif;

    @FXML
    private TableColumn<Tournee, String> T_transport;

    @FXML
    private TableView<Tournee> TourneeTableView;

    @FXML
    private JFXButton Update;

    @FXML
    private JFXButton VoirPlus;
    @FXML
    private JFXButton guideButton;


     private void addTourneeShow() {
        TourneeServices tourneeServices = new TourneeServices();
        List<Tournee> tourneeList = tourneeServices.getAllTournees();

        T_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        T_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });
        T_guide.setCellValueFactory(cellData -> {
            String guideName = cellData.getValue().getGuide().getNom();
            return new SimpleStringProperty(guideName);
        });
        T_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        T_date.setCellValueFactory(new PropertyValueFactory<>("Date_debut"));
        T_duree.setCellValueFactory(new PropertyValueFactory<>("Duree"));
        T_description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        T_tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif"));
        T_monument.setCellValueFactory(new PropertyValueFactory<>("Monuments"));
        T_age.setCellValueFactory(new PropertyValueFactory<>("Tranche_age"));
        T_transport.setCellValueFactory(new PropertyValueFactory<>("Moyen_transport"));


        TourneeTableView.getItems().addAll(tourneeList);
    }


    @FXML
    void goToGuide(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/Guide_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) guideButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Tournees");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleAjouterButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/AddTournee.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) Ajouter.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Tournees");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
       Tournee selectedTournee = TourneeTableView.getSelectionModel().getSelectedItem();

        if (selectedTournee != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    GuideServices guideServices = new GuideServices();
                    guideServices.removeGuide(selectedTournee.getId());

                    TourneeTableView.getItems().remove(selectedTournee);
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
    void handleUpdateButtonAction(ActionEvent event) throws IOException{
        Tournee selectedTournee = TourneeTableView.getSelectionModel().getSelectedItem();

        if (selectedTournee == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Tournee sélectionné");
            alert.setContentText("Veuillez choisir un Tournee à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/TourneeUpdate.fxml"));
        Parent root = loader.load();

        UpdateTourneeController updateTourneeController = loader.getController();
        updateTourneeController.populateFieldsWithData(selectedTournee);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Tournee selectedTournee = TourneeTableView.getSelectionModel().getSelectedItem();

        if (selectedTournee == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Guide sélectionné");
            alert.setContentText("Veuillez choisir un Guide à afficher.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/TourneeShow.fxml"));
        Parent root = loader.load();

        ShowTourneeController showTourneeController = loader.getController();
        showTourneeController.setTourneeData(selectedTournee);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTourneeShow();

    }
}
