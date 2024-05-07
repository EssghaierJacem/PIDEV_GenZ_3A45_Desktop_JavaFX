package tn.esprit.Controllers.Commande;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.Commande;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.services.CommandeServices;
import tn.esprit.services.DestinationServices;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeController_Front implements Initializable {

    @FXML
    private TableColumn<Commande, String> Code_promo;

    @FXML
    private TableView<Commande> CommandeTableView;

    @FXML
    private TableColumn<Commande, String> Date_commande;

    @FXML
    private TableColumn<Commande, String> Email;

    @FXML
    private JFXButton Logout;

    @FXML
    private TableColumn<Commande, String > Num_commande;

    @FXML
    private TableColumn<Commande, String> Type_paiement;

    @FXML
    private TableColumn<Commande, Integer> commande_id;

    @FXML
    private Label connectedUser_Username;
    @FXML
    private JFXButton dashboardButton;

    @FXML
    void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/FrontDashboard.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) dashboardButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("User - Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogoutAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        addCommandeShowListData();
    }
    private void addCommandeShowListData() {
        CommandeServices commandeServices = new CommandeServices();
        List<Commande> commandeList = commandeServices.getAllCommandes();

        commande_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Num_commande.setCellValueFactory(new PropertyValueFactory<>("Num_commande"));
        Date_commande.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Code_promo.setCellValueFactory(new PropertyValueFactory<>("code_promo"));
        Type_paiement.setCellValueFactory(new PropertyValueFactory<>("type_paiement"));

        CommandeTableView.getItems().addAll(commandeList);
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Commande selectedCommande = CommandeTableView.getSelectionModel().getSelectedItem();

        if (selectedCommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CommandeServices commandeServices = new CommandeServices();
                    commandeServices.removeCommande(selectedCommande.getId());

                    CommandeTableView.getItems().remove(selectedCommande);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun commande sélectionné");
            alert.setContentText("Veuillez choisir une commande à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Commande selectedCommande = CommandeTableView.getSelectionModel().getSelectedItem();

        if (selectedCommande == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun commande sélectionné");
            alert.setContentText("Veuillez choisir un commande à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commande/UpdateCommande.fxml"));
        Parent root = loader.load();

        UpdateCommandeController updateCommandeController = loader.getController();
        updateCommandeController.populateFieldsWithData(selectedCommande);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAjouterCommandeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Commande/AddCommande.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Commande selectedCommande = CommandeTableView.getSelectionModel().getSelectedItem();

        if (selectedCommande == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun commande sélectionné");
            alert.setContentText("Veuillez choisir un commande à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commande/CommandeByID_Back.fxml"));
        Parent root = loader.load();

        CommandeByID_BackController commandeByIDBackController = loader.getController();

        commandeByIDBackController.setCommandeData(selectedCommande);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
