package tn.esprit.Controllers.Participation;

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
import tn.esprit.entites.Participation;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.ParticipationServices;
import tn.esprit.entites.User;


import java.io.IOException;
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
    @FXML
    private Label errorLabel;
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

    private ParticipationServices participationServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }

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
        updateTel.setText(String.valueOf(participation.getTel()));
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
            String nom = updateNom.getText().trim();
            String prenom = updatePrenom.getText().trim();
            String tel = updateTel.getText().trim();
            String email = updateEmail.getText().trim();

            // Validate input
            String errorMessage = validateInput(nom, prenom, tel, email);
            if (errorMessage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Voulez-vous mettre à jour la participation?");
                alert.setContentText("Cette action ne peut pas être annulée.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    selectedParticipation.setNom(nom);
                    selectedParticipation.setPrenom(prenom);
                    selectedParticipation.setTel(Integer.parseInt(tel));
                    selectedParticipation.setEmail(email);

                    participationServices.updateParticipation(selectedParticipation);

                    participationTableView.refresh();

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Participation mise à jour avec succès");
                    successAlert.showAndWait();
                }
            } else {
                // Display error message
                errorLabel.setText(errorMessage);
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucune participation sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner une participation à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

    private String validateInput(String nom, String prenom, String tel, String email) {
        StringBuilder errorMessage = new StringBuilder();

        // Validate nom
        if (nom.isEmpty() || nom.length() < 3 || nom.length() > 10) {
            errorMessage.append("Le nom doit contenir entre 3 et 10 caractères.\n");
        }

        // Validate prenom
        if (prenom.isEmpty() || prenom.length() < 3 || prenom.length() > 10) {
            errorMessage.append("Le prénom doit contenir entre 3 et 10 caractères.\n");
        }

        // Validate tel
        if (!tel.matches("\\d{8}")) {
            errorMessage.append("Le numéro de téléphone doit contenir exactement 8 chiffres.\n");
        }

        // Validate email
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            errorMessage.append("L'adresse e-mail n'est pas valide.\n");
        }

        return errorMessage.toString();
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
