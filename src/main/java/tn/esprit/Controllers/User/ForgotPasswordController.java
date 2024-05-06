package tn.esprit.Controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.*;
import tn.esprit.services.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Random;


public class ForgotPasswordController {

    @FXML
    private Button idGetCode;

    @FXML
    private Button idBackToSigninForgotPass;

    @FXML
    private TextField idForgotPassEmail;

    public static int code;
    public static String EmailReset ;


    @FXML
    private void backToSignIn(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/User/Login.fxml"));
            idForgotPassEmail.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private int generateVerificationCode() {

        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    @FXML
    private void btnCodeAction(ActionEvent event) {
        code = generateVerificationCode();
        Alert A = new Alert(Alert.AlertType.WARNING);
        UserServices su = new UserServices();

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        boolean verifMail = idForgotPassEmail.getText().matches(emailRegex);

        if (!idForgotPassEmail.getText().equals("") && verifMail) {
            if (su.ChercherMail(idForgotPassEmail.getText()) == 1) {
                EmailReset = idForgotPassEmail.getText();
                MailSender.sendEmail("nader123mb456@gmail.com", "thuo wsef kuly acgp", idForgotPassEmail.getText(), "Verification code", "Votre code est : " + code);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/User/VerifCode.fxml"));
                    idForgotPassEmail.getScene().setRoot(root);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

            } else {
                A.setContentText("no account with this email ");
                A.show();
            }
        } else {
            A.setContentText("Veuillez saisir une adresse mail valide ! ");
            A.show();
        }

    }

}
