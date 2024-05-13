package tn.esprit.Controllers.Guide;

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
import tn.esprit.entites.Guide;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.GuideServices;
import tn.esprit.entites.User;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateGuideController implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateGuide;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<Guide> guideTableView;

    @FXML
    private TableColumn<Guide, Integer> guide_id;

    @FXML
    private TableColumn<Guide, String> guide_nationalite;

    @FXML
    private TableColumn<Guide, String> guide_nom;

    @FXML
    private TableColumn<Guide, Integer> guide_tarif;

    @FXML
    private TableColumn<Guide, Integer> guide_tel;

    @FXML
    private TextField updateExperiences;

    @FXML
    private TextField updateLangues;

    @FXML
    private TextField updateNationalite;

    @FXML
    private TextField updateNom;

    @FXML
    private TextField updatePrenom;

    @FXML
    private TextField updateTarif;

    @FXML
    private TextField updateTel;
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

    private GuideServices guideServices ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        guideServices = new GuideServices();
        fillGuideTableView();
        errorLabel.setVisible(false);
        guideTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }

    private void fillGuideTableView() {
        List<Guide> guideList = guideServices.getAllGuides();

        guide_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        guide_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        guide_nationalite.setCellValueFactory(new PropertyValueFactory<>("Nationalite"));
        guide_tel.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));
        guide_tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif_horaire"));

        guideTableView.getItems().addAll(guideList);
    }

    void populateFieldsWithData(Guide guide) {
        updateNom.setText(guide.getNom());
        updatePrenom.setText(guide.getPrenom());
        updateNationalite.setText(guide.getNationalite());
        updateTel.setText(String.valueOf(guide.getNum_tel()));
        updateTarif.setText(String.valueOf(guide.getTarif_horaire()));
        updateExperiences.setText(guide.getExperiences());
        updateLangues.setText(guide.getLangues_parlees());


    }

    @FXML
    void handleClearButtonAction(ActionEvent event) {
        updateNom.clear();
        updatePrenom.clear();
        updateNationalite.clear();
        updateTarif.clear();
        updateTel.clear();
        updateExperiences.clear();
        updateLangues.clear();


    }

    @FXML
    void handleUpdateGuideButtonAction(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }
        Guide selectedGuide = guideTableView.getSelectionModel().getSelectedItem();

        if (selectedGuide != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour le Guide?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedGuide.setNom(updateNom.getText());
                selectedGuide.setPrenom(updatePrenom.getText());
                selectedGuide.setNationalite(updateNationalite.getText());
                selectedGuide.setTarif_horaire(Double.valueOf(updateTarif.getText()));
                selectedGuide.setNum_tel(Integer.valueOf(updateTel.getText()));
                selectedGuide.setLangues_parlees(updateLangues.getText());
                selectedGuide.setExperiences(updateExperiences.getText());


                guideServices.updateGuide(selectedGuide);

                guideTableView.refresh();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Guide mis à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucun Guide sélectionné");
            errorAlert.setContentText("Veuillez sélectionner un Guide à mettre à jour.");
            errorAlert.showAndWait();
        }


    }
    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();

        if (!validateNom(errors)) {
            errors.append(" 'nom' doit comporter entre 4 et 10 caractères.\n");
        }

        if (!validatePrenom(errors)) {
            errors.append(" 'prenom' doit comporter entre 4 et 10 caractères.\n");
        }

        if (!validateNationalite(errors)) {
            errors.append(" La nationalite doit comporter au moins 5 caractères.\n");
        }

        if (!validateLangues(errors)) {
            errors.append(" 'Langues' doit comporter au moins 5 caractères.\n");
        }

        if (!validateExperience(errors)) {
            errors.append(" 'Experience' doit comporter au moins 5 caractères.\n");
        }

        if (!validateTarif(errors)) {
            errors.append(" 'Tarif' doit être une URL valide.\n");
        }



        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean validateNom(StringBuilder errors) {
        String nom = updateNom.getText().trim();
        if (nom.isEmpty() || nom.length() > 10 || nom.length() < 4) {
            return false;
        }
        return true;
    }

    private boolean validatePrenom(StringBuilder errors) {
        String prenom = updatePrenom.getText().trim();
        if (prenom.isEmpty() || prenom.length() > 10 || prenom.length() < 4) {
            return false;
        }
        return true;
    }

    private boolean validateNationalite(StringBuilder errors) {
        String nationalite = updateNationalite.getText().trim();
        if (nationalite.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateLangues(StringBuilder errors) {
        String langues = updateLangues.getText().trim();
        if (langues.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateExperience(StringBuilder errors) {
        String experience = updateExperiences.getText().trim();
        if (experience.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateTarif(StringBuilder errors) {
        try {
            float tarif = Float.parseFloat(updateTarif.getText().trim());
            if (tarif > 5000.0) {
                errors.append("'Tarif' ne doit pas dépasser 5000.0.\n");
                return false;
            }
        } catch (NumberFormatException e) {
            errors.append("'Tarif' doit être un nombre valide.\n");
            return false;
        }
        return true;
    }


    private void validateNum(String cinText, StringBuilder errors) {
        try {
            int cin = Integer.parseInt(cinText);
            if (cin < 10000000 || cin > 99999999) {
                errors.append("'num' doit être un nombre entier composé de 8 chiffres.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'num' doit être un nombre entier composé de 8 chiffres.\n");
        }
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
