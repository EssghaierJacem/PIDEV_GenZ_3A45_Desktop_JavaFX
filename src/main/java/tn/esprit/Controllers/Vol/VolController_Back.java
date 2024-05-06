package tn.esprit.Controllers.Vol;

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
import tn.esprit.entites.SessionManager;
import tn.esprit.services.VolServices;
import tn.esprit.entites.Vol;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

public class VolController_Back implements Initializable {

    @FXML
    private TableView<Vol> volTableView;

    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private JFXButton volButton;

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

    @FXML
    private TextField keywordSearch;


    @FXML
    private TableColumn<Vol, Integer> vol_cell_id;

    @FXML
    private TableColumn<Vol, String> vol_cell_destination;

    @FXML
    private TableColumn<Vol, String> vol_cell_compagnie;

    @FXML
    private TableColumn<Vol, String> vol_cell_dateD;

    @FXML
    private TableColumn<Vol, String> vol_cell_dateA;

    @FXML
    private TableColumn<Vol, String> vol_cell_AD;

    @FXML
    private TableColumn<Vol, String> vol_cell_AA;

    @FXML
    private TableColumn<Vol, Integer> vol_cell_duree;

    @FXML
    private TableColumn<Vol, Float> vol_cell_tarif;

    @FXML
    private TableColumn<Vol, String> vol_cell_classe;

    @FXML
    private TableColumn<Vol, String> vol_cell_escale;
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

    private List<Vol> originalVolList;



    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        populateVolTableView();

        keywordSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterVols(newValue);
        });
    }


    private void populateVolTableView() {
        VolServices volServices = new VolServices();
        originalVolList = volServices.getAllVols();

        vol_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        vol_cell_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });
        vol_cell_compagnie.setCellValueFactory(new PropertyValueFactory<>("compagnie_a"));
        vol_cell_dateD.setCellValueFactory(new PropertyValueFactory<>("date_depart"));
        vol_cell_dateA.setCellValueFactory(new PropertyValueFactory<>("date_arrivee"));
        vol_cell_AD.setCellValueFactory(new PropertyValueFactory<>("aeroport_depart"));
        vol_cell_AA.setCellValueFactory(new PropertyValueFactory<>("aeroport_arrivee"));
        vol_cell_duree.setCellValueFactory(new PropertyValueFactory<>("duree_vol"));
        vol_cell_tarif.setCellValueFactory(new PropertyValueFactory<>("tarif"));
        vol_cell_classe.setCellValueFactory(new PropertyValueFactory<>("classe"));
        vol_cell_escale.setCellValueFactory(new PropertyValueFactory<>("escale"));

        volTableView.getItems().addAll(originalVolList);
    }
    private void filterVols(String keyword) {
        List<Vol> filteredVolList = new ArrayList<>();

        for (Vol vol : originalVolList) {
            if (vol.getDestination().getPays().toLowerCase().contains(keyword.toLowerCase()) ||
                    vol.getCompagnie_a().toLowerCase().contains(keyword.toLowerCase()) ||
                    vol.getAeroport_depart().toLowerCase().contains(keyword.toLowerCase()) ||
                    vol.getAeroport_arrivee().toLowerCase().contains(keyword.toLowerCase())) {
                filteredVolList.add(vol);
            }
        }

        volTableView.getItems().setAll(filteredVolList);
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    VolServices volServices = new VolServices();
                    volServices.removeVol(selectedVol.getId());

                    volTableView.getItems().remove(selectedVol);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun vol sélectionné");
            alert.setContentText("Veuillez choisir un vol à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun vol sélectionné");
            alert.setContentText("Veuillez choisir un vol à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/UpdateVol.fxml"));
        Parent root = loader.load();

        UpdateVolController updateVolController = loader.getController();
        updateVolController.populateFieldsWithData(selectedVol);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAjouterVolButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vol/AddVol.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirPlusButtonAction(ActionEvent event) throws IOException {
        Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();

        if (selectedVol == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun vol sélectionné");
            alert.setContentText("Veuillez choisir un vol à afficher.");
            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/VolByID_Back.fxml"));
        Parent root = loader.load();

        VolByID_BackController volByIDBackController = loader.getController();

        volByIDBackController.setVolData(selectedVol);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
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
