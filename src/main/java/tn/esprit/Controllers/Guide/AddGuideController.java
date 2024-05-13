package tn.esprit.Controllers.Guide;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Guide;
import tn.esprit.entites.SessionManager;
import tn.esprit.services.GuideServices;
import tn.esprit.entites.User;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGuideController implements Initializable {
    @FXML
    private Label errorLabel;
    @FXML
    private JFXButton AddGuide;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addExperience;

    @FXML
    private TextField addLangues;

    @FXML
    private TextField addNationalite;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addNumTel;

    @FXML
    private TextField addPrenom;

    @FXML
    private TextField addTarif;
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

    @FXML
    void handleAddGuideButtonAction(ActionEvent event) throws IOException {
        if (!validateInputs()) {
            return;
        }
        Guide newGuide = new Guide();
        newGuide.setNom(addNom.getText());
        newGuide.setPrenom(addPrenom.getText());
        newGuide.setNationalite(addNationalite.getText());
        newGuide.setLangues_parlees(addLangues.getText());
        newGuide.setExperiences(addExperience.getText());
        newGuide.setNum_tel(Integer.parseInt(addNumTel.getText()));
        newGuide.setTarif_horaire(Integer.parseInt(addTarif.getText()));


        GuideServices guideServices = new GuideServices();
        guideServices.addGuide(newGuide);

        clearFields();
        Stage currentStage = (Stage) addNom.getScene().getWindow();
        currentStage.close();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/Guide_Back.fxml"));
        Parent root = loader.load();
        Guide_BackController Guide_BackController = loader.getController();


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();



    }
    private void clearFields() {
        addNom.clear();
        addPrenom.clear();
        addNationalite.clear();
        addLangues.clear();
        addExperience.clear();
        addNumTel.clear();
        addTarif.clear();

    }
    @FXML
    void handleClear(ActionEvent event) {
        clearFields();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        errorLabel.setVisible(false);
    }
    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();

        if (!validateNom(errors)) {
            errors.append(" 'Nom' doit comporter entre 4 et 10 caractères.\n");
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
            errors.append(" 'Experience' doit être un nombre entier composé de 8 chiffres.\n");
        }

        if (!validateTarif(errors)) {
            errors.append(" 'Tarif' ne doit pas dépasser 5000.0\n");
        }



        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean validateNom(StringBuilder errors) {
        String nom = addNom.getText().trim();
        if (nom.isEmpty() || nom.length() > 10 || nom.length() < 4) {
            return false;
        }
        return true;
    }

    private boolean validatePrenom(StringBuilder errors) {
        String prenom = addPrenom.getText().trim();
        if (prenom.isEmpty() || prenom.length() > 10 || prenom.length() < 4) {
            return false;
        }
        return true;
    }

    private boolean validateNationalite(StringBuilder errors) {
        String nationalite = addNationalite.getText().trim();
        if (nationalite.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateLangues(StringBuilder errors) {
        String langues = addLangues.getText().trim();
        if (langues.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateExperience(StringBuilder errors) {
        String experience = addExperience.getText().trim();
        if (experience.length() < 5) {
            return false;
        }
        return true;
    }

    private boolean validateTarif(StringBuilder errors) {
        try {
            float tarif = Float.parseFloat(addTarif.getText().trim());
            if (tarif > 5000.0) {
                errors.append("'Tarif' ne doit pas dépasser 5000.0.\n");
                return false; // Add return statement here
            }
        } catch (NumberFormatException e) {
            errors.append("'Tarif' doit être un nombre valide.\n");
            return false; // Add return statement here
        }
        return true; // Add return statement here
    }


    private void validateNum(String cinText, StringBuilder errors) {
        try {
            int cin = Integer.parseInt(cinText);
            if (cin < 10000000 || cin > 99999999) {
                errors.append("'numero de telephone' doit être un nombre entier composé de 8 chiffres.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'numero' doit être un nombre entier composé de 8 chiffres.\n");
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
