package tn.esprit.Controllers.Reservation;

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
import tn.esprit.entites.Reservation;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.ReservationServices;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateReservationController implements Initializable {

    public TextField updateQuantity;
    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateReservation;

    @FXML
    private Button update;

    @FXML
    private TableView<Reservation> reservationTableView;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_Id;

    @FXML
    private TableColumn<Reservation, String> reservation_cellNom_client;

    @FXML
    private TableColumn<Reservation, String> reservation_cell_Prenom_client;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_Num_tel;

    @FXML
    private TableColumn<Reservation, Integer> reservation_cell_quantite;
    @FXML
    private TableColumn<Reservation, Date> reservation_cell_dateR;
    @FXML
    private TextField updateNom_client;

    @FXML
    private TextField updatePrenom_client;

    @FXML
    private TextField  updateNum_tel;

    @FXML
    private DatePicker updateDateR;

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


    private ReservationServices reservationServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationServices = new ReservationServices();
        fillReservationTableView();

        reservationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillReservationTableView() {
        List<Reservation> reservationList = reservationServices.getAllReservations();

        reservation_cell_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        reservation_cellNom_client.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
        reservation_cell_Prenom_client.setCellValueFactory(new PropertyValueFactory<>("prenom_client"));
        reservation_cell_Num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        reservation_cell_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        reservation_cell_dateR.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
        reservationTableView.getItems().addAll(reservationList);
    }

    void populateFieldsWithData(Reservation reservation) {
        updateNom_client.setText(reservation.getNom_client());

        updatePrenom_client.setText(reservation.getPrenom_client());

        updateNum_tel.setText(String.valueOf(reservation.getNum_tel()));


        updateQuantity.setText(String.valueOf(reservation.getQuantite()));

        Date dateReservation = reservation.getDate_reservation();
        LocalDate dateR = dateReservation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        updateDateR.setValue(dateR);


    }
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        updateNom_client.clear();
        updatePrenom_client.clear();
        updateNum_tel.clear();
        updateQuantity.clear();
        updateDateR.setValue(null);
    }

    @FXML
    private void handleUpdateReservationButtonAction(ActionEvent event) {
        Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            if (validateInputs()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour la destination?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedReservation.setNom_client(updateNom_client.getText());
                selectedReservation.setPrenom_client(updatePrenom_client.getText());
                selectedReservation.setNum_tel(Integer.parseInt(updateNum_tel.getText()));
                selectedReservation.setQuantite(Integer.parseInt(updateQuantity.getText()));
                selectedReservation.setDate_reservation(java.sql.Date.valueOf(updateDateR.getValue()));
                reservationServices.updateReservation(selectedReservation);

                reservationTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("reservation mise à jour avec succès");
                successAlert.showAndWait();
            }}
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucune destination sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner une destination à mettre à jour.");
            errorAlert.showAndWait();
        }
    }
    private boolean validateInputs() {
        String nomClient = updateNom_client.getText();
        String prenomClient = updatePrenom_client.getText();
        String numTel = updateNum_tel.getText();
        String quantity = updateQuantity.getText();
        LocalDate dateR = updateDateR.getValue();

        if (nomClient.isEmpty() || prenomClient.isEmpty() || numTel.isEmpty() || quantity.isEmpty() || dateR == null) {
            errorLabel.setText("Veuillez remplir tous les champs correctement.");
            return false; // Au moins un champ est vide
        }

        if (!isNumeric(numTel) || numTel.length() != 8) {
            errorLabel.setText("Le numéro de téléphone doit avoir 8 chiffres.");
            return false; // Le numéro de téléphone doit avoir 8 chiffres et être numérique
        }

        try {
            int quantityValue = Integer.parseInt(quantity);
            if (quantityValue <= 0) {
                errorLabel.setText("La quantité doit être un nombre positif.");
                return false; // La quantité doit être un nombre positif
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("La quantité doit être un nombre valide.");
            return false; // La quantité n'est pas un nombre valide
        }

        // Vous pouvez ajouter d'autres validations selon vos besoins, par exemple pour la date de réservation

        return true; // Toutes les validations ont réussi
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+"); // Vérifie si la chaîne ne contient que des chiffres
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
