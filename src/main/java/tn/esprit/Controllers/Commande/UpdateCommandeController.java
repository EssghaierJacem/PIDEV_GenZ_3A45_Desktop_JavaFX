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
import tn.esprit.entites.SessionManager;
import tn.esprit.services.CommandeServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
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
    private TextField updateCode_promo;
    ;

    @FXML
    private TextField updateType;

    @FXML
    private TextField updateEmail;

    @FXML
    private DatePicker updateDateC;

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
        errorLabel.setText("");
    }

    @FXML
    private void handleUpdateCommandenButtonAction(ActionEvent event) {
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();

        if (selectedCommande != null) {
            if (validateInputs()) {
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
            }}
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucune destination sélectionnée");
            errorAlert.setContentText("Veuillez sélectionner une destination à mettre à jour.");
            errorAlert.showAndWait();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean validateInputs() {
        String numCommande = updateNum_commande.getText();
        String codePromo = updateCode_promo.getText();
        String typePaiement = updateType.getText();
        String email = updateEmail.getText();
        LocalDate dateCommande = updateDateC.getValue();

        if (numCommande.length() < 4 || numCommande.length() > 6) {
            errorLabel.setText("Le numéro de commande doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (codePromo.length() < 4 || codePromo.length() > 6) {
            errorLabel.setText("Le code promo doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (typePaiement.length() < 4 || typePaiement.length() > 6) {
            errorLabel.setText("Le type de paiement doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("L'email n'est pas valide.");
            return false;
        }

        if (dateCommande == null || dateCommande.isAfter(LocalDate.now())) {
            errorLabel.setText("Veuillez sélectionner une date de commande valide (passée ou présente).");
            return false;
        }

        return true;
    }
    private boolean isValidEmail(String email) {
        // Regular expression pattern for validating email addresses
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
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




