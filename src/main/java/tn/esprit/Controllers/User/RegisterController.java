package tn.esprit.Controllers.User;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Role;
import tn.esprit.entites.User;
import tn.esprit.services.UserServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private JFXButton Login;

    @FXML
    private JFXButton Register;

    @FXML
    private TextField addCin;

    @FXML
    private TextField addEmail;

    @FXML
    private TextField addNom;

    @FXML
    private PasswordField addPassword;

    @FXML
    private TextField addPhoto;

    @FXML
    private TextField addPrenom;

    @FXML
    private TextField addUsername;

    @FXML
    private Label labelError;

    private UserServices userServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userServices = new UserServices();
        labelError.setVisible(false);
    }

    @FXML
    void handleRegisterButtonAction(ActionEvent event) {
        String nom = addNom.getText();
        String prenom = addPrenom.getText();
        String username = addUsername.getText();
        String email = addEmail.getText();
        String password = addPassword.getText();
        String photo = addPhoto.getText();
        String cinText = addCin.getText();

        if (!validateInputs(nom, prenom, username, email, password, photo, cinText)) {
            return;
        }

        int cin = Integer.parseInt(cinText);

        User newUser = new User();
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhoto(photo);
        newUser.setCin(cin);
        newUser.setRole(Role.USER);

        userServices.addUser(newUser);

        clearFields();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/ListDestination_Front.fxml"));
            Parent listDestinationRoot = loader.load();
            Stage currentStage = (Stage) Register.getScene().getWindow();
            Scene listDestinationScene = new Scene(listDestinationRoot);
            currentStage.setScene(listDestinationScene);
            currentStage.setTitle("List Destination");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLoginActionButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean validateInputs(String nom, String prenom, String username, String email, String password, String photo, String cinText) {
        labelError.setText("");
        labelError.setVisible(false);
        StringBuilder errors = new StringBuilder();

        validateNomPrenom(nom, "Nom", errors);
        validateNomPrenom(prenom, "Prenom", errors);
        validateUsername(username, errors);
        validateEmail(email, errors);
        validatePassword(password, errors);
        validatePhotoURL(photo, errors);
        validateCin(cinText, errors);

        if (errors.length() > 0) {
            labelError.setText(errors.toString());
            labelError.setVisible(true);
            return false;
        }

        return true;
    }


    private void validateNomPrenom(String value, String fieldName, StringBuilder errors) {
        if (value.isEmpty() || value.length() < 3 || value.length() > 20) {
            errors.append("'").append(fieldName).append("' doit comporter entre 3 et 20 lettres.\n");
        }
    }

    private void validateUsername(String username, StringBuilder errors) {
        if (username.isEmpty() || username.length() < 3 || username.length() > 20) {
            errors.append("'Username' doit comporter entre 3 et 20 caractères.\n");
        }
    }

    private void validateEmail(String email, StringBuilder errors) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            errors.append("'Email' doit être au format 'y@x.sth'.\n");
        }
    }

    private void validatePassword(String password, StringBuilder errors) {
        if (password.isEmpty() || password.length() < 6 || password.length() > 20) {
            errors.append("'Mot de passe' doit comporter entre 6 et 20 caractères.\n");
        }
    }

    private void validatePhotoURL(String photo, StringBuilder errors) {
        String urlRegex = "^(http|https)://[a-zA-Z0-9./_%-]+\\.[a-zA-Z]{2,}$";
        if (!photo.matches(urlRegex)) {
            errors.append("'Photo' doit être une URL valide.\n");
        }
    }

    private void validateCin(String cinText, StringBuilder errors) {
        try {
            int cin = Integer.parseInt(cinText);
            if (cin < 10000000 || cin > 99999999) {
                errors.append("'CIN' doit être un nombre entier composé de 8 chiffres.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'CIN' doit être un nombre entier composé de 8 chiffres.\n");
        }
    }

    private void clearFields() {
        addNom.clear();
        addPrenom.clear();
        addUsername.clear();
        addEmail.clear();
        addPassword.clear();
        addPhoto.clear();
        addCin.clear();
        labelError.setText("");
        labelError.setVisible(false);
    }
}
