package tn.esprit.Controllers.Destination;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateDestinationController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateDestination;

    @FXML
    private TableView<Destination> destinationTableView;

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
    private TextField updateAbbrev;

    @FXML
    private ComboBox<String> updateAccess;

    @FXML
    private TextField updateAccomodation;

    @FXML
    private TextField updateAttractions;

    @FXML
    private TextField updateCuisine;

    @FXML
    private TextArea updateDescription;

    @FXML
    private TextField updateDevise;

    @FXML
    private TextField updateMultimedia;

    @FXML
    private TextField updatePays;

    @FXML
    private TextField updateVille;

    private DestinationServices destinationServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        destinationServices = new DestinationServices();
        fillDestinationTableView();

        destinationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillDestinationTableView() {
        List<Destination> destinationList = destinationServices.getAllDestinations();

        destination_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        destination_cell_pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        destination_cell_ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        destination_cell_devise.setCellValueFactory(new PropertyValueFactory<>("devise"));
        destination_cell_cuisinelocale.setCellValueFactory(new PropertyValueFactory<>("cuisine_locale"));

        destinationTableView.getItems().addAll(destinationList);
    }

    void populateFieldsWithData(Destination destination) {
        updatePays.setText(destination.getPays());
        updateVille.setText(destination.getVille());
        updateDevise.setText(destination.getDevise());
        updateCuisine.setText(destination.getCuisine_locale());
        updateAbbrev.setText(destination.getAbbrev());
        updateAccomodation.setText(destination.getAccomodation());
        updateAttractions.setText(destination.getAttractions());
        updateMultimedia.setText(destination.getMultimedia());
        updateDescription.setText(destination.getDescription());

        updateAccess.getItems().clear();
        updateAccess.getItems().addAll("Yes", "No");
        updateAccess.getSelectionModel().select(destination.getAccessibilite() ? "Yes" : "No");
    }
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updatePays.clear();
        updateVille.clear();
        updateDevise.clear();
        updateCuisine.clear();
        updateAbbrev.clear();
        updateAccomodation.clear();
        updateAttractions.clear();
        updateMultimedia.clear();
        updateDescription.clear();
        updateAccess.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleUpdateDestinationButtonAction(ActionEvent event) {
        Destination selectedDestination = destinationTableView.getSelectionModel().getSelectedItem();

        if (selectedDestination != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour la destination?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedDestination.setPays(updatePays.getText());
                selectedDestination.setVille(updateVille.getText());
                selectedDestination.setDevise(updateDevise.getText());
                selectedDestination.setCuisine_locale(updateCuisine.getText());
                selectedDestination.setAbbrev(updateAbbrev.getText());
                selectedDestination.setAccomodation(updateAccomodation.getText());
                selectedDestination.setAttractions(updateAttractions.getText());
                selectedDestination.setMultimedia(updateMultimedia.getText());
                selectedDestination.setDescription(updateDescription.getText());
                selectedDestination.setAccessibilite(updateAccess.getSelectionModel().getSelectedItem().equals("Yes"));

                destinationServices.updateDestination(selectedDestination);

                destinationTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Destination mise à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucune destination sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner une destination à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

}
