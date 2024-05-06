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
import tn.esprit.entites.SessionManager;
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
    private JFXButton commandeButton;

    @FXML
    private Label connectedUser_Username;

    @FXML
    private JFXButton destinationButton;


    @FXML
    private JFXButton dashboardButton;

    @FXML
    private JFXButton eventsButton;

    @FXML
    private JFXButton guideButton;

    @FXML
    private JFXButton participationButton;

    @FXML
    private JFXButton reservationButton;

    @FXML
    private JFXButton tourneeButton;

    @FXML
    private JFXButton volButton;
    @FXML
    private JFXButton Logout;

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
    @FXML
    void Logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();

        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) Logout.getScene().getWindow();

            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);

            currentStage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToDestination(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/ListDestination_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) destinationButton.getScene().getWindow();
            Scene newScene = new Scene(root);

            currentStage.setScene(newScene);
            currentStage.setTitle("List of Destinations");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToVol(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/ListVol_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) volButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List of Vols");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void goToCommande(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commande/ListCommande_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) commandeButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List des commandes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/BackDashboard.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) dashboardButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("ADMIN - Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void goToEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/ListEvent_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) eventsButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List of Events");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToGuide(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/Guide_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) guideButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des guides");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToParticipation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Participation/ListParticipation_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) participationButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des participations");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/ListReservation_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) reservationButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des reservation");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToTournee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/Tournee_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) tourneeButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des tourneés");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
