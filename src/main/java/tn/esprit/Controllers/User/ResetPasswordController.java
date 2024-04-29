package tn.esprit.Controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import tn.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import tn.esprit.services.*;

public class ResetPasswordController {
    @FXML
    private Button idBackCode;

    @FXML
    private PasswordField idConfirmNewPassword;

    @FXML
    private Button IdContinueCode;

    @FXML
    private PasswordField idNewPassword;

    @FXML
    private void btnResetAction(ActionEvent event) {
        Alert A = new Alert(Alert.AlertType.INFORMATION);
        if (!idNewPassword.getText().equals("") && idNewPassword.getText().equals(idConfirmNewPassword.getText())) {
            UserServices su = new UserServices();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ForgotPassword.fxml"));
                Parent root = loader.load();

            ForgotPasswordController forgotPasswordController = loader.getController();
            su.ResetPassword(forgotPasswordController.EmailReset, idNewPassword.getText());
            A.setContentText("Mot de passe modifié avec succes ! ");
            A.show();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {

                Parent page1 = FXMLLoader.load(getClass().getResource("/User/SignIn.fxml"));

                Scene scene = new Scene(page1);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(scene);

                stage.show();

            } catch (IOException ex) {

                System.out.println(ex.getMessage());

            }
        } else {
            A.setContentText("veuillez saisir un mot de passe conforme !");
            A.show();
        }

    }


    public void backToSignIn(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/User/Login.fxml"));
            idNewPassword.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}