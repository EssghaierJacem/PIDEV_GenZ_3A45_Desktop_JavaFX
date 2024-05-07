package tn.esprit.Controllers.Guide;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.GuideServices;
import tn.esprit.entites.Guide;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Guide_BackController implements Initializable {

    @FXML
    private JFXButton AjouterGuide;

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton GetGuide;

    @FXML
    private TableColumn<Guide, String> Guide_Experience;

    @FXML
    private TableColumn<Guide, String> Guide_Langues;

    @FXML
    private TableColumn<Guide, String> Guide_Nationalite;

    @FXML
    private TableColumn<Guide, String> Guide_Nom;

    @FXML
    private TableColumn<Guide, String> Guide_Prenom;

    @FXML
    private TableColumn<Guide, Integer> Guide_Tarif;

    @FXML
    private TableColumn<Guide, Integer> Guide_Tel;

    @FXML
    private TableColumn<Guide, Integer> Guide_id;

    @FXML
    private JFXButton Update;

    @FXML
    private TableView<Guide> guideTableView;

    //Buttons

    @FXML
    private JFXButton Logout;

    @FXML
    private JFXButton commandeButton;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addGuideShowListData();

    }




    private void addGuideShowListData() {

        GuideServices guideServices = new GuideServices();
        List<Guide> guideList = guideServices.getAllGuides();

        Guide_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Guide_Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Guide_Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        Guide_Nationalite.setCellValueFactory(new PropertyValueFactory<>("Nationalite"));
        Guide_Langues.setCellValueFactory(new PropertyValueFactory<>("Langues_parlees"));
        Guide_Experience.setCellValueFactory(new PropertyValueFactory<>("Experiences"));
        Guide_Tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif_horaire"));
        Guide_Tel.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));

        guideTableView.getItems().addAll(guideList);
    }

    @FXML
    void handleAjouterGuideButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Guide/AddGuide.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();





    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    GuideServices guideServices = new GuideServices();
                    guideServices.removeGuide(selectedGuide.getId());

                    guideTableView.getItems().remove(selectedGuide);
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
    void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Guide sélectionné");
            alert.setContentText("Veuillez choisir un Guide à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/GuideUpdate.fxml"));
        Parent root = loader.load();

        UpdateGuideController updateGuideController = loader.getController();
        updateGuideController.populateFieldsWithData(selectedGuide);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void handleVoirPlusButtonAction(ActionEvent event) throws IOException{
        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun Guide sélectionné");
            alert.setContentText("Veuillez choisir un Guide à afficher.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/GuideShow.fxml"));
        Parent root = loader.load();

        ShowGuideController showGuideController = loader.getController();
        showGuideController.setGuideData(selectedGuide);

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



