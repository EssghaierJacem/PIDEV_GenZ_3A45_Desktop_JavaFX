package tn.esprit.Controllers.User;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entites.Role;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.services.UserServices;
import org.mindrot.jbcrypt.BCrypt;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public Button idForgotPassword;
    @FXML
    private JFXButton Connect;

    @FXML
    private TextField LoginEmail;

    @FXML
    private PasswordField LoginPassword;

    @FXML
    private JFXButton Register;

    @FXML
    private Label errorLabel;

    private UserServices userServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userServices = new UserServices();
        errorLabel.setVisible(false);
    }

    @FXML
    void handleConnectButtonAction(ActionEvent event) throws IOException {
        String email = LoginEmail.getText();
        String password = LoginPassword.getText();
        if (!validateEmailFormat(email)) {
            errorLabel.setText("Invalid email format. Email should follow the format 'y@x.sth'.");
            errorLabel.setVisible(true);
            return;
        }

        User authenticatedUser = userServices.authenticate(email, password);

        if (authenticatedUser != null) {
            errorLabel.setVisible(false);
            errorLabel.setText("");
            String sessionId = SessionManager.createSession(authenticatedUser);

            if (authenticatedUser.getRole() == Role.ADMIN) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserBack.fxml"));
                    Parent adminRoot = loader.load();
                    Stage currentStage = (Stage) Connect.getScene().getWindow();
                    Scene adminScene = new Scene(adminRoot);
                    currentStage.setScene(adminScene);
                    currentStage.setTitle("Admin - Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (authenticatedUser.getRole() == Role.USER) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard_J/FrontDashboard.fxml"));
                    Parent userRoot = loader.load();
                    Stage currentStage = (Stage) Connect.getScene().getWindow();
                    Scene userScene = new Scene(userRoot);
                    currentStage.setScene(userScene);
                    currentStage.setTitle("User - Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            errorLabel.setText("Invalid email or password. Please try again.");
            errorLabel.setVisible(true);
        }
    }


    @FXML
    void handleRegisterButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Register.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }


    public void oublier(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/User/ForgotPassword.fxml"));
            LoginEmail.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

