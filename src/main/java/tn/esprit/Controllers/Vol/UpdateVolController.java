package tn.esprit.Controllers.Vol;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Classe;
import tn.esprit.entites.Vol;
import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.VolServices;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateVolController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateVol;

    @FXML
    private TextField updateAA;

    @FXML
    private TextField updateAD;

    @FXML
    private ComboBox<String> updateClasse;

    @FXML
    private TextField updateCompagnie;

    @FXML
    private DatePicker updateDateA;

    @FXML
    private DatePicker updateDateD;

    @FXML
    private ComboBox<String> updateDestination;

    @FXML
    private TextField updateDureeVol;

    @FXML
    private TextField updateEscale;

    @FXML
    private TextField updateImage;

    @FXML
    private TextField updateNumVol;

    @FXML
    private TextField updateTarif;

    @FXML
    private TableView<Vol> volTableView;

    @FXML
    private TableColumn<Vol, Integer> vol_cell_id;

    @FXML
    private TableColumn<Vol, String> vol_cell_destination;

    @FXML
    private TableColumn<Vol, String> vol_cell_compagnie;

    @FXML
    private TableColumn<Vol, String> vol_cell_classe;

    @FXML
    private TableColumn<Vol, String> vol_cell_dateD;

    private VolServices volServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        volServices = new VolServices();
        fillVolTableView();
        loadDestinations();
        loadClasses();

        volTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }
    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        List<Destination> destinationList = destinationServices.getAllDestinations();
        for (Destination destination : destinationList) {
            updateDestination.getItems().add(destination.toString());
        }
    }

    private void loadClasses() {
        updateClasse.getItems().addAll("ECONOMIC", "BUSINESS", "FIRST");
    }

    private void fillVolTableView() {
        List<Vol> volList = volServices.getAllVols();

        vol_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        vol_cell_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });
        vol_cell_compagnie.setCellValueFactory(new PropertyValueFactory<>("compagnie_a"));
        vol_cell_classe.setCellValueFactory(new PropertyValueFactory<>("classe"));
        vol_cell_dateD.setCellValueFactory(new PropertyValueFactory<>("date_depart"));

        volTableView.getItems().addAll(volList);
    }

    void populateFieldsWithData(Vol vol) {
        updateCompagnie.setText(vol.getCompagnie_a());
        updateNumVol.setText(String.valueOf(vol.getNum_vol()));
        updateAD.setText(vol.getAeroport_depart());
        updateAA.setText(vol.getAeroport_arrivee());
        updateDureeVol.setText(String.valueOf(vol.getDuree_vol()));
        updateTarif.setText(String.valueOf(vol.getTarif()));
        updateEscale.setText(vol.getEscale());
        updateImage.setText(vol.getImage());
        updateClasse.setValue(vol.getClasse().toString());
        updateDestination.setValue(vol.getDestination().toString());
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updateCompagnie.clear();
        updateNumVol.clear();
        updateAD.clear();
        updateAA.clear();
        updateDureeVol.clear();
        updateDateD.setValue(null);
        updateDateA.setValue(null);
        updateTarif.clear();
        updateEscale.clear();
        updateImage.clear();
        updateClasse.getSelectionModel().clearSelection();
        updateDestination.setValue(null);
    }

    @FXML
    private void handleUpdateVolButtonAction(ActionEvent event) {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour le vol?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedVol.setCompagnie_a(updateCompagnie.getText());
                selectedVol.setNum_vol(Integer.parseInt(updateNumVol.getText()));
                selectedVol.setAeroport_depart(updateAD.getText());
                selectedVol.setAeroport_arrivee(updateAA.getText());
                java.sql.Date dateDepart = java.sql.Date.valueOf(updateDateD.getValue());
                selectedVol.setDate_depart(dateDepart);
                java.sql.Date dateArrivee = java.sql.Date.valueOf(updateDateA.getValue());
                selectedVol.setDate_arrivee(dateArrivee);
                selectedVol.setEscale(updateEscale.getText());
                Classe selectedClasse = Classe.valueOf(updateClasse.getValue());
                selectedVol.setClasse(selectedClasse);
                selectedVol.setImage(updateImage.getText());
                selectedVol.setTarif((float) Double.parseDouble(updateTarif.getText()));
                selectedVol.setDuree_vol(Integer.parseInt(updateDureeVol.getText()));

                volServices.updateVol(selectedVol);

                volTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Vol mis à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucun vol sélectionné");
            errorAlert.setContentText("Veuillez sélectionner un vol à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

}
