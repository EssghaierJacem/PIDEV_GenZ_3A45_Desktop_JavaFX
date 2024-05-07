package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Tournee;
import tn.esprit.entites.Guide;

import tn.esprit.services.GuideServices;
import tn.esprit.services.TourneeServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Tournee_BackController implements Initializable {

    @FXML
    private JFXButton Ajouter;

    @FXML
    private JFXButton Delete;

    @FXML
    private TextField Search;

    @FXML
    private TableColumn<Tournee, String> T_age;

    @FXML
    private TableColumn<Tournee, String> T_date;

    @FXML
    private TableColumn<Tournee, String> T_description;

    @FXML
    private TableColumn<Tournee, String> T_destination;

    @FXML
    private TableColumn<Tournee, String> T_duree;

    @FXML
    private TableColumn<Tournee, String> T_guide;

    @FXML
    private TableColumn<Tournee, Integer> T_id;

    @FXML
    private TableColumn<Tournee, String> T_monument;

    @FXML
    private TableColumn<Tournee, String> T_nom;

    @FXML
    private TableColumn<Tournee, Float> T_tarif;

    @FXML
    private TableColumn<Tournee, String> T_transport;

    @FXML
    private TableView<Tournee> TourneeTableView;

    @FXML
    private JFXButton Update;

    @FXML
    private JFXButton VoirPlus;

    private ObservableList<Tournee> tourneeList;
    //Buttons

    @FXML
    private JFXButton Logout;

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


     private void addTourneeShow() {
         TourneeServices tourneeServices = new TourneeServices();
         List<Tournee> tournees = tourneeServices.getAllTournees();
         tourneeList = FXCollections.observableArrayList(tournees); // Initialise tourneeList

        T_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        T_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });
        T_guide.setCellValueFactory(cellData -> {
            String guideName = cellData.getValue().getGuide().getNom();
            return new SimpleStringProperty(guideName);
        });
        T_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        T_date.setCellValueFactory(new PropertyValueFactory<>("Date_debut"));
        T_duree.setCellValueFactory(new PropertyValueFactory<>("Duree"));
        T_description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        T_tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif"));
        T_monument.setCellValueFactory(new PropertyValueFactory<>("Monuments"));
        T_age.setCellValueFactory(new PropertyValueFactory<>("Tranche_age"));
        T_transport.setCellValueFactory(new PropertyValueFactory<>("Moyen_transport"));


        TourneeTableView.getItems().addAll(tourneeList);
    }


    private void initializeSearchFunctionality() {
        FilteredList<Tournee> filteredData = new FilteredList<>(tourneeList, p -> true);

        Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tournee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return tournee.getMonuments().toLowerCase().contains(lowerCaseFilter);
            });
        });

        TourneeTableView.setItems(filteredData);
    }


    @FXML
    void handleAjouterButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/AddTournee.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) Ajouter.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Tournees");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
       Tournee selectedTournee = TourneeTableView.getSelectionModel().getSelectedItem();

        if (selectedTournee != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    GuideServices guideServices = new GuideServices();
                    guideServices.removeGuide(selectedTournee.getId());

                    TourneeTableView.getItems().remove(selectedTournee);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun guide sélectionné");
            alert.setContentText("Veuillez choisir un uide à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void handleUpdateButtonAction(ActionEvent event) throws IOException{
        Tournee selectedTournee = TourneeTableView.getSelectionModel().getSelectedItem();

        if (selectedTournee == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Tournee sélectionné");
            alert.setContentText("Veuillez choisir un Tournee à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/TourneeUpdate.fxml"));
        Parent root = loader.load();

        UpdateTourneeController updateTourneeController = loader.getController();
        updateTourneeController.populateFieldsWithData(selectedTournee);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Tournee selectedTournee = TourneeTableView.getSelectionModel().getSelectedItem();

        if (selectedTournee == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Guide sélectionné");
            alert.setContentText("Veuillez choisir un Guide à afficher.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tournee/TourneeShow.fxml"));
        Parent root = loader.load();

        ShowTourneeController showTourneeController = loader.getController();
        showTourneeController.setTourneeData(selectedTournee);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTourneeShow();
        initializeSearchFunctionality();

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
