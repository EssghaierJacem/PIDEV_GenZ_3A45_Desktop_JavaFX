package tn.esprit.Controllers.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class VerifCodeController {

    @FXML
    private Button idBackCode;

    @FXML
    private Button IdContinueCode;

    @FXML
    private TextField idCodeVerif;

    @FXML
    private void btnConfirmerCodeAction(ActionEvent event) {
        if (idCodeVerif.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Champs vide !");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ForgotPassword.fxml"));
                Parent root = loader.load();

                ForgotPasswordController forgotPasswordController = loader.getController();

                if (Integer.parseInt(idCodeVerif.getText()) == forgotPasswordController.code) {

                        try {
                            Parent root2 = FXMLLoader.load(getClass().getResource("/User/ResetPassword.fxml"));
                            idCodeVerif.getScene().setRoot(root2);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }


                } else {

                    Alert A = new Alert(Alert.AlertType.WARNING);
                    A.setContentText("Code erroné ! ");
                    A.show();

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
