package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.Tournee;
import tn.esprit.services.DestinationServices;

import tn.esprit.services.TourneeServices;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateTourneeController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton Update;

    @FXML
    private TextField age;

    @FXML
    private TableColumn<Tournee, String> cell_age;

    @FXML
    private TableColumn<Tournee, String> cell_destination;

    @FXML
    private TableColumn<Tournee, Integer> cell_id;

    @FXML
    private TableColumn<Tournee, String> cell_monuments;

    @FXML
    private TableColumn<Tournee, Double> cell_tarif;

    @FXML
    private DatePicker date;

    @FXML
    private TextField desc;

    @FXML
    private ComboBox<String> destination;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private TextField duree;

    @FXML
    private ComboBox<String> guide;

    @FXML
    private TextField monuments;

    @FXML
    private TextField nom;

    @FXML
    private TextField tarif;

    @FXML
    private TableView<Tournee> tourneeTableView;

    @FXML
    private TextField transport;

    //Buttons


    @FXML
    private JFXButton commandeButton;

    @FXML
    private Label connectedUser_Username;

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

    private TourneeServices tourneeServices;


    private void clearFields() {
        nom.clear();
        monuments.clear();
        tarif.clear();
        transport.clear();
        date.setValue(null);
        duree.clear();
        age.clear();
        desc.clear();
        guide.getSelectionModel().clearSelection();
        destination.getSelectionModel().clearSelection();
    }


    private void fillTourneeTableView() {
        List<Tournee> tourneeList = tourneeServices.getAllTournees();

        cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cell_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });




        cell_tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif"));
        cell_monuments.setCellValueFactory(new PropertyValueFactory<>("Monuments"));
        cell_age.setCellValueFactory(new PropertyValueFactory<>("Tranche_age"));



        tourneeTableView.getItems().addAll(tourneeList);
    }


    void populateFieldsWithData(Tournee tournee) {

        nom.setText(String.valueOf(tournee.getNom()));

        duree.setText(tournee.getDuree());
        desc.setText(tournee.getDescription());
        monuments.setText(tournee.getMonuments());
        transport.setText(tournee.getMoyen_transport());
        age.setText(String.valueOf(tournee.getTranche_age()));
        tarif.setText(String.valueOf(tournee.getTarif()));
        guide.setValue(tournee.getGuide().toString());
        destination.setValue(tournee.getDestination().toString());


    }



    @FXML
    void handleClearButtonAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void handleUpdateButtonAction(ActionEvent event) {

        Tournee selectedTournee = tourneeTableView.getSelectionModel().getSelectedItem();

        if (selectedTournee != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour le Tournee?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedTournee.setNom(nom.getText());
                selectedTournee.setTranche_age(age.getText());
                selectedTournee.setDescription(desc.getText());
                selectedTournee.setTarif(Double.valueOf(tarif.getText()));
                selectedTournee.setMoyen_transport(transport.getText());
                selectedTournee.setDuree(duree.getText());
                selectedTournee.setMonuments(monuments.getText());
                selectedTournee.setDate_debut(java.sql.Date.valueOf(date.getValue()));
                selectedTournee.setMonuments(monuments.getText());


                tourneeServices.updateTournee(selectedTournee);

                tourneeTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Tournee mis à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucun Tournee sélectionné");
            errorAlert.setContentText("Veuillez sélectionner un Tournee à mettre à jour.");
            errorAlert.showAndWait();
        }


    }
    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        List<Destination> destinationList = destinationServices.getAllDestinations();
        for (Destination ddestination : destinationList) {
            destination.getItems().add(ddestination.toString());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourneeServices = new TourneeServices();
        fillTourneeTableView();
        loadDestinations();



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
