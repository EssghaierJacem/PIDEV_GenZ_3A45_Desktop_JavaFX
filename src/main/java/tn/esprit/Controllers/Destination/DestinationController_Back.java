package tn.esprit.Controllers.Destination;

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
import tn.esprit.services.DestinationServices;
import tn.esprit.entites.Destination;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class DestinationController_Back implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetDestination;

    @FXML
    private JFXButton Update;

    @FXML
    private TableColumn<Destination, Integer> destination_cell_id;

    @FXML
    private TableColumn<Destination, String> destination_cell_pays;

    @FXML
    private TableColumn<Destination, String> destination_cell_ville;

    @FXML
    private TableColumn<Destination, String> destination_cell_devise;

    @FXML
    private TableColumn<Destination, String> destination_cell_cuisinelocale;

    @FXML
    private TableColumn<Destination, String> destination_cell_abbrev;

    @FXML
    private TableColumn<Destination, String> destination_cell_attractions;

    @FXML
    private TableColumn<Destination, String> destination_cell_accomodation;

    @FXML
    private TableColumn<Destination, Boolean> destination_cell_access;

    @FXML
    private TableColumn<Destination, String> destination_cell_description;

    @FXML
    private TableView<Destination> destinationTableView;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        addDestinationShowListData();
    }

    private void addDestinationShowListData() {
        DestinationServices destinationServices = new DestinationServices();
        List<Destination> destinationList = destinationServices.getAllDestinations();

        destination_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        destination_cell_pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        destination_cell_ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        destination_cell_devise.setCellValueFactory(new PropertyValueFactory<>("devise"));
        destination_cell_cuisinelocale.setCellValueFactory(new PropertyValueFactory<>("cuisine_locale"));
        destination_cell_abbrev.setCellValueFactory(new PropertyValueFactory<>("abbrev"));
        destination_cell_attractions.setCellValueFactory(new PropertyValueFactory<>("attractions"));
        destination_cell_accomodation.setCellValueFactory(new PropertyValueFactory<>("accomodation"));
        destination_cell_access.setCellValueFactory(new PropertyValueFactory<>("accessibilite"));
        destination_cell_description.setCellValueFactory(new PropertyValueFactory<>("description"));

        destinationTableView.getItems().addAll(destinationList);
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Destination selectedDestination = destinationTableView.getSelectionModel().getSelectedItem();

        if (selectedDestination != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DestinationServices destinationServices = new DestinationServices();
                    destinationServices.removeDestination(selectedDestination.getId());

                    destinationTableView.getItems().remove(selectedDestination);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune destination sélectionnée");
            alert.setContentText("Veuillez choisir une destination à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Destination selectedDestination = destinationTableView.getSelectionModel().getSelectedItem();

        if (selectedDestination == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune destination sélectionnée");
            alert.setContentText("Veuillez choisir une destination à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/UpdateDestination.fxml"));
        Parent root = loader.load();

        UpdateDestinationController updateDestinationController = loader.getController();
        updateDestinationController.populateFieldsWithData(selectedDestination);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleAjouterDestinationButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Destination/AddDestination.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
