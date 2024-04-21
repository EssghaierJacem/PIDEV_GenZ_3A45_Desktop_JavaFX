package tn.esprit.Controllers.User;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private TextField addPassword;

    @FXML
    private TextField addPhoto;

    @FXML
    private TextField addPrenom;

    @FXML
    private TextField addUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void handleRegisterButtonAction(ActionEvent event){
        User newUser = new User();
        newUser.setNom(addNom.getText());
        newUser.setPrenom(addPrenom.getText());
        newUser.setUsername(addUsername.getText());
        newUser.setEmail(addEmail.getText());
        newUser.setPassword(addPassword.getText());
        newUser.setPhoto(addPhoto.getText());
        newUser.setCin(Integer.parseInt(addCin.getText()));
        newUser.setRole(Role.USER);

        UserServices userServices = new UserServices();
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
    private void clearFields(){
        addNom.clear();
        addPrenom.clear();
        addUsername.clear();
        addEmail.clear();
        addPassword.clear();
        addPhoto.clear();
        addPassword.clear();
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

}
