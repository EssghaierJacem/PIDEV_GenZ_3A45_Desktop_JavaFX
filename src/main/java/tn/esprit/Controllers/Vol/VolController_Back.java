package tn.esprit.Controllers.Vol;

import javafx.beans.property.SimpleStringProperty;
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
import tn.esprit.services.VolServices;
import tn.esprit.entites.Vol;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class VolController_Back implements Initializable {

    @FXML
    private TableView<Vol> volTableView;

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

    @FXML
    private TableColumn<Vol, Integer> vol_cell_id;

    @FXML
    private TableColumn<Vol, String> vol_cell_destination;

    @FXML
    private TableColumn<Vol, String> vol_cell_compagnie;

    @FXML
    private TableColumn<Vol, String> vol_cell_dateD;

    @FXML
    private TableColumn<Vol, String> vol_cell_dateA;

    @FXML
    private TableColumn<Vol, String> vol_cell_AD;

    @FXML
    private TableColumn<Vol, String> vol_cell_AA;

    @FXML
    private TableColumn<Vol, Integer> vol_cell_duree;

    @FXML
    private TableColumn<Vol, Float> vol_cell_tarif;

    @FXML
    private TableColumn<Vol, String> vol_cell_classe;

    @FXML
    private TableColumn<Vol, String> vol_cell_escale;



    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        addVolShowListData();
    }

    private void addVolShowListData() {
        VolServices volServices = new VolServices();
        List<Vol> volList = volServices.getAllVols();

        vol_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        vol_cell_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });
        vol_cell_compagnie.setCellValueFactory(new PropertyValueFactory<>("compagnie_a"));
        vol_cell_dateD.setCellValueFactory(new PropertyValueFactory<>("date_depart"));
        vol_cell_dateA.setCellValueFactory(new PropertyValueFactory<>("date_arrivee"));
        vol_cell_AD.setCellValueFactory(new PropertyValueFactory<>("aeroport_depart"));
        vol_cell_AA.setCellValueFactory(new PropertyValueFactory<>("aeroport_arrivee"));
        vol_cell_duree.setCellValueFactory(new PropertyValueFactory<>("duree_vol"));
        vol_cell_tarif.setCellValueFactory(new PropertyValueFactory<>("tarif"));
        vol_cell_classe.setCellValueFactory(new PropertyValueFactory<>("classe"));
        vol_cell_escale.setCellValueFactory(new PropertyValueFactory<>("escale"));

        volTableView.getItems().addAll(volList);
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    VolServices volServices = new VolServices();
                    volServices.removeVol(selectedVol.getId());

                    volTableView.getItems().remove(selectedVol);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun vol sélectionné");
            alert.setContentText("Veuillez choisir un vol à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun vol sélectionné");
            alert.setContentText("Veuillez choisir un vol à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/UpdateVol.fxml"));
        Parent root = loader.load();

        UpdateVolController updateVolController = loader.getController();
        updateVolController.populateFieldsWithData(selectedVol);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAjouterVolButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vol/AddVol.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun vol sélectionné");
            alert.setContentText("Veuillez choisir un vol à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/VolByID_Back.fxml"));
        Parent root = loader.load();

        VolByID_BackController volByIDBackController = loader.getController();

        volByIDBackController.setVolData(selectedVol);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
