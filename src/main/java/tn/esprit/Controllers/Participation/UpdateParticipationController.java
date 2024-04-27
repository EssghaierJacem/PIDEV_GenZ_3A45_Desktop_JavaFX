package tn.esprit.Controllers.Participation;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Participation;
import tn.esprit.services.ParticipationServices;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateParticipationController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateParticipation;

    @FXML
    private TableView<Participation> participationTableView;

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
    private TextField updateNom;

    @FXML
    private TextField updatePrenom;

    @FXML
    private TextField updateTel;

    @FXML
    private TextField updateEmail;

    private ParticipationServices participationServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        participationServices = new ParticipationServices();
        fillParticipationTableView();

        participationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillParticipationTableView() {
        List<Participation> participationList = participationServices.getAllParticipations();

        participation_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        participation_cell_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        participation_cell_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        participation_cell_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        participation_cell_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        participationTableView.getItems().addAll(participationList);
    }

    void populateFieldsWithData(Participation participation) {
        updateNom.setText(participation.getNom());
        updatePrenom.setText(participation.getPrenom());
        updateTel.setText(participation.getTel());
        updateEmail.setText(participation.getEmail());
    }
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updateNom.clear();
        updatePrenom.clear();
        updateTel.clear();
        updateEmail.clear();
    }

    @FXML
    private void handleUpdateParticipationButtonAction(ActionEvent event) {
        Participation selectedParticipation = participationTableView.getSelectionModel().getSelectedItem();

        if (selectedParticipation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour la participation?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedParticipation.setNom(updateNom.getText());
                selectedParticipation.setPrenom(updatePrenom.getText());
                selectedParticipation.setTel(Integer.parseInt(updateTel.getText()));
                selectedParticipation.setEmail(updateEmail.getText());

                participationServices.updateParticipation(selectedParticipation);

                participationTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Participation mise à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucune participation sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner une participation à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

}
