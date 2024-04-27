package tn.esprit.Controllers.Participation;

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
import tn.esprit.Controllers.Participation.ParticipationByID_BackController;
import tn.esprit.services.ParticipationServices;
import tn.esprit.entites.Participation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class ParticipationController_Back implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetParticipation;

    @FXML
    private JFXButton Update;

    @FXML
    private TableColumn<Participation, Integer> participation_cell_id;

    @FXML
    private TableColumn<Participation, String> participation_cell_nom;

    @FXML
    private TableColumn<Participation, String> participation_cell_prenom;

    @FXML
    private TableColumn<Participation, Integer> participation_cell_tel;

    @FXML
    private TableColumn<Participation, String> participation_cell_email;


    @FXML
    private TableView<Participation> participationTableView;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        addParticipationShowListData();
    }

    private void addParticipationShowListData() {
        ParticipationServices participationServices = new ParticipationServices();
        List<Participation> participationList = participationServices.getAllParticipations();

        participation_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        participation_cell_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        participation_cell_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        participation_cell_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        participation_cell_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        participationTableView.getItems().addAll(participationList);
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Participation selectedParticipation = participationTableView.getSelectionModel().getSelectedItem();

        if (selectedParticipation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ParticipationServices participationServices= new ParticipationServices();
                    participationServices.removeParticipation(selectedParticipation.getId());

                    participationTableView.getItems().remove(selectedParticipation);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune participation sélectionnée");
            alert.setContentText("Veuillez choisir une participation à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Participation selectedParticipation = participationTableView.getSelectionModel().getSelectedItem();

        if (selectedParticipation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune participation sélectionnée");
            alert.setContentText("Veuillez choisir une participation à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Participation/UpdateParticipation.fxml"));
        Parent root = loader.load();

        UpdateParticipationController updateParticipationController = loader.getController();
        updateParticipationController.populateFieldsWithData(selectedParticipation);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleAjouterParticipationButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Participation/AddParticipation.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Participation selectedParticipation = participationTableView.getSelectionModel().getSelectedItem();

        if (selectedParticipation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune participation sélectionné");
            alert.setContentText("Veuillez choisir une participation à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Participation/ParticipationByID_Back.fxml"));
        Parent root = loader.load();

        ParticipationByID_BackController participationByIDBackController = loader.getController();

        participationByIDBackController.setParticipationData(selectedParticipation);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}
