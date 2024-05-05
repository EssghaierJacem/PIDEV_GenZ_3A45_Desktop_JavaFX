package tn.esprit.Controllers.Commande;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.jfoenix.controls.JFXButton;

import javafx.stage.Stage;

import tn.esprit.entites.Destination;
import tn.esprit.services.CommandeServices;
import tn.esprit.entites.Commande;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
public class CommandeController_Back implements Initializable {

    @FXML
    private TableView<Commande> commandeTableView;

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

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
    private TextField keywordSearch;
    private Timer searchTimer = new Timer();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        addCommandeShowListData();
        keywordSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTimer.cancel();
            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    filterAndUpdateCommandeTable(newValue);
                }
            }, 100);
        });
    }

    private void addCommandeShowListData() {
        CommandeServices commandeServices = new CommandeServices();
        List<Commande> commandeList = commandeServices.getAllCommandes();

        commande_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        commande_cell_Num_commande.setCellValueFactory(new PropertyValueFactory<>("Num_commande"));
        commande_cell_dateC.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        commande_cell_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        commande_cell_code_promo.setCellValueFactory(new PropertyValueFactory<>("code_promo"));
        commande_cell_type.setCellValueFactory(new PropertyValueFactory<>("type_paiement"));
        commandeTableView.getItems().addAll(commandeList);
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();

        if (selectedCommande != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CommandeServices commandeServices = new CommandeServices();
                    commandeServices.removeCommande(selectedCommande.getId());

                    commandeTableView.getItems().remove(selectedCommande);
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
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();

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
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();

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

    private void filterAndUpdateCommandeTable(String keyword) {
        new Thread(() -> {
            List<Commande> filteredCommandeList = new ArrayList<>();
            CommandeServices commandeServices = new CommandeServices();
            List<Commande> originalCommandeList= commandeServices.getAllCommandes();
            for (Commande commande :originalCommandeList ) {
                if (commande.getNum_commande().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredCommandeList.add(commande);
                }
            }
            Platform.runLater(() -> {
                commandeTableView.getItems().clear();

                commandeTableView.getItems().addAll(filteredCommandeList);
            });
        }).start();

    }
}