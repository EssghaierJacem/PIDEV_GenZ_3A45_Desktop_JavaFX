package tn.esprit.Controllers.Commande;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Commande;
import tn.esprit.services.CommandeServices;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCommandeController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateCommande;

    @FXML
    private TableView<Commande> commandeTableView;

    @FXML
    private TableColumn<Commande, Integer> destination_cell_id;

    @FXML
    private TableColumn<Commande, String> Commande_cell_Num_Commande;
    @FXML
    private TableColumn<Commande, Integer> commande_cell_id;

    @FXML
    private TableColumn<Commande, String> commande_cell_code_promo;

    @FXML
    private TableColumn<Commande, String> commande_cell_type;
    @FXML
    private TableColumn<Commande, String> commande_cell_email;

    @FXML
    private TableColumn<Commande, String> commande_cell_dateC;

    @FXML
    private TableColumn<Commande, Integer> commande_cell_Num_commande;


    @FXML
    private TextField updateNum_commande;


    @FXML
    private TextField updateCode_promo;;

    @FXML
    private TextField updateType;

    @FXML
    private TextField updateEmail;

    @FXML
    private DatePicker updateDateC;


    private CommandeServices commandeServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandeServices = new CommandeServices();
        fillCommandeTableView();

        commandeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillCommandeTableView() {
        List<Commande> commandeList = commandeServices.getAllCommandes();

        commande_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        commande_cell_Num_commande.setCellValueFactory(new PropertyValueFactory<>("Num_commande"));
        commande_cell_code_promo.setCellValueFactory(new PropertyValueFactory<>("Code_promo"));
        commande_cell_type.setCellValueFactory(new PropertyValueFactory<>("type_paiement"));
        commande_cell_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        commande_cell_dateC.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        commandeTableView.getItems().addAll(commandeList);
    }

    void populateFieldsWithData(Commande commande) {
        updateNum_commande.setText(commande.getNum_commande());
        updateCode_promo.setText(commande.getCode_promo());
        updateType.setText(commande.getType_paiement());
        updateEmail.setText(commande.getEmail());
    }
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updateNum_commande.clear();
        updateCode_promo.clear();
        updateType.clear();
        updateEmail.clear();
    }

    @FXML
    private void handleUpdateCommandenButtonAction(ActionEvent event) {
       Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();

        if (selectedCommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour la destination?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedCommande.setNum_commande(updateNum_commande.getText());
                selectedCommande.setCode_promo(updateCode_promo.getText());
                selectedCommande.setType_paiement(updateType.getText());
                selectedCommande.setEmail(updateEmail.getText());
                java.sql.Date dateCommande = java.sql.Date.valueOf(updateDateC.getValue());
                selectedCommande.setDate_commande(dateCommande);
                commandeServices.updateCommande(selectedCommande);

                commandeTableView.refresh();

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
