package tn.esprit.Controllers.Guide;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Guide;
import tn.esprit.services.GuideServices;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateGuideController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateGuide;

    @FXML
    private TableView<Guide> guideTableView;

    @FXML
    private TableColumn<Guide, Integer> guide_id;

    @FXML
    private TableColumn<Guide, String> guide_nationalite;

    @FXML
    private TableColumn<Guide, String> guide_nom;

    @FXML
    private TableColumn<Guide, Integer> guide_tarif;

    @FXML
    private TableColumn<Guide, Integer> guide_tel;

    @FXML
    private TextField updateExperiences;

    @FXML
    private TextField updateLangues;

    @FXML
    private TextField updateNationalite;

    @FXML
    private TextField updateNom;

    @FXML
    private TextField updatePrenom;

    @FXML
    private TextField updateTarif;

    @FXML
    private TextField updateTel;

    private GuideServices guideServices ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guideServices = new GuideServices();
        fillGuideTableView();

        guideTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillGuideTableView() {
        List<Guide> guideList = guideServices.getAllGuides();

        guide_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        guide_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        guide_nationalite.setCellValueFactory(new PropertyValueFactory<>("Nationalite"));
        guide_tel.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));
        guide_tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif_horaire"));

        guideTableView.getItems().addAll(guideList);
    }

    void populateFieldsWithData(Guide guide) {
        updateNom.setText(guide.getNom());
        updatePrenom.setText(guide.getPrenom());
        updateNationalite.setText(guide.getNationalite());
        updateTel.setText(String.valueOf(guide.getNum_tel()));
        updateTarif.setText(String.valueOf(guide.getTarif_horaire()));
        updateExperiences.setText(guide.getExperiences());
        updateLangues.setText(guide.getLangues_parlees());


    }

    @FXML
    void handleClearButtonAction(ActionEvent event) {
        updateNom.clear();
        updatePrenom.clear();
        updateNationalite.clear();
        updateTarif.clear();
        updateTel.clear();
        updateExperiences.clear();
        updateLangues.clear();


    }

    @FXML
    void handleUpdateGuideButtonAction(ActionEvent event) {

        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour le Guide?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedGuide.setNom(updateNom.getText());
                selectedGuide.setPrenom(updatePrenom.getText());
                selectedGuide.setNationalite(updateNationalite.getText());
                selectedGuide.setTarif_horaire(Double.valueOf(updateTarif.getText()));
                selectedGuide.setNum_tel(Integer.valueOf(updateTel.getText()));
                selectedGuide.setLangues_parlees(updateLangues.getText());
                selectedGuide.setExperiences(updateExperiences.getText());


                guideServices.updateGuide(selectedGuide);

                guideTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Guide mis à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucun Guide sélectionné");
            errorAlert.setContentText("Veuillez sélectionner un Guide à mettre à jour.");
            errorAlert.showAndWait();
        }


    }

}
